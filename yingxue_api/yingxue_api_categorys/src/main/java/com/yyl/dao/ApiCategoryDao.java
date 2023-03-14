package com.yyl.dao;

import com.yyl.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiCategoryDao {
    List<Category> findAll();
    Category queryById(@Param("id") Integer id);

}
