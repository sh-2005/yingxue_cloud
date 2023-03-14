package com.yyl.service;

import com.yyl.dao.ApiCategoryDao;
import com.yyl.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ApiCategoryServicelmpl implements ApiCategoryService{

    @Autowired
    private ApiCategoryDao apiCategoryDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> findAll() {
        return apiCategoryDao.findAll();
    }

    @Override
    public Category selectOne(Integer id) {
        return apiCategoryDao.queryById(id);
    }
}
