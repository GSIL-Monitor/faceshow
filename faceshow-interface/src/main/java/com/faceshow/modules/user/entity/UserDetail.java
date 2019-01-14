package com.faceshow.modules.user.entity;

import java.io.Serializable;
/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 我的订单记录需要使用的BEAN
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.user.entity<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/4/10 19:21
 * -------------------------------------------------------------- <br>
 */
/*
*   	a.user_id,
	a.account,
	a.account_user_name,
	a.is_type,
	a.price,
	a.types,
	a.create_time,
	b.gname
*/
public class UserDetail implements Serializable {
  private Integer  userId;//赠送人(充值提现人）'
    private String userName;//赠送人名称
  private Integer account;//接收人
  private String accountUserName;//接收人名称',
  private Integer isType;//赠送类型 1 短视频 2 直播 3 一对一 4 聊天 5个人中心送礼物, 6 问答 7PK 8充值 9体现',
  private Integer price;//价格(礼品单价)'
  private Integer types;//1支付宝 2微信 4银行卡 5贝宝支付',
  private String createTime;//添加时间'
  private  String gname;//礼物名字
  private Integer payState;//充值 0失败 1成功
  private Integer applyState;//申请提现 0失败 1成功 2申请中

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPayState() {
        return payState==null?0:payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState==null?0:payState;
    }

    public Integer getApplyState() {
        return applyState==null?0:applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState==null?0:applyState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccount() {
        return account==null?0:account;
    }

    public void setAccount(Integer account) {
        this.account = account==null?0:account;
    }

    public String getAccountUserName() {
        return accountUserName==null?"":accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName==null?"":accountUserName;
    }

    public Integer getIsType() {
        return isType;
    }

    public void setIsType(Integer isType) {
        this.isType = isType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTypes() {
        return types==null?0:types;
    }

    public void setTypes(Integer types) {
        this.types = types==null?0:types;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGname() {
        return gname==null?"":gname;
    }

    public void setGname(String gname) {
        this.gname = gname==null?"":gname;
    }
}
