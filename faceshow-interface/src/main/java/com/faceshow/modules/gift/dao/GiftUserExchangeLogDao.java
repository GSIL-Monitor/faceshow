package com.faceshow.modules.gift.dao;

import com.faceshow.modules.gift.entity.GiftUserExchangeLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GiftUserExchangeLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(GiftUserExchangeLog record);

    GiftUserExchangeLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(GiftUserExchangeLog record);
}