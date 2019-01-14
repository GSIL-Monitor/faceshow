package com.faceshow.modules.pay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RechargeLog implements Serializable {
    /**
     * ID主键
     */
    private Integer logId;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 充值金额
     */
    private BigDecimal money;

    /**
     * 充值状态 0 支付成功 1 失败
     */
    private Integer statues;

    /**
     * 充值订单编号
     */
    private Integer orderId;

    /**
     * 充值订单编号
     */
    private String orderNo;

    /**
     * 修改人ID
     */
    private Integer editor;

    /**
     * 修改时间
     */
    private Date editorTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联支付类型表(pay_type)主键ID
     */
    private Integer typeId;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatues() {
        return statues;
    }

    public void setStatues(Integer statues) {
        this.statues = statues;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getEditor() {
        return editor;
    }

    public void setEditor(Integer editor) {
        this.editor = editor;
    }

    public Date getEditorTime() {
        return editorTime;
    }

    public void setEditorTime(Date editorTime) {
        this.editorTime = editorTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", createTime=").append(createTime);
        sb.append(", money=").append(money);
        sb.append(", statues=").append(statues);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", remark=").append(remark);
        sb.append(", typeId=").append(typeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}