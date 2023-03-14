package com.yyl.service;

import com.yyl.dao.UsersDao;
import com.yyl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class UsersServicelmpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByPhone(String phone) {
        User user = usersDao.findByPhone(phone);
        System.out.println(user);
        return user;
    }

    @Override
    public User insert(User user) {
         usersDao.insert(user);
         return user;
    }

    @Override
    public User update(User user) {
        user.setUpdatedAt(new Date());
        usersDao.update(user);
        return user;
    }

    @Override
    public User queryById(Integer id) {
        return usersDao.queryById(id);
    }


}
