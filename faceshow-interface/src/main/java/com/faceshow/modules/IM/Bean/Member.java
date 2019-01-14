package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
/**
 *@Author:YS
 *@Description: 群成员
 *@Date:2018/3/14
 *@param:No such property: code for class: Script1
 */

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "ActionStatus")
    private String actionStatus;
    @JsonProperty(value = "ErrorCode")
    private int errorCode;
    @JsonProperty(value = "GroupInfo")
    private List<GroupInfo> groupInfo ;

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<GroupInfo> getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(List<GroupInfo> groupInfo) {
        this.groupInfo = groupInfo;
    }

    @Override
    public String toString() {
        return "Member{" +
                "actionStatus='" + actionStatus + '\'' +
                ", errorCode=" + errorCode +
                ", groupInfo=" + groupInfo +
                '}';
    }
}
