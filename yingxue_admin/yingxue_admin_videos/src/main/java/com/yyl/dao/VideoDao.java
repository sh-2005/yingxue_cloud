package com.yyl.dao;

import com.yyl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface VideoDao {
    /*
     * 根据条件分页查询
     * */
    List<User> findAllKeywords(@Param("offset") int offset, @Param("limit")int limit,@Param("id")String id, @Param("title")String name,@Param("categoryId")String categoryId,@Param("username")String username);
    /*
     * 根据条件查询总数
     * */
    Long findTotalCountsByKeywords(@Param("id")String id, @Param("title")String name,@Param("categoryId")String categoryId,@Param("username")String username);
}
