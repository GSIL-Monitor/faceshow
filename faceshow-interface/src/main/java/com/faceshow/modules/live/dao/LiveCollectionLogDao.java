package com.faceshow.modules.live.dao;

import com.faceshow.modules.live.entity.LiveCollectionLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LiveCollectionLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(LiveCollectionLog record);

    LiveCollectionLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(LiveCollectionLog record);
}