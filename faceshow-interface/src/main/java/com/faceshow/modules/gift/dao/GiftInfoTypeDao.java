package com.faceshow.modules.gift.dao;


import com.faceshow.modules.gift.entity.GiftInfoType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GiftInfoTypeDao {
    int deleteByPrimaryKey(Integer typeId);

    int insertSelective(GiftInfoType record);

    GiftInfoType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(GiftInfoType record);
}