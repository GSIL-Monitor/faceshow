package com.faceshow.modules.qa.entity;

import java.io.Serializable;
import java.util.Date;

public class QaView implements Serializable {
    /**
     * 问答查看ID主键
     */
    private Integer viewId;

    /**
     * 问答信息ID主键
     */
    private Integer infoId;

    /**
     * 查看人
     */
    private Integer userId;

    /**
     * 查看时间
     */
    private Date createTime;

    /**
     * 是否有新回答 0 无, 1有
     */
    private Byte isNewAnswer;

    private static final long serialVersionUID = 1L;

    public Integer getViewId() {
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
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

    public Byte getIsNewAnswer() {
        return isNewAnswer;
    }

    public void setIsNewAnswer(Byte isNewAnswer) {
        this.isNewAnswer = isNewAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", viewId=").append(viewId);
        sb.append(", infoId=").append(infoId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", isNewAnswer=").append(isNewAnswer);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}