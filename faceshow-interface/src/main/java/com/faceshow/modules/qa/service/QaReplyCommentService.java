package com.faceshow.modules.qa.service;

import com.faceshow.modules.qa.entity.QaReplyComment;

import java.util.Map;

/**
 * 问答回复评论操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:19
 * -------------------------------------------------------------- <br>
 */
public interface QaReplyCommentService {

    /**
     * 根据问答id查询问答评论列表
     *
     * @param map 查询条件
     * @return
     */
    Object findCommentListByReplyId(Map<String, Object> map);

    /**
     * 添加问答评论
     *
     * @param qaReplyComment 评论详情
     * @return
     */
    Object save(QaReplyComment qaReplyComment);

    /**
     * 删除问答评论
     *
     * @param commentId 评论id
     * @return
     */
    Object delete(Integer commentId, Integer replyId);

    /**
     * 点赞或者取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    Object addOrDelLike(Integer commentId, Integer userId);
}
