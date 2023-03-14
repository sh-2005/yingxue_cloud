/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50729
Source Host           : localhost:3306
Source Database       : yingxue

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2023-03-14 13:13:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级分类id',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_category_1` (`parent_id`),
  CONSTRAINT `fk_category_category_1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='分类';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('29', '光', null, '2022-06-15 21:27:40', '2022-06-15 21:27:40', null);
INSERT INTO `category` VALUES ('30', '迪迦', '29', '2022-06-15 21:34:38', '2022-06-15 21:34:38', null);
INSERT INTO `category` VALUES ('32', '修勾', null, '2022-06-15 21:50:45', '2022-06-15 21:50:45', null);
INSERT INTO `category` VALUES ('33', '可爱的修勾', '29', '2022-06-15 21:51:22', '2022-06-15 21:51:22', null);
INSERT INTO `category` VALUES ('34', '傻傻的修勾', '32', '2022-06-15 21:51:36', '2022-06-15 21:51:36', null);
INSERT INTO `category` VALUES ('37', '抖音', null, '2022-06-16 09:15:31', '2022-06-16 09:15:31', null);
INSERT INTO `category` VALUES ('38', '修勾', '37', '2022-06-16 09:15:41', '2022-06-16 09:15:41', null);
INSERT INTO `category` VALUES ('40', '迪迦', null, '2022-06-17 14:01:05', '2022-06-17 14:01:05', null);
INSERT INTO `category` VALUES ('41', '光', '40', '2022-06-17 14:01:12', '2022-06-17 14:01:12', null);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `video_id` int(11) NOT NULL COMMENT '视频id',
  `content` text NOT NULL COMMENT '内容',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comment_user_1` (`uid`),
  KEY `fk_comment_video_1` (`video_id`),
  CONSTRAINT `fk_comment_user_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_comment_video_1` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论';

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `video_id` int(11) NOT NULL COMMENT '视频id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_favorite_user_1` (`uid`),
  KEY `fk_favorite_video_1` (`video_id`),
  CONSTRAINT `fk_favorite_user_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_favorite_video_1` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏';

-- ----------------------------
-- Records of favorite
-- ----------------------------

-- ----------------------------
-- Table structure for following
-- ----------------------------
DROP TABLE IF EXISTS `following`;
CREATE TABLE `following` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `following_id` int(11) NOT NULL COMMENT '被关注用户id',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_following_user_1` (`uid`),
  KEY `fk_following_user_2` (`following_id`),
  CONSTRAINT `fk_following_user_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_following_user_2` FOREIGN KEY (`following_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注';

-- ----------------------------
-- Records of following
-- ----------------------------

-- ----------------------------
-- Table structure for played
-- ----------------------------
DROP TABLE IF EXISTS `played`;
CREATE TABLE `played` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户id',
  `video_id` int(11) NOT NULL COMMENT '视频id',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_played_user_1` (`uid`),
  KEY `fk_played_video_1` (`video_id`),
  CONSTRAINT `fk_played_user_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_played_video_1` FOREIGN KEY (`video_id`) REFERENCES `video` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='播放历史';

-- ----------------------------
-- Records of played
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL COMMENT '用户名',
  `avatar` varchar(256) NOT NULL COMMENT '头像链接',
  `intro` varchar(256) NOT NULL COMMENT '简介',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `phone_linked` tinyint(1) NOT NULL COMMENT '是否绑定手机号',
  `openid` varchar(28) DEFAULT NULL COMMENT '微信openid',
  `wechat_linked` tinyint(1) NOT NULL COMMENT '是否绑定微信',
  `following_count` int(11) NOT NULL COMMENT '关注数',
  `followers_count` int(11) NOT NULL COMMENT '粉丝数',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('9', '光', '', '一个很懒博啥的主', '18295742407', '1', null, '0', '0', '0', '2022-06-15 21:47:21', '2023-02-17 10:15:23', null);
INSERT INTO `user` VALUES ('10', '17838618103', '', '一个很懒得博主', '17838618103', '1', null, '0', '0', '0', '2022-06-17 14:03:44', '2022-06-17 14:03:56', null);

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL COMMENT '标题',
  `intro` varchar(256) NOT NULL COMMENT '简介',
  `uid` int(11) NOT NULL COMMENT 'up主id',
  `cover` varchar(256) NOT NULL COMMENT '视频封面链接',
  `link` varchar(256) NOT NULL COMMENT '视频播放链接',
  `category_id` int(11) NOT NULL COMMENT '分类id',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_video_user_1` (`uid`),
  KEY `fk_video_tag_1` (`category_id`),
  CONSTRAINT `fk_video_tag_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_video_user_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='视频';

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('27', '来自光的力量', '你可以不相信爱情，但你一定要相信光！', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/bc42995b874944c8b37ce8e0f53d9613.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/bc42995b874944c8b37ce8e0f53d9613.mp4', '30', '2022-06-16 08:56:02', '2022-06-16 08:56:02', null);
INSERT INTO `video` VALUES ('28', '一只无忧无虑的修勾', '一只无忧无虑的修勾', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/1f8605f6faf840fda7a07579223caf8f.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/1f8605f6faf840fda7a07579223caf8f.mp4', '33', '2022-06-16 08:56:26', '2022-06-16 08:56:26', null);
INSERT INTO `video` VALUES ('31', '一只傻傻的修勾', '一只傻傻的修勾', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/02d812efb65c4618b03d577e3197841f.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/02d812efb65c4618b03d577e3197841f.mp4', '34', '2022-06-16 08:57:17', '2022-06-16 08:57:17', null);
INSERT INTO `video` VALUES ('32', '一只傻傻的修勾', '一只傻傻的修勾', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/83bfb279fe574d0f8e33e8691d966c6d.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/83bfb279fe574d0f8e33e8691d966c6d.mp4', '38', '2022-06-16 09:17:36', '2022-06-16 09:17:36', null);
INSERT INTO `video` VALUES ('33', '来自光的力量', '来自光的力量', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/b82210ff67db42e78a356b2ae601ce27.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/b82210ff67db42e78a356b2ae601ce27.mp4', '41', '2022-06-17 14:02:31', '2022-06-17 14:02:31', null);
INSERT INTO `video` VALUES ('34', '朗读sssss', '朗读', '9', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/cover/dbda047aacba4b78a73c1f237996f77e.jpg', 'https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/video/dbda047aacba4b78a73c1f237996f77e.mp4', '30', '2023-02-17 10:35:50', '2023-02-17 10:35:50', null);
