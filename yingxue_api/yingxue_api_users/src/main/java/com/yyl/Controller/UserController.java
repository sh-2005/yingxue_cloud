package com.yyl.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyl.entity.*;
import com.yyl.feignclients.FilesClients;
import com.yyl.feignclients.VideoClients;
import com.yyl.prefix.RedisPrefix;
import com.yyl.service.ApiCommentService;
import com.yyl.service.UsersService;
import com.yyl.utils.JSONUtils;
import com.yyl.vo.MsgVo;
import com.yyl.vo.VideoVo;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private FilesClients filesClients;

    @Autowired
    private VideoClients videoClients;

    @Autowired
    private ApiCommentService apiCommentService;


    /*
     * 用户播放
     * */
    @PutMapping("/user/played/{id}")
    public void played(@PathVariable("id")Integer videoId,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        System.out.println("user:"+user);
        VideoVo videoVo = videoClients.findById(videoId);
        if (videoVo.getLikes()!=0){
            String like = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX + videoVo.getId());
            Integer likes = Integer.valueOf(like);
            videoVo.setLikes(likes);
        }
        redisTemplate.opsForSet().add(RedisPrefix.VIDEO_PLAYED_PREFIX+user.getId(),videoVo);
    }

    /*
     * 播放视频历史
     * */
    @GetMapping("/user/played")
    public Set<VideoVo> played(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        String playedId=RedisPrefix.VIDEO_PLAYED_PREFIX+user.getId();
        Set<VideoVo> members = redisTemplate.opsForSet().members(playedId);
        System.out.println("视频历史:"+members);
        return members;
    }



    /*
    * 视频点赞
    * */
    @PutMapping("/user/liked/{id}")
    public void liked(@PathVariable("id")String videoId,HttpServletRequest request){
        System.out.println("===============");
        User user = (User) request.getAttribute("user");
        System.out.println("dasdasd"+user.getId());
        Integer id = user.getId();
        //将当前用户存入redis中
        stringRedisTemplate.opsForSet().add(RedisPrefix.USER_LIKE_PREFIX+id,videoId);
        //将视频点赞+1
        stringRedisTemplate.opsForValue().increment(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX+videoId);

        if (stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_DISLIKE_PREFIX+user.getId(),videoId)){
            //从不喜欢的列表移除
            stringRedisTemplate.opsForSet().remove(RedisPrefix.USER_DISLIKE_PREFIX+user.getId(),videoId);
        }
    }
    /*
    * 视频取消点赞
    * */
    @DeleteMapping("/user/liked/{id}")
    public void cancelLiked(@PathVariable("id")Integer videoId,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        Integer id = user.getId();
        //将该视频从用户点赞列表中移除
        stringRedisTemplate.opsForSet().remove(RedisPrefix.USER_LIKE_PREFIX+id,videoId.toString());
        //将视频点赞次数-1
        stringRedisTemplate.opsForValue().decrement(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX+videoId);
    }

    /*
    * 用户点击不喜欢
    * */
    @PutMapping("/user/disliked/{id}")
    public void disliked(@PathVariable("id")String videoId,HttpServletRequest request){
        //获取当前点击用户信息
        User user = (User) request.getAttribute("user");
        //放入当前用户不喜欢的列表
        stringRedisTemplate.opsForSet().add(RedisPrefix.USER_DISLIKE_PREFIX+user.getId(),videoId);
        //判断之前是否点击过喜欢的视频
        if (stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_LIKE_PREFIX+user.getId(),videoId)){
            //从喜欢的列表删除
            stringRedisTemplate.opsForSet().remove(RedisPrefix.USER_LIKE_PREFIX+user.getId(),videoId);
            //当前视频喜欢次数-1
            stringRedisTemplate.opsForValue().decrement(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX+videoId);
        }
    }

    /*
    * 用户取消不喜欢
    * */
    @DeleteMapping("/user/disliked/{id}")
    public void concelDisliked(@PathVariable("id")String videoId,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        if (stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_DISLIKE_PREFIX+user.getId(),videoId)) {
            stringRedisTemplate.opsForSet().remove(RedisPrefix.USER_DISLIKE_PREFIX + user.getId(),videoId);
        }
    }

    /*
    * 用户收藏视频
    * */
    @PutMapping("/user/favorites/{id}")
    public void favorites(@PathVariable("id")Integer videoId,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        stringRedisTemplate.opsForSet().add(RedisPrefix.USER_FAVORITES_PREFIX+user.getId(),String.valueOf(videoId));
        //判断用户是否收藏该视频
        if (stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_FAVORITES_PREFIX+user.getId(),String.valueOf(videoId))){
            VideoVo videoVo = videoClients.findById(videoId);
            redisTemplate.opsForSet().add(RedisPrefix.VIDEO_FAVORITES_PREFIX+user.getId(),videoVo);
            log.info("收藏视频成功:{}", JSONUtils.writeValueAsString(videoVo));
        }
    }

    /*
    * 收藏视频列表
    * */
    @GetMapping("/user/favorites")
    public Set<Favorite> favorites(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        String favoritesId =RedisPrefix.VIDEO_FAVORITES_PREFIX+user.getId();
        Set<Favorite> members = redisTemplate.opsForSet().members(favoritesId);
        return members;
    }


    /*
    * 用户取消收藏视频
    * */
    @DeleteMapping("/user/favorites/{id}")
    public void concelFavorites(@PathVariable("id")Integer videoId,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        log.info("视频id为:{}",videoId);
        Set<String> s = stringRedisTemplate.opsForSet().members(RedisPrefix.VIDEO_FAVORITES_PREFIX +user.getId());
        for (String a:s){
            System.out.println(a);
        if (stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_FAVORITES_PREFIX+user.getId(),String.valueOf(videoId))){
            stringRedisTemplate.opsForSet().remove(RedisPrefix.VIDEO_FAVORITES_PREFIX+user.getId(),a);
            stringRedisTemplate.opsForSet().remove(RedisPrefix.USER_FAVORITES_PREFIX+user.getId(),String.valueOf(videoId));
        }
        }
    }


    /*
    * 用户发布视频
    * */
    @PostMapping("/user/videos")
    public Video publishVideos(MultipartFile file, Video video,Integer category_id,HttpServletRequest request){
        Map<String, String> result = filesClients.upload(file);
        video.setCover(result.get("cover"));
        video.setLink(result.get("video"));
        video.setCategoryId(category_id);
        //获取用户信息
        User user = (User) request.getAttribute("user");
        video.setUid(user.getId());
        //调用视频服务
        Video publish = videoClients.publish(video);
        log.info("视频发布成功之后返回的视频信息为:{}", JSONUtils.writeValueAsString(publish));
        return publish;
    }


    /*
    * 注销登录
    * */
    @DeleteMapping("tokens")
    public void tokens(String token){
        String tokenKey="session_"+token;
        stringRedisTemplate.delete(tokenKey);
    }

    @GetMapping("/userInfo/{id}")
    public User user(@PathVariable("id")Integer id){
        log.info("id:{}",id);
        return usersService.queryById(id);
    }

    /*
    * 已登录用户信息
    * */
    @GetMapping("user")
    public User user(HttpServletRequest request) throws JsonProcessingException {
        User user = (User)request.getAttribute("user");
        log.info("获取的用户信息为:{}",new ObjectMapper().writeValueAsString(user));
        return user;
    }


    /*
    * 发送验证码
    * */
    @PostMapping("captchas")
    public void captchas(@RequestBody MsgVo msgVo){
        String phone = msgVo.getPhone();
        log.info("手机号:{}",phone);
        String timeoutKey="timeout_"+phone;
        log.info("key:{}",stringRedisTemplate.hasKey(timeoutKey));
        //检查key是否存在
        if (stringRedisTemplate.hasKey(timeoutKey)){
            throw new RuntimeException("提示：不允许重复发送");
        }
        try {
            //生成验证码
            String code = RandomStringUtils.randomNumeric(4);
            //发送验证码
            log.info("验证码:{}",code);
            String phoneKey ="phone_"+phone;
            //2分钟  验证码有效期
            //将验证码放入reidis
            stringRedisTemplate.opsForValue().set(phoneKey,code,120, TimeUnit.MINUTES);
            //防止在一分钟以内重复发送短信
            stringRedisTemplate.opsForValue().set(timeoutKey,"true",60, TimeUnit.MINUTES);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("提示:短信发送失败!");
        }
    }

    /*
    * 登录
    * */
    @PostMapping("tokens")
    public Map<String,Object> tokens(@RequestBody MsgVo msgVo, HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        //用户输入的手机号
        String phone = msgVo.getPhone();
        //用户输入的验证码
        String captcha = msgVo.getCaptcha();
        String phoneKey ="phone_"+phone;
        //判断验证码是否过期
        if (!stringRedisTemplate.hasKey(phoneKey))throw new RuntimeException("提示：验证码已过期");
        //获取验证码
        String code = stringRedisTemplate.opsForValue().get(phoneKey);
        if (!captcha.equals(code)) throw new RuntimeException("提示：验证码输入错误");
        //判断是否为首次登录
        User user = usersService.findByPhone(phone);
        if (ObjectUtils.isEmpty(user)){
            user = new User();
            user.setName(phone);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setPhone(phone);
            user.setIntro("");
            user.setAvatar("");
            user.setPhoneLinked(1);
            user.setWechatLinked(0);
            user.setFollowersCount(0);
            user.setFollowingCount(0);
            usersService.insert(user);
        }
        //获取本次请求sessionid
        String token = request.getSession().getId();
        log.info("token:"+token);
        String tokenKey ="session_"+token;
        redisTemplate.opsForValue().set(tokenKey,user,7,TimeUnit.DAYS);
        hashMap.put("token",token);
        return hashMap;
    }

    @PatchMapping("user")
    public User user(@RequestBody User user,HttpServletRequest request){
        String token = (String) request.getAttribute("token");
        User userOld = (User) request.getAttribute("user");
        if (!StringUtils.isEmpty(user.getPhone())){
            //修改手机号
            String phoneKey ="phone_"+user.getPhone();
            //根据手机号看是否存在验证码
            System.out.println(stringRedisTemplate.hasKey(phoneKey));

            if (stringRedisTemplate.hasKey(phoneKey)) throw new RuntimeException("提示：验证码已过期");
            //根据手机号获取验证码
            String code = stringRedisTemplate.opsForValue().get(phoneKey);
            if (!StringUtils.equals(code,user.getCaptcha())) throw new RuntimeException("提示:验证码输入错误");
            userOld.setPhone(user.getPhone());
        }
        //未修改手机号
        if (!StringUtils.isEmpty(user.getName()))userOld.setName(user.getName());
        if (!StringUtils.isEmpty(user.getIntro()))userOld.setIntro(user.getIntro());
        //放入redis
        user.setId(userOld.getId());
        usersService.update(user);
        redisTemplate.opsForValue().set("session_"+token,userOld,7,TimeUnit.DAYS);
        return userOld;
    }

}
