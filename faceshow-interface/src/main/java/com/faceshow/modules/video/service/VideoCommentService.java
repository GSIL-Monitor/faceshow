package com.faceshow.modules.video.service;

import com.faceshow.modules.video.entity.VideoComment;

import java.util.Map;

/**
 * 视频评论操作Service
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:26
 * -------------------------------------------------------------- <br>
 */
public interface VideoCommentService {

    int deleteByPrimaryKey(Integer commentId);

    /**
     * 给视频添加评论
     *
     * @param videoComment 评论信息
     * @return
     */
    Object insertSelective(VideoComment videoComment);

    int save(VideoComment record);


    /**
     * 点击评论图标, 弹出评论页面, 查询所有评论
     *
     * @param videoId  当前视频id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前用户
     * @return
     */
    Object findCommentByVideoId(Integer userId, Integer videoId, Integer currPage, Integer pageSize);

    /**
     * 视频评论,用户点赞, 取消点赞
     *
     * @param commentId 视频评论id
     * @param userId    点赞人id
     * @return
     */
    Object updateCommentLike(Integer commentId, Integer userId);

    /**
     * 视频评论点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    int addCommentLike(Integer commentId, Integer userId);

    /**
     * 视频评论取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    int delCommentLike(Integer commentId, Integer userId);

    /**
     * 删除短视频评论
     *
     * @param commentId 评论id
     * @return
     */
    Object deleteCommentById(Integer commentId, Integer videoId);

    /**
     * 根据评论id查询视频id
     * @param videoCommentId
     * @return
     */
    Integer findVideoIdByCommentId(Integer videoCommentId);
}
