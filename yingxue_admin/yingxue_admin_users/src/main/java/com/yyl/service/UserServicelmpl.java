package com.yyl.service;

import com.yyl.dao.UserDao;
import com.yyl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServicelmpl implements UserService {

    @Autowired
    private UserDao userDao;


    /*
    * offset 当前页   limit  每条展示的条数
    * start 起始页
    *  1    2    (1-1)*2    0*2    0
    *  2    2    (2-1)*2    1*2    2
    *  3    2    (3-1)*2    2*2    4
    *  4    2    (4-1)*2    3*2    6
    *  start
    * */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> findAllKeywords(int offset, int limit, String id, String name, String phone) {
        int start=(offset-1)*limit;
        return userDao.findAllKeywords(start,limit,id,name,phone);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long findTotalCountsByKeywords(String id, String name, String phone) {
        return userDao.findTotalCountsByKeywords(id,name,phone);
    }
}
