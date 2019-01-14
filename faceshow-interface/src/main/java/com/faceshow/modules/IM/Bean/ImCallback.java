package com.faceshow.modules.IM.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImCallback implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(value = "ActionStatus")
    private  String actionStatus="OK";
    @JsonProperty(value = "ErrorInfo")
    private  String errorInfo="";
    @JsonProperty(value = "ErrorCode")
    private Integer errorCode=0;

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
