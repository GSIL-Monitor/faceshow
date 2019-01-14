package com.faceshow.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

public class SysFastdfs implements Serializable {

    /**
     * 上传文件的ID主键
     */
    private Integer fastId;

    /**
     * 名称路径
     */
    private String url;

    /**
     * 上传时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public SysFastdfs() {
    }

    public SysFastdfs(String url, Date createTime) {
        this.url = url;
        this.createTime = createTime;
    }

    public Integer getFastId() {
        return fastId;
    }

    public void setFastId(Integer fastId) {
        this.fastId = fastId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fastId=").append(fastId);
        sb.append(", url=").append(url);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}