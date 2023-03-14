package com.yyl.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yyl.entity.Comment;
import com.yyl.entity.User;

import java.util.Date;
import java.util.List;

public class VideoDetailVo {
    private Integer id;
    private String title;
    private String category;
    private String link;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;


    @JsonProperty("plays_count")
    private Integer playsCount;
    @JsonProperty("likes_count")
    private Integer likesCount;

    private Boolean liked;
    private Boolean disliked;
    private Boolean favorite;


    private User uploader;
    private List<Comment> comments;

    public VideoDetailVo() {
    }

    @Override
    public String toString() {
        return "VideoDetailVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", link='" + link + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", uploader=" + uploader +
                ", playsCount=" + playsCount +
                ", likesCount=" + likesCount +
                ", liked=" + liked +
                ", disliked=" + disliked +
                ", favorite=" + favorite +
                ", comments=" + comments +
                '}';
    }

    public VideoDetailVo(Integer id, String title, String category, String link, Date createdAt, Date updatedAt, User uploader, Integer playsCount, Integer likesCount, Boolean liked, Boolean disliked, Boolean favorite, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.link = link;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.uploader = uploader;
        this.playsCount = playsCount;
        this.likesCount = likesCount;
        this.liked = liked;
        this.disliked = disliked;
        this.favorite = favorite;
        this.comments = comments;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Integer getPlaysCount() {
        return playsCount;
    }

    public void setPlaysCount(Integer playsCount) {
        this.playsCount = playsCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getDisliked() {
        return disliked;
    }

    public void setDisliked(Boolean disliked) {
        this.disliked = disliked;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
