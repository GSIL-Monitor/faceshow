package com.faceshow.modules.pay.service.impl;

import com.faceshow.modules.pay.dao.RechargeNormDao;
import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.pay.service.RechargeNormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RechargeNormServiceImpl implements RechargeNormService{

    @Autowired
    RechargeNormDao rechargeNormDao;

    @Override
    public RechargeNorm getRechargeNormById(int rechargeId) {
        return rechargeNormDao.selectByPrimaryKey(rechargeId);
    }
    @Override
    public RechargeNorm getRechargeNormBySign(String sign) {
        return rechargeNormDao.selectByPrimarySign(sign);
    }
}
