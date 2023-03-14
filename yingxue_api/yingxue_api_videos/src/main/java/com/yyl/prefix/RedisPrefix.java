package com.yyl.prefix;

public interface RedisPrefix {
    String USER_LIKE_PREFIX="USER_LIKE_";  //用户喜欢视频列表前缀
    String USER_DISLIKE_PREFIX="USER_DISLIKE_";//用户不喜欢的列表

    String VIDEO_LIKE_COUNT_PREFIX="VIDEO_LIKE_COUNT_";//视频点赞次数前缀

    String VIDEO_PLAYED_COUNT_PREFIX="VIDEO_PLAYED_COUNT_";//视频视频播放次数前缀

    String VIDEO_FAVORITES_PREFIX="VIDEO_FAVORITES_"; //用户收藏视频信息列表前缀
    String USER_FAVORITES_PREFIX="USER_FAVORITES_";   //用户收藏视频列表前缀(只是作为判断用处)
    String VIDEO_PLAYED_PREFIX="VIDEO_PLAYED_";
}
