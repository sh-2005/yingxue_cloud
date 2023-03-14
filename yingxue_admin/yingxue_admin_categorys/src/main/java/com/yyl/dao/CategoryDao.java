package com.yyl.dao;

import com.yyl.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CategoryDao {
    //类别列表
    List<Category> findAll();
    //类别添加
    int insert(Category category);
    //类别修改
    int update(Category category);
    //类别删除
    int deleteById(Integer id);
}
