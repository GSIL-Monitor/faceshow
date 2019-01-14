package com.faceshow.modules.pay.dao;

import com.faceshow.modules.pay.entity.RechargeLog;
import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RechargeLogDao  extends BaseDao<Object> {
    int deleteByPrimaryKey(Integer logId);

    int insert(RechargeLog record);

    int insertSelective(RechargeLog record);

    RechargeLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(RechargeLog record);

    int updateByPrimaryKey(RechargeLog record);
}