package com.faceshow.modules.job.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 职业信息返回值
 */
public class JobInfoSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行业信息id
     */
    private Integer infoId;

    /**
     * 行业信息名
     */
    private String infoName;

    private List<JobInfoSelectRowVo> children;

    public List<JobInfoSelectRowVo> getChildren() {
        return children;
    }

    public void setChildren(List<JobInfoSelectRowVo> children) {
        this.children = children;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getInfoName() {
        return infoName == null ? "" : infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    @Override
    public String toString() {
        return "JobInfoSelectRowVo{" +
                "infoId=" + infoId +
                ", infoName='" + infoName + '\'' +
                '}';
    }
}
