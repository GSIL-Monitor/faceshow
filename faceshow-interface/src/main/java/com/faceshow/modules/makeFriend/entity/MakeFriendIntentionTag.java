package com.faceshow.modules.makeFriend.entity;

import java.io.Serializable;

public class MakeFriendIntentionTag implements Serializable {
    /**
     * 交友意向标签ID主键
     */
    private Integer tagId;

    /**
     * 标签名称
     */
    private Integer tname;

    /**
     * 是否用户自定义 0 系统定义 1 用户自定义
     */
    private Byte isType;

    private static final long serialVersionUID = 1L;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTname() {
        return tname;
    }

    public void setTname(Integer tname) {
        this.tname = tname;
    }

    public Byte getIsType() {
        return isType;
    }

    public void setIsType(Byte isType) {
        this.isType = isType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tagId=").append(tagId);
        sb.append(", tname=").append(tname);
        sb.append(", isType=").append(isType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}