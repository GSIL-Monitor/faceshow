package com.faceshow.modules.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class UserInfo implements Serializable {
    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 等级ID主键
     */
    private Integer levelId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 是否VIP会员 0 否 1 是
     */
    private Byte isVip;

    /**
     * 名称
     */
    private String userName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 会员状态 0 冻结 1 正常
     */
    private Byte statues;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 0冻结1正常   输入三次支付密码错误冻结 提示修该支付密码
     */
    private Byte frozen;

    /**
     * 用户余额
     */
    private BigDecimal balance;

    /**
     * 是否是机器人账号, 0普通账号, 1机器人账号
     */
    private Byte isType;

    /**
     * 会员昵称
     */
    private String nickName;

    /**
     * 用户性别 0 女 1 男, 2未知
     */
    private Byte sex;

    /**
     * 腾讯云签名, 即时通讯使用
     */
    private String tencentSignature;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * APP头像
     */
    private String img;

    /**
     * APP头像的压缩图片
     */
    private String smallImg;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 上次登录时间
     */
    private Date lastTime;

    /**
     * 二维码图片的路径
     */
    private String qrCode;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 所在区域
     */
    private Integer countryId;

    /**
     * 具体地址
     */
    private String address;

    /**
     * 推荐人
     */
    private Integer recommendId;

    /**
     * 微信用户唯一标示
     */
    private String openid;

    /**
     * 第三方登录唯一标识
     */
    private String uid;

    /**
     * 第三方登录的token
     */
    private String token;

    /**
     * 编辑者
     */
    private Integer editor;

    /**
     * 编辑时间
     */
    private Date editorTime;

    /**
     * 0 已删除 1 未删除
     */
    private Byte del;

    /**
     * 魅力值
     */
    private Integer charmValues;

    /**
     * 关注数
     */
    private Integer attentionNum;

    /**
     * 粉丝数
     */
    private Integer fensiNum;

    /**
     * 是否是主播 0 否 1 是
     */
    private Byte isAnchor;

    /**
     * 主播编号根据是否是主播来添加
     */
    private String anchorNo;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 职业
     */
    private String job;

    /**
     * 身高
     */
    private String stature;

    /**
     * 体重
     */
    private String weight;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 维度
     */
    private String latitude;

    /**
     * 用户距离
     */
    private UserSet userSet;

    /**
     * 用户详细信息
     */
    private UserInfoDetail userInfoDetail;

    /**
     * 登录类型
     */
    private Byte type;

    /**
     * 兴趣爱好
     */
    private String hobbys;

    /**
     * 国旗
     */
    private String countryFlag;

    private Long regTimeMsec;

    private Long loginTimeMsec;

    private Long lastTimeMsec;

    private Long editorTimeMsec;

    private static final long serialVersionUID = 1L;

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }


    public Long getRegTimeMsec() {
        if (regTime != null) {
            return regTime.getTime();
        }
        return null;
    }

    public void setRegTimeMsec(Long regTimeMsec) {
        this.regTimeMsec = regTimeMsec;
    }

    public Long getLoginTimeMsec() {
        if (lastTime != null) {
            return lastTime.getTime();
        }
        return null;
    }

    public void setLoginTimeMsec(Long loginTimeMsec) {
        this.loginTimeMsec = loginTimeMsec;
    }

    public Long getLastTimeMsec() {
        if (lastTime != null) {
            return lastTime.getTime();
        }
        return null;
    }

    public void setLastTimeMsec(Long lastTimeMsec) {
        this.lastTimeMsec = lastTimeMsec;
    }

    public Long getEditorTimeMsec() {
        if (editorTime != null) {
            return editorTime.getTime();
        }
        return null;
    }

    public void setEditorTimeMsec(Long editorTimeMsec) {
        this.editorTimeMsec = editorTimeMsec;
    }

    public String getConstellation() {
        return constellation == null ? "" : constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public UserInfoDetail getUserInfoDetail() {
        return userInfoDetail;
    }

    public void setUserInfoDetail(UserInfoDetail userInfoDetail) {
        this.userInfoDetail = userInfoDetail;
    }

    public UserSet getUserSet() {
        return userSet;
    }

    public void setUserSet(UserSet userSet) {
        this.userSet = userSet;
    }

    public String getTencentSignature() {
        return tencentSignature == null ? "" : tencentSignature;
    }

    public void setTencentSignature(String tencentSignature) {
        this.tencentSignature = tencentSignature;
    }

    public String getSmallImg() {
        return smallImg == null ? "" : smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Byte getIsVip() {
        return isVip;
    }

    public void setIsVip(Byte isVip) {
        this.isVip = isVip;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPwd() {
        return pwd == null ? "" : pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Byte getStatues() {
        return statues;
    }

    public void setStatues(Byte statues) {
        this.statues = statues;
    }

    public String getPayPassword() {
        return payPassword == null ? "" : payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Byte getIsType() {
        return isType;
    }

    public void setIsType(Byte isType) {
        this.isType = isType;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature == null ? "" : signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getImg() {
        return img == null ? "" : img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getQrCode() {
        return qrCode == null ? "" : qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Integer recommendId) {
        this.recommendId = recommendId;
    }

    public String getOpenid() {
        return openid == null ? "" : openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public Byte getDel() {
        return del;
    }

    public void setDel(Byte del) {
        this.del = del;
    }

    public String getCountry() {
        return country == null ? "" : country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getCharmValues() {
        return charmValues == null ? 0 : charmValues;
    }

    public void setCharmValues(Integer charmValues) {
        this.charmValues = charmValues;
    }

    public Integer getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(Integer attentionNum) {
        this.attentionNum = attentionNum;
    }

    public Integer getFensiNum() {
        return fensiNum;
    }

    public void setFensiNum(Integer fensiNum) {
        this.fensiNum = fensiNum;
    }

    public Byte getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Byte isAnchor) {
        this.isAnchor = isAnchor;
    }

    public String getAnchorNo() {
        return anchorNo == null ? "" : anchorNo;
    }

    public void setAnchorNo(String anchorNo) {
        this.anchorNo = anchorNo == null ? null : anchorNo.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job == null ? "" : job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getStature() {
        return stature == null ? "" : stature;
    }

    public void setStature(String stature) {
        this.stature = stature == null ? null : stature.trim();
    }

    public String getWeight() {
        return weight == null ? "" : weight;
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public Byte getFrozen() {
        return frozen;
    }

    public void setFrozen(Byte frozen) {
        this.frozen = frozen;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getHobbys() {
        return hobbys;
    }

    public void setHobbys(String hobbys) {
        this.hobbys = hobbys;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", levelId=" + levelId +
                ", mobile='" + mobile + '\'' +
                ", isVip=" + isVip +
                ", userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", statues=" + statues +
                ", payPassword='" + payPassword + '\'' +
                ", frozen=" + frozen +
                ", balance=" + balance +
                ", isType=" + isType +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", tencentSignature='" + tencentSignature + '\'' +
                ", signature='" + signature + '\'' +
                ", img='" + img + '\'' +
                ", smallImg='" + smallImg + '\'' +
                ", email='" + email + '\'' +
                ", regTime=" + regTime +
                ", loginTime=" + loginTime +
                ", lastTime=" + lastTime +
                ", qrCode='" + qrCode + '\'' +
                ", country='" + country + '\'' +
                ", countryId=" + countryId +
                ", address='" + address + '\'' +
                ", recommendId=" + recommendId +
                ", openid='" + openid + '\'' +
                ", token='" + token + '\'' +
                ", editor=" + editor +
                ", editorTime=" + editorTime +
                ", del=" + del +
                ", charmValues='" + charmValues + '\'' +
                ", attentionNum=" + attentionNum +
                ", fensiNum=" + fensiNum +
                ", isAnchor=" + isAnchor +
                ", anchorNo='" + anchorNo + '\'' +
                ", birthday=" + birthday +
                ", job='" + job + '\'' +
                ", stature='" + stature + '\'' +
                ", weight='" + weight + '\'' +
                ", constellation='" + constellation + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", userSet=" + userSet +
                ", userInfoDetail=" + userInfoDetail +
                ", type=" + type +
                ", hobbys='" + hobbys + '\'' +
                ", regTimeMsec=" + regTimeMsec +
                ", loginTimeMsec=" + loginTimeMsec +
                ", lastTimeMsec=" + lastTimeMsec +
                ", editorTimeMsec=" + editorTimeMsec +
                '}';
    }
}