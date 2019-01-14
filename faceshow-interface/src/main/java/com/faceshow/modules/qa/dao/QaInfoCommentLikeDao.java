package com.faceshow.modules.qa.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答评论点赞信息操作Dao
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:08
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaInfoCommentLikeDao extends BaseDao<Object> {
    /**
     * 根据评论id和用户id判断是否点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    Integer findByCommentIdAndUserId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    /**
     * 根据评论id删除评论点赞
     *
     * @param commentId 评论id
     * @return
     */
    int deleteByCommentId(Integer commentId);

    /**
     * 根据评论id和用户id删除点赞信息
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    int deleteByCommentIdAndUserId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);
}
