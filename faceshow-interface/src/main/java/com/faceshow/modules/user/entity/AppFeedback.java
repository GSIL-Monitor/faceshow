package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class AppFeedback implements Serializable {
    /**
     * 反馈ID主键
     */
    private Integer feedblackId;

    /**
     * 反馈人
     */
    private Integer userId;

    /**
     * 反馈问题内容
     */
    private String content;

    /**
     * 反馈时间
     */
    private Date createTime;

    /**
     * 是否处理 1 已处理 0 未处理
     */
    private Boolean isProcess;

    /**
     * 处理时间
     */
    private Date processTime;

    /**
     * 处理人
     */
    private Integer processMan;

    private static final long serialVersionUID = 1L;

    public Integer getFeedblackId() {
        return feedblackId;
    }

    public void setFeedblackId(Integer feedblackId) {
        this.feedblackId = feedblackId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsProcess() {
        return isProcess;
    }

    public void setIsProcess(Boolean isProcess) {
        this.isProcess = isProcess;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Integer getProcessMan() {
        return processMan;
    }

    public void setProcessMan(Integer processMan) {
        this.processMan = processMan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", feedblackId=").append(feedblackId);
        sb.append(", userId=").append(userId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", isProcess=").append(isProcess);
        sb.append(", processTime=").append(processTime);
        sb.append(", processMan=").append(processMan);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}