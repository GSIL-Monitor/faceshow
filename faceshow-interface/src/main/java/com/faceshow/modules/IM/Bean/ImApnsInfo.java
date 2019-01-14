package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImApnsInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty
    private String Sound;//离线推送声音文件路径。
    @JsonProperty
    private  Integer BadgeMode;//这个字段缺省或者为 0 表示需要计数，为 1 表示本条消息不需要计数，即右上角图标数字不增加
    @JsonProperty
    private String Title;//该字段用于标识apns推送的标题
    @JsonProperty
    private String SubTitle;//该字段用于标识apns推送的子标题
    @JsonProperty
    private String Image;//该字段用于标识apns携带的图片地址，当客户端拿到该字段时，可以通过下载图片资源的方式将图片展示在弹窗上
    @JsonIgnore
    public String getSound() {
        return Sound;
    }
    @JsonIgnore
    public void setSound(String sound) {
        Sound = sound;
    }
    @JsonIgnore
    public String getTitle() {
        return Title;
    }
    @JsonIgnore
    public void setTitle(String title) {
        Title = title;
    }
    @JsonIgnore
    public String getSubTitle() {
        return SubTitle;
    }
    @JsonIgnore
    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }
    @JsonIgnore
    public String getImage() {
        return Image;
    }
    @JsonIgnore
    public void setImage(String image) {
        Image = image;
    }
    @JsonIgnore
    public Integer getBadgeMode() {
        return BadgeMode;
    }
    @JsonIgnore
    public void setBadgeMode(Integer badgeMode) {
        BadgeMode = badgeMode;
    }
}
