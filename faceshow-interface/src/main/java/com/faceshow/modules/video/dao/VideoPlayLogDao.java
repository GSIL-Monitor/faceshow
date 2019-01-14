package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoPlayLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoPlayLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(VideoPlayLog record);

    VideoPlayLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(VideoPlayLog record);

    /**
     * 批量保存数据
     *
     * @param playList
     * @return
     */
    int saveBatch(List<VideoPlayLog> playList);
}