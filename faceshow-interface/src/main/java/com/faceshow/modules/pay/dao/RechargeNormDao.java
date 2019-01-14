package com.faceshow.modules.pay.dao;

import com.faceshow.modules.pay.entity.RechargeNorm;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RechargeNormDao extends BaseDao<Object> {
    int deleteByPrimaryKey(Integer rechargeId);

    int insert(RechargeNorm record);

    int insertSelective(RechargeNorm record);

    RechargeNorm selectByPrimaryKey(Integer rechargeId);
    RechargeNorm selectByPrimarySign(String sign);

    int updateByPrimaryKeySelective(RechargeNorm record);

    int updateByPrimaryKey(RechargeNorm record);
}