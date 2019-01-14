package com.faceshow.modules.job.vo;

import java.io.Serializable;

/**
 * 查询用户的职业信息
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/20 16:11
 * -------------------------------------------------------------- <br>
 */
public class JobUserSelectRowVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户职业id
     */
    private Integer jobUserId;
    /**
     * 用户职业名称
     */
    private String career;
    /**
     * 用户所属公司
     */
    private String company;
    /**
     * 用户所属行业
     */
    private String infoName;
    /**
     * 用户所属行业id
     */
    private Integer infoId;

    public JobUserSelectRowVo() {
    }

    public Integer getJobUserId() {
        return jobUserId;
    }

    public void setJobUserId(Integer jobUserId) {
        this.jobUserId = jobUserId;
    }

    public String getCareer() {
        return career == null ? "" : career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCompany() {
        return company == null ? "" : company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInfoName() {
        return infoName == null ? "" : infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    @Override
    public String toString() {
        return "JobUserSelectRowVo{" +
                "jobUserId=" + jobUserId +
                ", career='" + career + '\'' +
                ", company='" + company + '\'' +
                ", infoName='" + infoName + '\'' +
                ", infoId=" + infoId +
                '}';
    }
}
