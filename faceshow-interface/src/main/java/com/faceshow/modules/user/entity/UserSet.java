package com.faceshow.modules.user.entity;

import java.io.Serializable;
import java.util.Date;

public class UserSet implements Serializable {
    /**
     * 用户基本设置ID主键
     */
    private Integer setId;

    /**
     * 用户ID主键
     */
    private Integer userId;

    /**
     * 1默认(显示距离), 2不出现在附近的人上面(显示距离), 3仅好友显示距离, 4隐身
     *
     * 默认对对他人显示距离 1.默认对他人显示距离 2.勾选之后，不对他人显示距离
     */
    private Byte distance;

    /**
     * 不出现在附近 1.默认，出现在附近的人上 2.勾选之后，不出现在附近的人上
     */
    private Byte nearby;

    /**
     * 只对好友可见 1.默认对所有人都显示距离 2.勾选之后，只对好友显示距离
     */
    private Byte friends;

    /**
     * 关闭距离 1.对所有人都不显示距离不 2.所有人在附近的人都搜索不到你
     */
    private Byte closeDistance;

    /**
     * 是否屏蔽通讯录 0 屏蔽 1 正常
     */
    private Byte isAddressBook;

    /**
     * 是否隐藏我的基本资料 0 屏蔽 1 正常
     */
    private Byte basicInfo;

    /**
     * 隐藏我的兴趣 0 屏蔽 1 正常
     */
    private Byte interest;

    /**
     * 隐藏我的职业 0 屏蔽 1 正常
     */
    private Byte career;

    /**
     * 隐藏我的情感状态 0 屏蔽 1 正常
     */
    private Byte emotion;

    /**
     * 隐藏我的交友意向 0 屏蔽 1 正常
     */
    private Byte makeFriend;

    /**
     * 隐藏我掌握的语言 0 屏蔽 1 正常
     */
    private Byte language;

    /**
     * 设置时间
     */
    private Date setTime;

    private static final long serialVersionUID = 1L;

    public UserSet() {
    }

    public UserSet(Integer userId, Date setTime) {
        this.userId = userId;
        this.setTime = setTime;
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getDistance() {
        return distance;
    }

    public void setDistance(Byte distance) {
        this.distance = distance;
    }

    public Byte getNearby() {
        return nearby;
    }

    public void setNearby(Byte nearby) {
        this.nearby = nearby;
    }

    public Byte getFriends() {
        return friends;
    }

    public void setFriends(Byte friends) {
        this.friends = friends;
    }

    public Byte getCloseDistance() {
        return closeDistance;
    }

    public void setCloseDistance(Byte closeDistance) {
        this.closeDistance = closeDistance;
    }

    public Byte getIsAddressBook() {
        return isAddressBook;
    }

    public void setIsAddressBook(Byte isAddressBook) {
        this.isAddressBook = isAddressBook;
    }

    public Byte getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(Byte basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Byte getInterest() {
        return interest;
    }

    public void setInterest(Byte interest) {
        this.interest = interest;
    }

    public Byte getCareer() {
        return career;
    }

    public void setCareer(Byte career) {
        this.career = career;
    }

    public Byte getEmotion() {
        return emotion;
    }

    public void setEmotion(Byte emotion) {
        this.emotion = emotion;
    }

    public Byte getMakeFriend() {
        return makeFriend;
    }

    public void setMakeFriend(Byte makeFriend) {
        this.makeFriend = makeFriend;
    }

    public Byte getLanguage() {
        return language;
    }

    public void setLanguage(Byte language) {
        this.language = language;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", setId=").append(setId);
        sb.append(", userId=").append(userId);
        sb.append(", distance=").append(distance);
        sb.append(", nearby=").append(nearby);
        sb.append(", friends=").append(friends);
        sb.append(", closeDistance=").append(closeDistance);
        sb.append(", isAddressBook=").append(isAddressBook);
        sb.append(", basicInfo=").append(basicInfo);
        sb.append(", interest=").append(interest);
        sb.append(", career=").append(career);
        sb.append(", emotion=").append(emotion);
        sb.append(", makeFriend=").append(makeFriend);
        sb.append(", language=").append(language);
        sb.append(", setTime=").append(setTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}