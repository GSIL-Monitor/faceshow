package com.faceshow.modules.video.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 视频简介操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/27 8:49
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface VideoInfoIntroductionDao extends BaseDao<Object> {

    /**
     * 保存视频简介信息
     *
     * @param videoId      视频id
     * @param introduction 视频简介信息
     * @return
     */
    int save(@Param("videoId") Integer videoId, @Param("introduction") String introduction);

    /**
     * 根据视频id查询视频简介
     *
     * @param videoId 视频id
     * @return
     */
    String findIntroductionByVideoId(Integer videoId);

    /**
     * 根据视频id删除视频简介
     *
     * @param videoId 视频id
     * @return
     */
    int deleteByVideoId(Integer videoId);
}
