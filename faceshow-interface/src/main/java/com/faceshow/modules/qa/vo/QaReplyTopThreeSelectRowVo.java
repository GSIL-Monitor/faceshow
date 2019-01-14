package com.faceshow.modules.qa.vo;

import java.io.Serializable;

/**
 * 回复前三名
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/3 10:32
 * -------------------------------------------------------------- <br>
 */
public class QaReplyTopThreeSelectRowVo implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer replyId;
    private Integer userId;
    private Long createTime;
    private String nickName;

    public QaReplyTopThreeSelectRowVo() {
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "QaReplyTopThreeSelectRowVo{" +
                "replyId=" + replyId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
