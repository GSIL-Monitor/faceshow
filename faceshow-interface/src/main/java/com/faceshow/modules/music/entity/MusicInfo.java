package com.faceshow.modules.music.entity;

import java.io.Serializable;
import java.util.Date;

public class MusicInfo implements Serializable {
    /**
     * 推荐ID主键
     */
    private Integer musicId;

    /**
     * 音乐类型
     */
    private Integer typeId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 音乐地址
     */
    private String url;

    /**
     * 音乐名称
     */
    private String mname;

    /**
     * 音乐图片
     */
    private String img;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 使用次数
     */
    private Integer numbers;

    /**
     * 音乐介绍
     */
    private String introduce;

    private static final long serialVersionUID = 1L;

    public Integer getMusicId() {
        return musicId;
    }

    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", musicId=").append(musicId);
        sb.append(", typeId=").append(typeId);
        sb.append(", userId=").append(userId);
        sb.append(", url=").append(url);
        sb.append(", mname=").append(mname);
        sb.append(", img=").append(img);
        sb.append(", createTime=").append(createTime);
        sb.append(", country=").append(country);
        sb.append(", numbers=").append(numbers);
        sb.append(", introduce=").append(introduce);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}