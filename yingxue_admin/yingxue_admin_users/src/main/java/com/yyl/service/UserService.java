package com.yyl.service;

import com.yyl.entity.User;

import java.util.List;

public interface UserService {
    /*
     * 根据条件分页查询
     * */
    List<User> findAllKeywords(int offset,int limit,String id,String name,String phone);
    /*
     * 根据条件查询总数
     * */
    Long findTotalCountsByKeywords(String id,String name,String phone);
}
