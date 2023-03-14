package com.yyl.dao;

import com.yyl.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ApiVideosDao {
    List<Video> findAll(@Param("page") int page, @Param("row") int row);

    int insert(Video video);

    List<Video> findCategoryId(@Param("page") int page, @Param("row") int row,@Param("categoryId") int categoryId);

    Video findById(int id);


}
