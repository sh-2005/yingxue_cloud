package com.yyl.controller;

import com.yyl.entity.Category;
import com.yyl.service.ApiCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
public class ApiCategoryController {

    private static final Logger log= LoggerFactory.getLogger(ApiCategoryController.class);

    @Autowired
    private ApiCategoryService apiCategoryService;

    @GetMapping
    public List<Category> categories(){
        List<Category> all = apiCategoryService.findAll();
        log.info("总个数:{}",all.size());
        return all;
    }

    @GetMapping("{id}")
    public Category findOne(@PathVariable("id") Integer id) {
        log.info("查询到的类别id为:{}",id);
        Category category = apiCategoryService.selectOne(id);
        return category;
    }
}
