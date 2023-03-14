package com.yyl.controller;

import com.yyl.service.CategoryService;
import com.yyl.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   //底层使用jackson转换json
@RequestMapping("categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    //删除分类
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id){
        log.info("删除类别的id:{}",id);
        boolean status = categoryService.delete(id);
        log.info("当前类别为:{},本次删除是否成功:{}",id,status);
    }


    //修改分类
    @PatchMapping("{id}")
    public Category update(@PathVariable("id") String id,@RequestBody Category category){
        log.info("修改的类别id:{},修改类别名称：{},修改父类别的id",id,category.getName(),category.getParentId());
        category.setId(Integer.valueOf(id));
        category = categoryService.update(category);
        log.info("修改成功返回的类别对象:{}",category);
        return category;
    }

    //保存分类
    @PostMapping
    public Category save(@RequestBody Category category){
        log.info("分类名称:{},分类别id:{}",category.getName(),category.getParentId());
        category = categoryService.insert(category);
        log.info("保存成功返回的类别对象:{}",category);
        return category;
    }

    //分类列表
   @GetMapping
    public List<Category> categories(){
       List<Category> all = categoryService.findAll();
       log.info("当前一级类别总数为:{}",all.size());
        return all;
   }

}
