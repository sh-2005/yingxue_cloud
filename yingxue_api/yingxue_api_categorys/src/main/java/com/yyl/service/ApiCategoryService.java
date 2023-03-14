package com.yyl.service;

import com.yyl.entity.Category;

import java.util.List;

public interface ApiCategoryService {
    List<Category> findAll();
    Category selectOne(Integer id);
}
