package com.yyl.dao;

import com.yyl.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersDao {

    User findByPhone(String phone);

    int insert(User user);

    int update(User user);

    User queryById(Integer id);
}
