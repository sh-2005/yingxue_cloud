package com.yyl.service;

import com.yyl.dao.VideoDao;
import com.yyl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class VideoServicelmpl implements VideoService {
    @Autowired
    private VideoDao videoDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllKeywords(int offset, int limit, String id, String name, String categoryId, String username) {
        int start =(offset-1)*limit;
        return videoDao.findAllKeywords(start,limit,id,name,categoryId,username);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long findTotalCountsByKeywords(String id, String name, String categoryId, String username) {
        return videoDao.findTotalCountsByKeywords(id,name,categoryId,username);
    }
}
