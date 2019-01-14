package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoAitFriend implements Serializable {
    /**
     * 上传视频艾特好友主键ID
     */
    private Integer aitId;

    /**
     * 视频ID主键
     */
    private Integer videoId;

    /**
     * 好友ID
     */
    private Integer userId;

    /**
     * 好友昵称
     */
    private String niceName;

    /**
     * 艾特时间
     */
    private Date aitTime;

    private static final long serialVersionUID = 1L;

    public VideoAitFriend() {
    }

    public VideoAitFriend(Integer videoId, Integer userId, Date aitTime) {
        this.videoId = videoId;
        this.userId = userId;
        this.aitTime = aitTime;
    }

    public Integer getAitId() {
        return aitId;
    }

    public void setAitId(Integer aitId) {
        this.aitId = aitId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName == null ? null : niceName.trim();
    }

    public Date getAitTime() {
        return aitTime;
    }

    public void setAitTime(Date aitTime) {
        this.aitTime = aitTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", aitId=").append(aitId);
        sb.append(", videoId=").append(videoId);
        sb.append(", userId=").append(userId);
        sb.append(", niceName=").append(niceName);
        sb.append(", aitTime=").append(aitTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}