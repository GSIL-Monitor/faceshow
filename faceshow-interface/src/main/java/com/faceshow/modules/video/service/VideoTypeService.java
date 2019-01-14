package com.faceshow.modules.video.service;


import com.faceshow.modules.video.entity.VideoType;

import java.util.Map;

public interface VideoTypeService {

    int deleteByPrimaryKey(Integer typeId);

    int insertSelective(VideoType record);

    VideoType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(VideoType record);

    /**
     * 查询视频分类20个
     *
     * @return
     */
    Object list(Map<String,Object>map);

    /**
     * 进入分类详情页
     *
     * @param typeId 分类id
     * @return
     */
    Object intoVideoType(Integer typeId);
}
