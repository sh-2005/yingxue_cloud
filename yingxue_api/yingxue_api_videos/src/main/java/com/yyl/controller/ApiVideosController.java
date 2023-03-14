package com.yyl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yyl.entity.Video;
import com.yyl.service.ApiCommentService;
import com.yyl.service.ApiVideosService;
import com.yyl.utils.JSONUtils;
import com.yyl.vo.VideoDetailVo;
import com.yyl.vo.VideoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
public class ApiVideosController {

    private static final Logger log = LoggerFactory.getLogger(ApiVideosController.class);

    @Autowired
    private ApiVideosService apiVideosService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ApiCommentService apiCommentService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /*
     * 用户播放
     * */
    @GetMapping("/video/findBy/{id}")
    public VideoVo findById(@PathVariable("id")Integer videoId){
        VideoVo videoVo = apiVideosService.findById(videoId);
        return videoVo;
    }



    /*
    *视频详情
    * */
    @GetMapping("/videos/{id}")
    public VideoDetailVo video(@PathVariable("id")Integer videoId,String token){
        log.info("当前接收到的视频id:{}",videoId);
        VideoDetailVo videoDetailVo = apiVideosService.findById(videoId,token);
        return videoDetailVo;
    }

    /*
    * 视频列表
    * */
    @GetMapping("videos")
    public List<VideoVo> videos(@RequestParam(value = "page",defaultValue = "1")int page
            ,@RequestParam(value = "per_page",defaultValue = "10") int rows
            ,@RequestParam(value = "category") Integer categoryId) throws JsonProcessingException {
        log.info("当前页:{},每页显示记录数:{},分类id:{}",page,rows,categoryId);
        List<VideoVo> categorys = apiVideosService.findCategoryId(page, rows, categoryId);
        return categorys;
    }
    /*
    * 首页视频推荐
    * */
    @GetMapping("recommends")
    public List<VideoVo> findAll(@RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "per_page",defaultValue = "10") int rows) throws JsonProcessingException {
        List<VideoVo> all = apiVideosService.findAll(page,rows);
        log.info("查看视频服务的信息:{}",all.toString());
        return all;
    }
    /*
    * 发布视频
    * */
    @PostMapping("publish")
    public Video publish(@RequestBody Video video){
        log.info("接受到的video为:{}", JSONUtils.writeValueAsString(video));
        return apiVideosService.insert(video);
    }

}
