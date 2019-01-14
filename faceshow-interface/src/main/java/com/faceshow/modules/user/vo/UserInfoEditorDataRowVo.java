package com.faceshow.modules.user.vo;

import java.io.Serializable;

/**
 * 用户个人资料编辑页面返回数据
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/20 10:25
 * -------------------------------------------------------------- <br>
 */
public class UserInfoEditorDataRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户性别
     */
    private Integer sex;
    /**
     * 用户生日
     */
    private String birthday;
    /**
     * 用户个人简介
     */
    private String signature;
    /**
     * 用户现居住地址
     */
    private String nowAddress;
    /**
     * 用户职业
     */
    private String career;
    /**
     * 用户身高
     */
    private String stature;
    /**
     * 用户体重
     */
    private String weight;
    /**
     * 用户情感状态
     */
    private String emotions;
    /**
     * 用户交友意向
     */
    private String makeFriends;
    /**
     * 用户掌握的语言
     */
    private String languages;
    /**
     * 用户兴趣爱好
     */
    private String hobby;
    /**
     * 用户头像
     */
    private String img;
    /**
     * 行业名称
     */
    private String industry;
    /**
     * 公司名称
     */
    private String company;

    public UserInfoEditorDataRowVo() {
    }

    public String getIndustry() {
        return industry == null ? "" : industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature == null ? "" : signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNowAddress() {
        return nowAddress == null ? "" : nowAddress;
    }

    public void setNowAddress(String nowAddress) {
        this.nowAddress = nowAddress;
    }

    public String getCareer() {
        return career == null ? "" : career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getStature() {
        return stature == null ? "0" : stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getWeight() {
        return weight == null ? "0" : weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEmotions() {
        return emotions == null ? "" : emotions;
    }

    public void setEmotions(String emotions) {
        this.emotions = emotions;
    }

    public String getMakeFriends() {
        return makeFriends == null ? "" : makeFriends;
    }

    public void setMakeFriends(String makeFriends) {
        this.makeFriends = makeFriends;
    }

    public String getLanguages() {
        return languages == null ? "" : languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getHobby() {
        return hobby == null ? "" : hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "UserInfoEditorDataRowVo{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", signature='" + signature + '\'' +
                ", noeAddress='" + nowAddress + '\'' +
                ", career='" + career + '\'' +
                ", stature='" + stature + '\'' +
                ", weight='" + weight + '\'' +
                ", emotions='" + emotions + '\'' +
                ", makeFriends='" + makeFriends + '\'' +
                ", languages='" + languages + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
