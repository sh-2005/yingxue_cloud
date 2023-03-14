package com.yyl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yyl.entity.Video;
import com.yyl.vo.VideoDetailVo;
import com.yyl.vo.VideoVo;

import java.util.List;

public interface ApiVideosService {
    List<VideoVo> findAll(int page, int row) throws JsonProcessingException;
    Video insert(Video video);
    List<VideoVo> findCategoryId(Integer page, Integer row,Integer categoryId) throws JsonProcessingException;
    VideoDetailVo findById(int id, String token);
    VideoVo findById(int id);
}
