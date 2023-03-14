package com.yyl.dao;

import com.yyl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserDao {

    /*
    * 根据条件分页查询
    * */
    List<User> findAllKeywords(@Param("offset") int offset,@Param("limit")int limit,@Param("id")String id,@Param("name")String name,@Param("phone")String phone);
    /*
    * 根据条件查询总数
    * */
    Long findTotalCountsByKeywords(@Param("id")String id,@Param("name")String name,@Param("phone")String phone);
}
