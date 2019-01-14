package com.faceshow.modules.pay.dao;

import com.faceshow.modules.pay.entity.RechargeOrder;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RechargeOrderDao  extends BaseDao<Object> {
    int deleteByPrimaryKey(Integer orderId);

    int insert(RechargeOrder record);

    int insertSelective(RechargeOrder record);

    RechargeOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(RechargeOrder record);

    int updateByPrimaryKey(RechargeOrder record);

    /**
     * 根据订单号获取订单信息
     * @param orderNo
     * @return
     */
    RechargeOrder selectByOrderNo(String orderNo);
}