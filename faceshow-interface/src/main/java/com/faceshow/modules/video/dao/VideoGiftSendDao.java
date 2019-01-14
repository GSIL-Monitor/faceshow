package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoGiftSend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoGiftSendDao {
    int deleteByPrimaryKey(Integer sendId);

    int insertSelective(Map<String, Object> map);

    VideoGiftSend selectByPrimaryKey(Integer sendId);

    int updateByPrimaryKeySelective(VideoGiftSend record);

    /**
     * 查询收到的礼物排行列表
     *
     * @param map 查询条件
     * @return
     */
    List<Object> haveGiftRankList(Map<String, Object> map);

    /**
     * 查询收到的礼物排行数量
     *
     * @param map 查询条件
     * @return
     */
    Integer haveGiftRankTotal(Map<String, Object> map);
}