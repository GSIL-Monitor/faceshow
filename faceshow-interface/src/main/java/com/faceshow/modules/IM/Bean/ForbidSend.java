package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 *@Author:YS
 *@Description: 取消禁言和禁言 https://cloud.tencent.com/document/product/269/1627
 *@Date:2018/3/14
 *@param:No such property: code for class: Script1
 */

public class ForbidSend implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "GroupId")
    private String groupId;
    @JsonProperty(value = "Members_Account")
    private String[] members_Account ;
    @JsonProperty(value = "ShutUpTime")
    private int shutUpTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getMembers_Account() {
        return members_Account;
    }

    public void setMembers_Account(String[] members_Account) {
        this.members_Account = members_Account;
    }

    public int getShutUpTime() {
        return shutUpTime;
    }

    public void setShutUpTime(int shutUpTime) {
        this.shutUpTime = shutUpTime;
    }
}
