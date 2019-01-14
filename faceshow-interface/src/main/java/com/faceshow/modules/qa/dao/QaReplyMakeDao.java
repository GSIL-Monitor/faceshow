package com.faceshow.modules.qa.dao;

import com.faceshow.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 问答回复吐槽操作Dao
 * <p>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/3/19 18:28
 * -------------------------------------------------------------- <br>
 */
@Mapper
public interface QaReplyMakeDao extends BaseDao<Object> {

    /**
     * 根据问答回复id和当前用户id删除吐槽信息
     *
     * @param replyId 问答回复id
     * @param userId  用户id
     * @return
     */
    int deleteByUserIdAndReplyId(@Param("userId") Integer userId, @Param("replyId") Integer replyId);

    /**
     * 判断是否吐槽
     *
     * @param replyId
     * @param userId
     * @return
     */
    Integer findByReplyIdAndUserId(@Param("replyId") Integer replyId, @Param("userId") Integer userId);
}