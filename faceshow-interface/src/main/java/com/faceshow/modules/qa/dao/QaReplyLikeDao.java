package com.faceshow.modules.qa.dao;


import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答回复点赞操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaReplyLikeDao extends BaseDao<Object> {

    /**
     * 根据用户id和问答回复id删除点赞信息
     *
     * @param userId  当前用户id
     * @param replyId 回复id
     * @return
     */
    int deleteByUserIdAndReplyId(@Param("userId") Integer userId, @Param("replyId") Integer replyId);

    /**
     * 判断当前用户是否点赞
     *
     * @param replyId 回复id
     * @param userId  用户id
     * @return
     */
    Integer findByReplyIdAndUserId(@Param("replyId") Integer replyId, @Param("userId") Integer userId);
}