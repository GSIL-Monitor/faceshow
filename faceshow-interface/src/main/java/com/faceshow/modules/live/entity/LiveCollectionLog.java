package com.faceshow.modules.live.entity;

import java.io.Serializable;
import java.util.Date;

public class LiveCollectionLog implements Serializable {
    /**
     * 用户直播点赞(收藏)日志ID主键
     */
    private Integer logId;

    /**
     * 用户直播主键ID
     */
    private Integer liveId;

    /**
     * 点赞(收藏)会员ID主键
     */
    private Integer userId;

    /**
     * 点赞(收藏)时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
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
        sb.append(", liveId=").append(liveId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}