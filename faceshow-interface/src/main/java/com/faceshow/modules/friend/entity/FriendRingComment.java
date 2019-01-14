package com.faceshow.modules.friend.entity;

import java.io.Serializable;
import java.util.Date;

public class FriendRingComment implements Serializable {
    /**
     * 评论点赞ID主键
     */
    private Integer commentId;

    /**
     * 朋友圈ID主键
     */
    private Integer friendId;

    /**
     * 父id
     */
    private Integer parentCommentId;

    /**
     * 评论人
     */
    private Integer userId;

    /**
     * 评论点赞数 默认是 0
     */
    private Integer likeNum;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 是否审核通过 0 未通过 1 已通过
     */
    private Boolean isCheck;

    /**
     * 关联视频评论id
     */
    private Integer videoCommentId;

    private static final long serialVersionUID = 1L;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public Integer getVideoCommentId() {
        return videoCommentId;
    }

    public void setVideoCommentId(Integer videoCommentId) {
        this.videoCommentId = videoCommentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", friendId=").append(friendId);
        sb.append(", parentCommentId=").append(parentCommentId);
        sb.append(", userId=").append(userId);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", isCheck=").append(isCheck);
        sb.append(", videoCommentId=").append(videoCommentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}