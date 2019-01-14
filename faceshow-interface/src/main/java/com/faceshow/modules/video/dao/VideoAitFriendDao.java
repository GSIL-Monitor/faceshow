package com.faceshow.modules.video.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import com.faceshow.modules.video.entity.VideoAitFriend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 短视频发布@好友信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/22 10:23
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface VideoAitFriendDao extends BaseDao<VideoAitFriend> {

    /**
     * 根据视频id查询该视频@的好友
     *
     * @param videoId 视频id
     * @return
     */
    List<String> findByVideoId(Integer videoId);
}
