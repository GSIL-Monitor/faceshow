package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserSearchKey implements Serializable {
    /**
     * 搜索ID主键
     */
    private Integer keyId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 搜索关键词名称
     */
    private String keyValue;

    /**
     * 搜索时间
     */
    private Date createTime;

    /**
     * 搜索类型 1 搜索用户 2 搜索话题 3 搜索群组 4 搜索音乐
     */
    private Byte searchType;

    private static final long serialVersionUID = 1L;

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getSearchType() {
        return searchType;
    }

    public void setSearchType(Byte searchType) {
        this.searchType = searchType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", keyId=").append(keyId);
        sb.append(", userId=").append(userId);
        sb.append(", keyValue=").append(keyValue);
        sb.append(", createTime=").append(createTime);
        sb.append(", searchType=").append(searchType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}