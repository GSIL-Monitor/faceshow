package com.faceshow.modules.hobby.entity;

import java.io.Serializable;
import java.util.Date;

public class HobbyUserInfo implements Serializable {
    /**
     * 推荐ID主键
     */
    private Integer uhId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 标签ID主键
     */
    private Integer hobbyId;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getUhId() {
        return uhId;
    }

    public void setUhId(Integer uhId) {
        this.uhId = uhId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Integer hobbyId) {
        this.hobbyId = hobbyId;
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
        sb.append(", uhId=").append(uhId);
        sb.append(", userId=").append(userId);
        sb.append(", hobbyId=").append(hobbyId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}