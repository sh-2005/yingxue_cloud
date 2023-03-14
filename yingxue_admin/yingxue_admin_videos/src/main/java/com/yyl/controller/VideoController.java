package com.yyl.controller;

import com.yyl.entity.User;
import com.yyl.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("videos")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @GetMapping
    public HashMap<String,Object> videos(@RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "per_page",defaultValue = "5")Integer rows,
                          String id,     //视频id
                          String name,  //视频名称
                          @RequestParam(value = "category_id",required = false)String categoryId,
                          @RequestParam(value = "uploader_name",required = false)String username){
        log.info("当前页为:{}",page);
        log.info("每页显示记录书：{}",rows);
        log.info("搜索条件id是否存在:{},id为;{}", !StringUtils.isEmpty(id),id);
        log.info("搜索条件name是否存在:{},name为;{}", !StringUtils.isEmpty(name),name);
        log.info("搜索条件category_id是否存在:{},category_id为;{}", !StringUtils.isEmpty(categoryId),categoryId);
        log.info("搜索条件uploader_name是否存在:{},uploader_name为;{}", !StringUtils.isEmpty(username),username);
        HashMap<String, Object> hashMap = new HashMap<>();
        Long totalCountsByKeywords = videoService.findTotalCountsByKeywords(id, name, categoryId, username);
        List<User> allKeywords = videoService.findAllKeywords(page, rows, id, name, categoryId, username);
        hashMap.put("total_count",totalCountsByKeywords);
        hashMap.put("items",allKeywords);
        log.info("符合条件的总数:{}",totalCountsByKeywords);
        log.info("tiems:{}",allKeywords);
        return hashMap;
    }
}
