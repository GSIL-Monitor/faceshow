package com.faceshow.modules.video.vo;

import java.io.Serializable;

/**
 * 分页查询当前短视频的评论信息(一级评论信息) 返回值
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/3 8:42
 * -------------------------------------------------------------- <br>
 */
public class VideoCommentSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer commentId;
    private Integer videoId;
    private Integer userId;
    private Integer parentCommentId;
    private String comment;
    private Integer likeNum;
    private Long createTime;
    private String userImg;
    private String isCommentator;
    private String byCommentator;
    private Integer byCommentatorId;

    private Integer isLike;

    public Integer getIsLike() {
        return isLike;
    }

    public void setIsLike(Integer isLike) {
        this.isLike = isLike;
    }

    public VideoCommentSelectRowVo() {
    }

    public Integer getCommentId() {
        return commentId == null ? 0 : commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getVideoId() {
        return videoId == null ? 0 : videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId == null ? 0 : userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getParentCommentId() {
        return parentCommentId == null ? 0 : parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getComment() {
        return comment == null ? "" : comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikeNum() {
        return likeNum == null ? 0 : likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Long getCreateTime() {
        return createTime == null ? 0 : createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUserImg() {
        return userImg == null ? "" : userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getIsCommentator() {
        return isCommentator == null ? "" : isCommentator;
    }

    public void setIsCommentator(String isCommentator) {
        this.isCommentator = isCommentator;
    }

    public String getByCommentator() {
        return byCommentator == null ? "" : byCommentator;
    }

    public void setByCommentator(String byCommentator) {
        this.byCommentator = byCommentator;
    }

    public Integer getByCommentatorId() {
        return byCommentatorId == null ? 0 : byCommentatorId;
    }

    public void setByCommentatorId(Integer byCommentatorId) {
        this.byCommentatorId = byCommentatorId;
    }

    @Override
    public String toString() {
        return "VideoCommentSelectRowVo{" +
                "commentId=" + commentId +
                ", videoId=" + videoId +
                ", userId=" + userId +
                ", parentCommentId=" + parentCommentId +
                ", comment='" + comment + '\'' +
                ", likeNum=" + likeNum +
                ", createTime=" + createTime +
                ", userImg='" + userImg + '\'' +
                ", isCommentator='" + isCommentator + '\'' +
                ", byCommentator='" + byCommentator + '\'' +
                ", byCommentatorId=" + byCommentatorId +
                '}';
    }
}
