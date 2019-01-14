package com.faceshow.modules.user.entity;

import java.io.Serializable;

public class UserNewsletterInfo extends UserNewsletterInfoKey implements Serializable {
    /**
     * 聊天头像
     */
    private String img;

    /**
     * 是否免打扰模式 0 免打扰 1 正常
     */
    private Byte isDisturb;

    /**
     * 所属用户(那个注册用户创建的分组信息)
     */
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Byte getIsDisturb() {
        return isDisturb;
    }

    public void setIsDisturb(Byte isDisturb) {
        this.isDisturb = isDisturb;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", img=").append(img);
        sb.append(", isDisturb=").append(isDisturb);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}