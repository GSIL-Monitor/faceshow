package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ImFriendAdd implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "From_Account")
    private String from_Account;// 	需要为该Identifier添加好友。
    @JsonProperty(value = "AddType")
    private String addType="Add_Type_Both";//加好友方式（默认双向加好友方式）： "Add_Type_Single"表示单向加好友； "Add_Type_Both"表示双向加好友。
    @JsonProperty(value = "ForceAddFlags")
    private Integer forceAddFlags =1;//管理员强制加好友标记：1表示强制加好友；0表示常规加好友方式。
    @JsonProperty(value = "AddFriendItem")
    private List<ImFriend> addFriendItem;//好友结构体对象。

    public String getFrom_Account() {
        return from_Account;
    }

    public void setFrom_Account(String from_Account) {
        this.from_Account = from_Account;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public Integer getForceAddFlags() {
        return forceAddFlags;
    }

    public void setForceAddFlags(Integer forceAddFlags) {
        this.forceAddFlags = forceAddFlags;
    }

    public List<ImFriend> getAddFriendItem() {
        return addFriendItem;
    }

    public void setAddFriendItem(List<ImFriend> addFriendItem) {
        this.addFriendItem = addFriendItem;
    }
}
