package com.faceshow.common.utils.mapbean;


import java.io.Serializable;

public class Root implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;

    private Result result;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return this.result;
    }
}
