package com.faceshow.modules.qa.service;

import com.faceshow.common.utils.Query;
import com.faceshow.modules.qa.entity.QaInfoComment;

import java.util.List;
import java.util.Map;

/**
 * 问答评论操作Serveice接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:17
 * -------------------------------------------------------------- <br>
 */
public interface QaInfoCommentService {
    /**
     * 根据问答id查询问答评论列表
     *
     * @param map 查询条件
     * @return
     */
    Object findCommentListByInfoId(Map<String, Object> map);

    /**
     * 添加问答评论
     *
     * @param qaInfoComment 评论详情
     * @return
     */
    Object save(QaInfoComment qaInfoComment);

    /**
     * 删除问答评论
     *
     * @param commentId 评论id
     * @return
     */
    Object delete(Integer commentId, Integer infoId);

    /**
     * 点赞或者取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    Object addOrDelLike(Integer commentId, Integer userId);
}
