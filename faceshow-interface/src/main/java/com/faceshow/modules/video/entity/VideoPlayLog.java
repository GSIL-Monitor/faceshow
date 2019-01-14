package com.faceshow.modules.video.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoPlayLog implements Serializable {
    /**
     * 视频播放日志ID主键
     */
    private Integer logId;

    /**
     * 视频ID主键
     */
    private Integer videoId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 点播时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public VideoPlayLog() {
    }

    public VideoPlayLog(Integer videoId, Integer userId, Date createTime) {
        this.videoId = videoId;
        this.userId = userId;
        this.createTime = createTime;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", videoId=").append(videoId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}