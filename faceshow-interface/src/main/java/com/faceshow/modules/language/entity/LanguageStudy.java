package com.faceshow.modules.language.entity;

import java.io.Serializable;
import java.util.Date;

public class LanguageStudy implements Serializable {
    /**
     * 学习中的语言ID主键
     */
    private Integer studyId;

    /**
     * 用户ID主键
     */
    private Integer userId;

    /**
     * 国别语言
     */
    private String language;

    /**
     * 添加时间
     */
    private Date creatorTime;

    private static final long serialVersionUID = 1L;

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studyId=").append(studyId);
        sb.append(", userId=").append(userId);
        sb.append(", language=").append(language);
        sb.append(", creatorTime=").append(creatorTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}