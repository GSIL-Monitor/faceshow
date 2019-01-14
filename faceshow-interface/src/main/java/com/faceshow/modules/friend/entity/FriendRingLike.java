package com.faceshow.modules.friend.entity;

import java.io.Serializable;
import java.util.Date;

public class FriendRingLike implements Serializable {
    /**
     * 评论点赞ID主键
     */
    private Integer likeId;

    /**
     * 朋友圈ID主键
     */
    private Integer friendId;

    /**
     * 点赞人
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 关联视频id
     */
    private Integer videoId;

    private static final long serialVersionUID = 1L;

    public Long createTimeMsec() {
        if (createTime != null) {
            return createTime.getTime();
        }
        return null;
    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", likeId=").append(likeId);
        sb.append(", friendId=").append(friendId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", videoId=").append(videoId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}