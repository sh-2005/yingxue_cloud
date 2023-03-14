package com.yyl.service;

import com.yyl.dao.ApiCommentDao;
import com.yyl.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ApiCommentServicelmpl implements ApiCommentService {

    @Autowired
    private ApiCommentDao apiCommentDao;


    @Override
    public int insert(Comment comment) {
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        comment.setContent(comment.getContent());
        return apiCommentDao.insert(comment);
    }
}
