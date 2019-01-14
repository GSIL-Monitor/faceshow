package com.faceshow.modules.report.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportInfo implements Serializable {
    /**
     * 举报ID主键
     */
    private Integer reportId;

    /**
     * 举报类型ID主键多个逗号隔开
     */
    private String typeId;

    /**
     * 会员ID主键
     */
    private Integer userId;

    /**
     * 被举报人
     */
    private Integer account;

    /**
     * 举报类型: 1视频, 2直播, 3评论, 4 朋友圈, 5 个人, 6问答, 7 问答回复,8 1V1
     */
    private Byte reportType;

    /**
     * 举报信息的主键ID, 对应report_type的几种ID主键
     */
    private Integer infoId;

    /**
     * 举报图片
     */
    private String img;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 举报时间
     */
    private Date createTime;

    /**
     * 是否处理 0 未处理 1 已处理
     */
    private Byte isProcess;

    /**
     * 处理人
     */
    private String isUser;

    /**
     * 处理时间
     */
    private Date processTime;

    /**
     * 备注
     */
    private String memo;

    private static final long serialVersionUID = 1L;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public Byte getReportType() {
        return reportType;
    }

    public void setReportType(Byte reportType) {
        this.reportType = reportType;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsProcess() {
        return isProcess;
    }

    public void setIsProcess(Byte isProcess) {
        this.isProcess = isProcess;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser == null ? null : isUser.trim();
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reportId=").append(reportId);
        sb.append(", typeId=").append(typeId);
        sb.append(", userId=").append(userId);
        sb.append(", account=").append(account);
        sb.append(", reportType=").append(reportType);
        sb.append(", infoId=").append(infoId);
        sb.append(", img=").append(img);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", isProcess=").append(isProcess);
        sb.append(", isUser=").append(isUser);
        sb.append(", processTime=").append(processTime);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}