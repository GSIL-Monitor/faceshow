package com.faceshow.modules.video.vo;

import java.io.Serializable;

/**
 * 解析视频简介使用
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/26 15:12
 * -------------------------------------------------------------- <br>
 */
public class VideoIntroduction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private Integer type;
    /**
     * id
     */
    private String userId;

    public VideoIntroduction() {
    }

    public VideoIntroduction(String content, Integer type, String userId) {
        this.content = content;
        this.type = type;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VideoIntroduction{" +
                "content='" + content + '\'' +
                ", type=" + type +
                ", userId='" + userId + '\'' +
                '}';
    }
}
