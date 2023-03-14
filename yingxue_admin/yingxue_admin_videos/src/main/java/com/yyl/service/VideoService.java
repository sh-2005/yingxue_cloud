package com.yyl.service;

import com.yyl.entity.User;

import java.util.List;

public interface VideoService {
    /*
     * 根据条件分页查询
     * */
    List<User> findAllKeywords(int offset,int limit,String id,String name,String categoryId,String username);
    /*
     * 根据条件查询总数
     * */
    Long findTotalCountsByKeywords(String id,String name,String categoryId,String username);
}
