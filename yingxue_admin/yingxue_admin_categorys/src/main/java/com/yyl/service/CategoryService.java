package com.yyl.service;

import com.yyl.entity.Category;

import java.util.List;

public interface CategoryService {
    //类别列表
    List<Category> findAll();
    //添加类别  参数:接收前端传递给我本地保存类别   返回一个保存之后完整类别对象
    Category insert(Category category);
    //修改类别
    Category update(Category category);
    //删除类别
    boolean delete(Integer id);
}
