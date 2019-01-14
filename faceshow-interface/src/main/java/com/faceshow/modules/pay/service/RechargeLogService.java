package com.faceshow.modules.pay.service;

import java.math.BigDecimal;
import java.util.Date;

public interface RechargeLogService {

    /**
     * 保存支付日志
     * @param money
     * @param statues
     * @param orderId
     * @param orderNo
     * @param remark
     * @return
     */
    public Object addRechargeLogService(int payTypeId, BigDecimal money, int statues,
                                        Integer orderId,
                                        String orderNo, String remark);

}
