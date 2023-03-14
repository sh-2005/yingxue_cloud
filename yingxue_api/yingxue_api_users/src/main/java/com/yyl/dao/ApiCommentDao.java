package com.yyl.dao;

import com.yyl.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiCommentDao {
    int insert(Comment comment);
}
