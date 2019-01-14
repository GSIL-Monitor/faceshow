package com.faceshow.modules.hobby.entity;

import java.io.Serializable;
import java.util.Date;

public class HobbyInfo implements Serializable {
    /**
     * 标签ID主键
     */
    private Integer hobbyId;

    /**
     * 兴趣爱好分类ID主键
     */
    private Integer typeId;

    /**
     * 标签名称
     */
    private String tags;

    /**
     * 标签类型, 0系统定义, 1用户自定义
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Integer hobbyId) {
        this.hobbyId = hobbyId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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
        sb.append(", hobbyId=").append(hobbyId);
        sb.append(", typeId=").append(typeId);
        sb.append(", tags=").append(tags);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}