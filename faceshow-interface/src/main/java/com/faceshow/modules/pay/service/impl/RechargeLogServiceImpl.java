package com.faceshow.modules.pay.service.impl;

import com.faceshow.modules.pay.dao.RechargeLogDao;
import com.faceshow.modules.pay.entity.RechargeLog;
import com.faceshow.modules.pay.service.RechargeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单日志
 */
@Service
public class RechargeLogServiceImpl implements RechargeLogService {
    @Autowired
    private RechargeLogDao rechargeLogDao;

    @Override
    public Object addRechargeLogService(int payTypeId, BigDecimal money, int statues, Integer orderId, String orderNo, String remark) {
        RechargeLog log = new RechargeLog();
        log.setTypeId(payTypeId);
        log.setCreateTime(new Date());
        log.setMoney(money);
        log.setOrderId(orderId);
        log.setOrderNo(orderNo);
        log.setRemark(remark);
        log.setStatues(statues);
        rechargeLogDao.insertSelective(log);
        return null;
    }
}
