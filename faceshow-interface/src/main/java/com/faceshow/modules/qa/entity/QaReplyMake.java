package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaReplyMake implements Serializable {
    /**
     * 问答回复点赞ID主键
     */
    private Integer makeId;

    /**
     * 问答回复ID主键
     */
    private Integer replyId;

    /**
     * 点赞人
     */
    private Integer userId;

    /**
     * 点赞时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getMakeId() {
        return makeId;
    }

    public void setMakeId(Integer makeId) {
        this.makeId = makeId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
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
        sb.append(", makeId=").append(makeId);
        sb.append(", replyId=").append(replyId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}