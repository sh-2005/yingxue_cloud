package com.yyl.service;

import com.yyl.entity.User;

public interface UsersService {
    User findByPhone(String phone);
    User insert(User user);
    User update(User user);
    User queryById(Integer id);
}
