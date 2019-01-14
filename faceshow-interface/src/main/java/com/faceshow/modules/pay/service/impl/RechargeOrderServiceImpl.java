package com.faceshow.modules.pay.service.impl;

import com.faceshow.common.utils.R;
import com.faceshow.modules.pay.dao.RechargeOrderDao;
import com.faceshow.modules.pay.entity.RechargeOrder;
import com.faceshow.modules.pay.service.RechargeLogService;
import com.faceshow.modules.pay.service.RechargeOrderService;
import com.faceshow.modules.user.dao.UserGiveAwayLogDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付订单信息
 */
@Service
public class RechargeOrderServiceImpl implements RechargeOrderService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RechargeOrderDao rechargeOrderDao;
    @Autowired
    private RechargeLogService rechargeLogService;
    @Autowired
    private UserGiveAwayLogDao userGiveAwayLogDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Override
    public Object saveRechargeOrder(int typeId, int rechargeId, int userId, BigDecimal price, String orderNo) {

        RechargeOrder rechargeOrder = rechargeOrderDao.selectByOrderNo(orderNo);
        if (rechargeOrder != null){
            logger.warn("orderNo 存在：{}", orderNo);
            return R.ok("");
        }
        RechargeOrder order = new RechargeOrder();
        order.setTypeId(typeId);
        order.setRechargeId(rechargeId);
        order.setUserId(userId);
        order.setOrderNo(orderNo);
        order.setStatues(1);
        order.setPrice(price);
        order.setPayType(0);
        order.setCreateTime(new Date());
        rechargeOrderDao.insertSelective(order);

        //添加日志信息
        rechargeLogService.addRechargeLogService(typeId, price, 0, order.getOrderId(), orderNo, null);

        UserInfo userInfo = userInfoDao.selectByPrimaryKey(userId);
        //用户赠送礼品表日志
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("userId", userId);
        parMap.put("userName", userInfo.getUserName());
        parMap.put("createTime", new Date());
        parMap.put("isType", 8);
        parMap.put("price", price);
        parMap.put("itemId", order.getOrderId());
        parMap.put("types", typeId);
        userGiveAwayLogDao.insertPaySelective(parMap);

        return R.ok();
    }
}
