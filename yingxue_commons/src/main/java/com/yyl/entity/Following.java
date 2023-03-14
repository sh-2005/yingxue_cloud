package com.yyl.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 关注(Following)实体类
 *
 * @author makejava
 * @since 2022-05-12 17:31:37
 */
public class Following implements Serializable {
    private static final long serialVersionUID = -69565612815216605L;
    
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 被关注用户id
     */
    private Integer followingId;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private Date deletedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    public Date getCreatedAt(Date date) {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

}

