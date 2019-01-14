package com.faceshow.common.utils.mapbean;

import java.io.Serializable;

/**
 * 百度地图返回值
 */
public class MapResultBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private Result result;

    public MapResultBean() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
