package com.faceshow.modules.pay.service;

import java.math.BigDecimal;

public interface RechargeOrderService {

    /**
     * 保存支付记录
     * @param typeId
     * @param rechargeId
     * @param userId
     * @param price
     * @param orderNo
     * @return
     */
    public Object saveRechargeOrder(int typeId, int rechargeId, int userId, BigDecimal price, String orderNo);

}
