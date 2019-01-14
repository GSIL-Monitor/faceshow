package com.faceshow.modules.pay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RechargeNorm implements Serializable {
    /**
     * 规格ID主键
     */
    private Integer rechargeId;

    /**
     * 价格
     */
    private BigDecimal priceChina;

    private BigDecimal priceAmerica;

    /**
     * 内购标识符
     */
    private String sign;

    /**
     * 钻石数量
     */
    private Integer number;

    /**
     * 添加人
     */
    private Integer creator;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Integer editor;

    /**
     * 修改时间
     */
    private Date editorTime;

    private static final long serialVersionUID = 1L;

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public BigDecimal getPriceChina() {
        return priceChina;
    }

    public void setPriceChina(BigDecimal priceChina) {
        this.priceChina = priceChina;
    }

    public BigDecimal getPriceAmerica() {
        return priceAmerica;
    }

    public void setPriceAmerica(BigDecimal priceAmerica) {
        this.priceAmerica = priceAmerica;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rechargeId=").append(rechargeId);
        sb.append(", priceChina=").append(priceChina);
        sb.append(", priceAmerica=").append(priceAmerica);
        sb.append(", sign=").append(sign);
        sb.append(", number=").append(number);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", editor=").append(editor);
        sb.append(", editorTime=").append(editorTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}