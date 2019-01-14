package com.faceshow.modules.video.dao;

import com.faceshow.modules.video.entity.VideoCommentCollectionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoCommentCollectionLogDao {
    int deleteByPrimaryKey(Integer logId);

    int insertSelective(Map<String, Object> search);

    VideoCommentCollectionLog selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(VideoCommentCollectionLog record);

    /**
     * 判断当前用户是否已经点赞
     *
     * @param userId
     * @param commentId
     * @return
     */
    Integer findLikeByUserIdAndCommentId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    /**
     * 根据用户id和评论id删除点赞信息
     *
     * @param userId
     * @param commentId
     * @return
     */
    int deleteByUserIdAndCommentId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    /**
     * 根据评论id删除评论点赞
     *
     * @param commentId 评论id
     * @return
     */
    int deleteByCommentId(Integer commentId);
}