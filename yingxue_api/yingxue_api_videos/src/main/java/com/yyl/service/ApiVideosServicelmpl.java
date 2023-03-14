package com.yyl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyl.dao.ApiVideosDao;
import com.yyl.entity.Category;
import com.yyl.entity.User;
import com.yyl.entity.Video;
import com.yyl.feignclients.CategoryClients;
import com.yyl.feignclients.UserClient;
import com.yyl.prefix.RedisPrefix;
import com.yyl.utils.JSONUtils;
import com.yyl.vo.VideoDetailVo;
import com.yyl.vo.VideoVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ApiVideosServicelmpl implements ApiVideosService{

    private static  final Logger log= LoggerFactory.getLogger(ApiVideosServicelmpl.class);
    @Autowired
    private ApiVideosDao apiVideosDao;

    @Autowired
    private UserClient userClinents;

    @Autowired
    private CategoryClients categoryClients;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<VideoVo> findAll(int page,int row) throws JsonProcessingException {
        int start=(page-1)*row;
        List<Video> all = apiVideosDao.findAll(start,row);
        log.info("视频信息为:{}",new ObjectMapper().writeValueAsString(all));
        return getList(all);
    }

    /*
    * 添加视频
    * */
    @Override
    public Video insert(Video video) {
        video.setCreatedAt(new Date());
        video.setUpdatedAt(new Date());
        apiVideosDao.insert(video);
        rabbitTemplate.convertAndSend("videos","",JSONUtils.writeValueAsString(getVideoVo(video)));
        return video;
    }

    @Override
    public List<VideoVo> findCategoryId(Integer page, Integer row, Integer categoryId) throws JsonProcessingException {
        int start=(page-1)*row;
        List<Video> categoryId1 = apiVideosDao.findCategoryId(start,row,categoryId);
        return getList(categoryId1);
    }


    /*
    * 视频详情
    * */
    @Override
    public VideoDetailVo findById(int id,String token) {
        Video video = apiVideosDao.findById(id);
        VideoDetailVo videoDetailVo = new VideoDetailVo();
        BeanUtils.copyProperties(video,videoDetailVo);
        //设置类别
        Category category = categoryClients.findOne(video.getCategoryId());
        videoDetailVo.setCategory(category.getName());
        //设置用户信息
        User user = userClinents.user(video.getUid());
        videoDetailVo.setUploader(user);
        //设置点赞次数
        videoDetailVo.setLikesCount(0);
        String likeCounts = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_LIKE_COUNT_PREFIX+video.getId());
        if (!StringUtils.isEmpty(likeCounts)){
            videoDetailVo.setLikesCount(Integer.valueOf(likeCounts));
        }
        //设置播放次数
        videoDetailVo.setPlaysCount(0);
        String playCounts = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_PLAYED_COUNT_PREFIX +video.getId());
        if (!StringUtils.isEmpty(playCounts)){
            videoDetailVo.setPlaysCount(Integer.valueOf(playCounts));
        }
        //判断用户是否登录
        String tokenKey ="session_"+token;
        log.info("tokenKey:{}",tokenKey);
        User o = (User) redisTemplate.opsForValue().get(tokenKey);
        System.out.println("用户信息为:"+o);
       //不为空  以登录
        if (!ObjectUtils.isEmpty(o)){
            //设置是否喜欢
            Boolean liked = stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_LIKE_PREFIX + o.getId(),video.getId().toString());
            videoDetailVo.setLiked(liked);
            //设置是否不喜欢
            Boolean disliked = stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_DISLIKE_PREFIX + o.getId(),video.getId().toString());
            videoDetailVo.setDisliked(disliked);
            //设置是否收藏
            Boolean favorite = stringRedisTemplate.opsForSet().isMember(RedisPrefix.USER_FAVORITES_PREFIX + o.getId(), video.getId().toString());
            videoDetailVo.setFavorite(favorite);
        }
        return videoDetailVo;
    }

    @Override
    public VideoVo findById(int id) {
        Video video = apiVideosDao.findById(id);
        return getVideoVo(video);
    }

    public List<VideoVo> getList(List<Video> videos){
        List<VideoVo> videoVos=new ArrayList<>();
        videos.forEach(video ->{
                VideoVo videVo = getVideoVo(video);
                videoVos.add(videVo);
        });
        return videoVos;
    }

    public VideoVo getVideoVo(Video video){
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(video,videoVo);
        //up主信息
        User user = userClinents.user(video.getUid());
        videoVo.setUploader(user.getName());
        //类别信息
        Category category = categoryClients.findOne(video.getCategoryId());
        videoVo.setCategory(category.getName());
        videoVo.setLikes(0);
        return videoVo;
    }
}
