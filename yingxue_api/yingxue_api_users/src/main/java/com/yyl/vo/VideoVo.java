package com.yyl.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class VideoVo {
    private Integer id;
    private String title;
    private String cover;
    private String category;
    private Integer likes;
    private String uploader;
    @JsonProperty("created_at")
    private Date createdAt;

    public VideoVo() {
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "VideoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", category='" + category + '\'' +
                ", likes=" + likes +
                ", uploader='" + uploader + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public VideoVo(Integer id, String title, String cover, String category, Integer likes, String uploader, Date createdAt) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.category = category;
        this.likes = likes;
        this.uploader = uploader;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

}
