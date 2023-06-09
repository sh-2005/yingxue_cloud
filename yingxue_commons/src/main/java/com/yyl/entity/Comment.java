package com.yyl.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 评论(Comment)实体类
 *
 * @author makejava
 * @since 2022-05-12 17:31:37
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = -80112860601183427L;
    
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 视频id
     */
    private Integer videoId;
    /**
     * 内容
     */
    private String content;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
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

