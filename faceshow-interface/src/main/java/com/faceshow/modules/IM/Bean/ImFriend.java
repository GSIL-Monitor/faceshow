package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImFriend implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "To_Account")
    private String to_Account;//好友的Identifier。
   /* @JsonProperty(value = "Remark")
    private String remark;//From_Account对To_Account的好友备注
    @JsonProperty(value = "GroupName")
    private String groupName;//From_Account对To_Account的分组信息*/
    @JsonProperty(value = "AddSource")
    private String addSource="AddSource_Type_China";//加好友来源字段
   /* @JsonProperty(value = "AddWording")
    private String addWording;//From_Account和To_Account形成好友关系时的附言信息*/

    public String getTo_Account() {
        return to_Account;
    }

    public void setTo_Account(String to_Account) {
        this.to_Account = to_Account;
    }

    public String getAddSource() {
        return addSource;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }
}
