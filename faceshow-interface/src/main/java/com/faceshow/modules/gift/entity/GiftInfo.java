package com.faceshow.modules.gift.entity;

import java.io.Serializable;
import java.util.Date;

public class GiftInfo implements Serializable {
    /**
     * 礼品ID主键
     */
    private Integer giftId;

    /**
     * 礼品ID主键
     */
    private Integer typeId;

    /**
     * 礼品名称
     */
    private String gname;

    /**
     * 价格
     */
    private Byte price;

    /**
     * 图片
     */
    private String img;

    /**
     * 添加人
     */
    private Integer creator;

    /**
     * 添加时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname == null ? null : gname.trim();
    }

    public Byte getPrice() {
        return price;
    }

    public void setPrice(Byte price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
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
        sb.append(", giftId=").append(giftId);
        sb.append(", typeId=").append(typeId);
        sb.append(", gname=").append(gname);
        sb.append(", price=").append(price);
        sb.append(", img=").append(img);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}