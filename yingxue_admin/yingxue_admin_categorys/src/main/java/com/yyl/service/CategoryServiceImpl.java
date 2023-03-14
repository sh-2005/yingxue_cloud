package com.yyl.service;

import com.yyl.dao.CategoryDao;
import com.yyl.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category insert(Category category) {
        category.setCreatedAt(new Date());  //设置创建时间
        category.setUpdatedAt(new Date());  //设置更新时间
        categoryDao.insert(category);
        return category;
    }

    @Override
    public Category update(Category category) {
        category.setUpdatedAt(new Date());
        categoryDao.update(category);
        return category;
    }

    @Override
    public boolean delete(Integer id) {
        return categoryDao.deleteById(id)>0;
    }
}
