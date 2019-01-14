package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoCollectionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface VideoCollectionLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(VideoCollectionLog record);

    VideoCollectionLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(VideoCollectionLog record);

    Integer findLogIdByVideoIdAndUserId(@Param("video_id") Integer video_id, @Param("userId") Integer userId);

    /**
     * 根据视频id和用户id删除视频点赞信息
     *
     * @param video_id 视频id
     * @param userId   用户id
     * @return
     */
    int delByVideoIdAndUserId(@Param("video_id") Integer video_id, @Param("userId") Integer userId);
}