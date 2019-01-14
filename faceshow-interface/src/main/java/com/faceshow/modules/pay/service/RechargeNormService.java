package com.faceshow.modules.pay.service;

import com.faceshow.modules.pay.entity.RechargeNorm;

public interface RechargeNormService {

    /**
     * 获取充值规格信息
     * @param rechargeId
     * @return
     */
    public RechargeNorm getRechargeNormById(int rechargeId);
    public RechargeNorm getRechargeNormBySign(String sign);

}
