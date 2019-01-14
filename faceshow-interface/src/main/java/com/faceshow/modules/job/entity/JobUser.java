package com.faceshow.modules.job.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class JobUser implements Serializable {
    /**
     * 用户职业ID主键
     */
    private Integer jobUserId;

    /**
     * 职业信息ID主键
     */
    private Integer infoId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户ID主键
     */
    private Integer userId;

    /**
     * 所属公司
     */
    private String company;

    /**
     * 所属职业
     */
    private String career;

    /**
     * 开始时间 所属开始工作时间(在这家公司的工作时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间 所属结束工作时间(在这家公司的工作时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private static final long serialVersionUID = 1L;

    public Integer getJobUserId() {
        return jobUserId;
    }

    public void setJobUserId(Integer jobUserId) {
        this.jobUserId = jobUserId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career == null ? null : career.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", jobUserId=").append(jobUserId);
        sb.append(", infoId=").append(infoId);
        sb.append(", createTime=").append(createTime);
        sb.append(", userId=").append(userId);
        sb.append(", company=").append(company);
        sb.append(", career=").append(career);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}