package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.MQ.message.likenum.QaReplyCommentLikeNumProduct;
import com.faceshow.common.MQ.message.likenum.QaReplyCommentNumProduct;
import com.faceshow.common.utils.*;
import com.faceshow.modules.qa.dao.QaReplyCommentLikeDao;
import com.faceshow.modules.qa.dao.QaReplyCommentDao;
import com.faceshow.modules.qa.dao.QaReplyDao;
import com.faceshow.modules.qa.entity.QaInfoCommentLike;
import com.faceshow.modules.qa.entity.QaReplyComment;
import com.faceshow.modules.qa.service.QaReplyCommentService;
import com.faceshow.modules.qa.vo.QaInfoCommentSelectRowVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 问答回复评论点赞操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:19
 * -------------------------------------------------------------- <br>
 */
@Service
public class QaReplyCommentServiceImpl implements QaReplyCommentService {

    @Autowired
    private QaReplyDao qaReplyDao;

    @Autowired
    private QaReplyCommentDao qaReplyCommentDao;

    @Autowired
    private QaReplyCommentLikeDao qaReplyCommentLikeDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 根据问答id查询问答评论列表
     *
     * @param map 查询条件
     * @return
     */
    @Override
    public Object findCommentListByReplyId(Map<String, Object> map) {
        Query query = new Query(map);
        // 查询问答一级评论列表
        List<QaInfoCommentSelectRowVo> qaInfoCommentList = qaReplyCommentDao.findCommentListByReplyId(query);
        if (qaInfoCommentList == null || qaInfoCommentList.size() < 1) {
            return R.ok().put("page", new PageUtils(new ArrayList<>(0), 0, query.getLimit(), query.getPage()));
        }
        // 查询子评论
        List<QaInfoCommentSelectRowVo> list = new ArrayList<>(0);
        findCommentByParent(qaInfoCommentList, list);

        // 查询一级评论总数量
        Integer total = qaReplyCommentDao.findCommentTotalByReplyId(Integer.parseInt(query.get("replyId").toString()));

        // 查询评论总数
        Integer commentNum = qaReplyDao.findCommentNumByReplyId(Integer.parseInt(query.get("replyId").toString()));

        Integer userId = Integer.parseInt(query.get("userId").toString());
        for (QaInfoCommentSelectRowVo comment : list) {
            // 判断此评论当前用户是否已经点赞
            comment.setIsLike(isLike(comment.getCommentId(), userId));
        }
        return R.ok().put("page", new PageUtils(list, total, query.getLimit(), query.getPage())).put("commentNum", commentNum);
    }

    /**
     * 添加问答评论
     *
     * @param qaReplyComment 评论详情
     * @return
     */
    @Override
    public Object save(QaReplyComment qaReplyComment) {
        qaReplyComment.setCreateTime(new Date());
        // 保存评论
        int i = qaReplyCommentDao.save(qaReplyComment);
        Integer commentNum = qaReplyDao.findCommentNumByReplyId(qaReplyComment.getReplyId());
        if (i > 0) {
            // 问答评论数量+1
            QaReplyCommentNumProduct.updateqaReplyCommentNumOne(qaReplyComment.getReplyId(), 1);
            return R.result(1, "OK", commentNum + 1);
        }
        return R.result(1, "OK", commentNum);
    }

    /**
     * 删除问答评论
     *
     * @param commentId 评论id
     * @return
     */
    @Override
    public Object delete(Integer commentId, Integer replyId) {
        // 递归删除评论及其自评论
        deleteCommentByCommentId(commentId);
        // 查询问答剩余评论数
        Integer commentNum = qaReplyCommentDao.findCommentNumByReplyId(replyId);
        // 修改剩余评论数量
        QaReplyCommentNumProduct.updateQaReplyCommentNumMany(replyId, commentNum);
        return R.result(1, "OK", commentNum);
    }

    /**
     * 点赞或者取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    @Override
    public Object addOrDelLike(Integer commentId, Integer userId) {
        if (isLike(commentId, userId) > 0) {
            // 已经点赞，取消点赞
            int i = qaReplyCommentLikeDao.deleteByCommentIdAndUserId(commentId, userId);
            if (i > 0) {
                // 从redis中删除
                redisUtils.delete(RedisKeys.QA_REPLY_COMMENT_LIKE + commentId + "_" + userId);
                // 点赞数量 -1
                QaReplyCommentLikeNumProduct.updateQaReplyCommentLikeNum(commentId, -1);
            }
            return R.ok("取消点赞成功");
        } else {
            // 添加点赞信息
            QaInfoCommentLike qaInfoCommentLike = new QaInfoCommentLike();
            qaInfoCommentLike.setCommentId(commentId);
            qaInfoCommentLike.setCreateTime(new Date());
            qaInfoCommentLike.setUserId(userId);
            int i = qaReplyCommentLikeDao.save(qaInfoCommentLike);
            if (i > 0) {
                // 添加进redis中
                redisUtils.set(RedisKeys.QA_REPLY_COMMENT_LIKE + commentId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
                // 点赞数量+1
                QaReplyCommentLikeNumProduct.updateQaReplyCommentLikeNum(commentId, 1);
            }
            return R.ok("点赞成功");
        }
    }

    /**
     * 递归查询评论
     *
     * @param commentList
     * @param list
     */
    private void findCommentByParent(List<QaInfoCommentSelectRowVo> commentList, List<QaInfoCommentSelectRowVo> list) {
        if (commentList == null || commentList.size() < 1) {
            return;
        }

        for (int i = 0; i < commentList.size(); i++) {
            QaInfoCommentSelectRowVo qaInfoCommentSelectRowVo = commentList.get(i);
            list.add(qaInfoCommentSelectRowVo);

            // 获取子评论信息
            List<QaInfoCommentSelectRowVo> childerComment = qaReplyCommentDao.findCommentByParent(qaInfoCommentSelectRowVo.getCommentId());
            findCommentByParent(childerComment, list);
        }
    }

    /**
     * 判断当前次评论当前用户是否点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    private Integer isLike(Integer commentId, Integer userId) {
        // 从redis中获取
        String like = redisUtils.get(RedisKeys.QA_REPLY_COMMENT_LIKE + commentId + "_" + userId);
        if (StringUtils.isBlank(like)) {
            return qaReplyCommentLikeDao.findByCommentIdAndUserId(commentId, userId);
        }
        return 1;
    }

    /**
     * 递归删除评论
     *
     * @param commentId
     */
    private void deleteCommentByCommentId(Integer commentId) {
        // 查询是否拥有子评论
        List<Integer> childComment = qaReplyCommentDao.findCommentIdByParentId(commentId);
        if (childComment != null && childComment.size() > 0) {
            for (Integer id : childComment) {
                deleteCommentByCommentId(id);
            }
        }
        // 删除评论点赞
        qaReplyCommentLikeDao.deleteByCommentId(commentId);
        // 删除redis信息
        redisUtils.delete(redisUtils.keys(RedisKeys.QA_REPLY_COMMENT_LIKE + commentId + "_*"));
        // 删除评论
        qaReplyCommentDao.delete(commentId);
    }
}
