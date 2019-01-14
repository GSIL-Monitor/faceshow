package com.faceshow.modules.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/*
	a.constellation,
	a.img,
	a.nick_name,
	a.user_name,
	a.charm_values,
	a.attention_num,
	a.fensi_num,
	a.signature,
	a.sex * 1 sex,
	a.birthday,
	a.stature,
	a.weight,
	a.job,
	c.cname,
	SUM(b.numbers) total
*/
public class PersonalHome implements Serializable {//不要用驼峰法 移动端写好了已经
    private String constellation;//星座
    private String img;//头像
    @JsonProperty(value = "nick_name")
    private String nickName;//昵称
    @JsonProperty(value = "user_name")
    private String userName;//名称
    @JsonProperty(value = "charm_values")
    private String charmValues;//魅力值
    @JsonProperty(value = "attention_num")
    private Long attentionNum;//关注数
    @JsonProperty(value = "fensi_num")
    private Long fensiNum;//粉丝数
    private String signature;//签名
    private Integer sex;//性别
    private String birthday;//生日
    private String stature;//身高
    private String weight;//体重
    private String job;//职业
    private String cname;//城市名
    private Long total;//礼物数量
    @JsonProperty(value = "is_tutual")
    private Integer isTutual;//0代表我关注了人家 人家没有关注我1相互关注2互不关注   3人家关注了我 我没有关注人家

    public String getNickName() {
        return nickName==null?"":nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName==null?"":userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCharmValues() {
        return charmValues==null?"":charmValues;
    }

    public void setCharmValues(String charmValues) {
        this.charmValues = charmValues;
    }

    public Long getAttentionNum() {
        return attentionNum==null?0:attentionNum;
    }

    public void setAttentionNum(Long attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Long getFensiNum() {
        return fensiNum==null?0:fensiNum;
    }

    public void setFensiNum(Long fensiNum) {
        this.fensiNum = fensiNum;
    }

    public Integer getIsTutual() {
        return isTutual==null?6:isTutual;
    }

    public void setIsTutual(Integer isTutual) {
        this.isTutual = isTutual;
    }

    public Integer getSex() {
        return sex==null?0:sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getConstellation() {
        return constellation==null?"":constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getImg() {
        return img==null?"":img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getSignature() {
        return signature==null?"":signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBirthday() {
        return birthday==null?"":birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStature() {
        return stature==null?"":stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getWeight() {
        return weight==null?"":weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJob() {
        return job==null?"":job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCname() {
        return cname==null?"":cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getTotal() {
        return total==null?0:total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
