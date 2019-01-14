package com.faceshow.modules.qa.service.impl;

import com.faceshow.common.MQ.message.likenum.QaCommentLikeNumProduct;
import com.faceshow.common.MQ.message.likenum.QaCommentNumProduct;
import com.faceshow.common.utils.*;
import com.faceshow.modules.qa.dao.QaInfoCommentDao;
import com.faceshow.modules.qa.dao.QaInfoCommentLikeDao;
import com.faceshow.modules.qa.dao.QaInfoDao;
import com.faceshow.modules.qa.entity.QaInfoComment;
import com.faceshow.modules.qa.entity.QaInfoCommentLike;
import com.faceshow.modules.qa.service.QaInfoCommentService;
import com.faceshow.modules.qa.vo.QaInfoCommentSelectRowVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 问答评论点赞操作Service接口实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:17
 * -------------------------------------------------------------- <br>
 */
@Service
@Transactional
public class QaInfoCommentServiceImpl implements QaInfoCommentService {

    @Autowired
    private QaInfoCommentDao qaInfoCommentDao;

    @Autowired
    private QaInfoCommentLikeDao qaInfoCommentLikeDao;

    @Autowired
    private QaInfoDao qaInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 根据问答id查询问答评论列表
     *
     * @param map 查询条件
     * @return
     */
    @Override
    public Object findCommentListByInfoId(Map<String, Object> map) {
        Query query = new Query(map);
        // 查询问答一级评论列表
        List<QaInfoCommentSelectRowVo> qaInfoCommentList = qaInfoCommentDao.findCommentListByInfoId(query);
        if (qaInfoCommentList == null || qaInfoCommentList.size() < 1) {
            return R.ok().put("page", new PageUtils(new ArrayList<>(0), 0, query.getLimit(), query.getPage()));
        }
        // 查询子评论
        List<QaInfoCommentSelectRowVo> list = new ArrayList<>(0);
        findCommentByParent(qaInfoCommentList, list);

        // 查询一级评论总数量
        Integer total = qaInfoCommentDao.findCommentTotalByInfoId(Integer.parseInt(query.get("infoId").toString()));

        // 查询评论总数
        Integer commentNum = qaInfoDao.findCommentNumByInfoId(Integer.parseInt(query.get("infoId").toString()));

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
     * @param qaInfoComment 评论详情
     * @return
     */
    @Override
    public Object save(QaInfoComment qaInfoComment) {
        qaInfoComment.setCreateTime(new Date());
        // 保存评论
        int i = qaInfoCommentDao.save(qaInfoComment);
        if (i > 0) {
            // 问答评论数量+1
            QaCommentNumProduct.updateQaCommentNumOne(qaInfoComment.getInfoId(), 1);
        }
        return R.result(1, "OK", qaInfoCommentDao.findCommentNumByInfoId(qaInfoComment.getInfoId()));
    }

    /**
     * 删除问答评论
     *
     * @param commentId 评论id
     * @return
     */
    @Override
    public Object delete(Integer commentId, Integer infoId) {
        // 递归删除评论及其自评论
        deleteCommentByCommentId(commentId);
        // 查询问答剩余评论数
        Integer commentNum = qaInfoCommentDao.findCommentNumByInfoId(infoId);
        // 修改剩余评论数量
        QaCommentNumProduct.updateQaCommentNumMany(infoId, commentNum);

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
            int i = qaInfoCommentLikeDao.deleteByCommentIdAndUserId(commentId, userId);
            // 从redis中删除
            redisUtils.delete(RedisKeys.QA_INFO_COMMENT_LIKE + commentId + "_" + userId);
            if (i > 0) {
                // 点赞数量 -1
                QaCommentLikeNumProduct.updateQaCommentLikeNum(commentId, -1);
            }
            return R.ok("取消点赞成功");
        } else {
            // 添加点赞信息
            QaInfoCommentLike qaInfoCommentLike = new QaInfoCommentLike();
            qaInfoCommentLike.setCommentId(commentId);
            qaInfoCommentLike.setCreateTime(new Date());
            qaInfoCommentLike.setUserId(userId);
            int i = qaInfoCommentLikeDao.save(qaInfoCommentLike);
            if (i > 0) {
                // 点赞数量+1
                QaCommentLikeNumProduct.updateQaCommentLikeNum(commentId, 1);
                // 添加进redis中
                redisUtils.set(RedisKeys.QA_INFO_COMMENT_LIKE + commentId + "_" + userId, 1, RedisUtils.NOT_EXPIRE);
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
            List<QaInfoCommentSelectRowVo> childerComment = qaInfoCommentDao.findCommentByParent(qaInfoCommentSelectRowVo.getCommentId());
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
        String like = redisUtils.get(RedisKeys.QA_INFO_COMMENT_LIKE + commentId + "_" + userId);
        if (StringUtils.isBlank(like)) {
            return qaInfoCommentLikeDao.findByCommentIdAndUserId(commentId, userId);
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
        List<Integer> childComment = qaInfoCommentDao.findCommentIdByParentId(commentId);
        if (childComment != null && childComment.size() > 0) {
            for (Integer id : childComment) {
                deleteCommentByCommentId(id);
            }
        }
        // 删除评论点赞
        qaInfoCommentLikeDao.deleteByCommentId(commentId);
        // 删除redis信息
        redisUtils.delete(redisUtils.keys(RedisKeys.QA_INFO_COMMENT_LIKE + commentId + "_*"));

        // 删除评论
        qaInfoCommentDao.delete(commentId);
    }
}
