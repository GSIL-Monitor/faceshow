package com.faceshow.modules.video.service.impl;

import com.faceshow.common.MQ.message.likenum.VideoCommentLikeNumProduct;
import com.faceshow.common.MQ.message.likenum.VideoCommentNumProduct;
import com.faceshow.common.MQ.message.push.VideoCommentProduct;
import com.faceshow.common.utils.PageUtils;
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.friend.dao.FriendRingCommentDao;
import com.faceshow.modules.friend.entity.FriendRingComment;
import com.faceshow.modules.friend.service.FriendRingService;
import com.faceshow.modules.video.dao.VideoCommentCollectionLogDao;
import com.faceshow.modules.video.dao.VideoCommentDao;
import com.faceshow.modules.video.entity.VideoComment;
import com.faceshow.modules.video.service.VideoCommentService;
import com.faceshow.modules.video.vo.VideoCommentSelectRowVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 视频评论操作Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:27
 * -------------------------------------------------------------- <br>
 */
@Service
public class VideoCommentServiceImpl implements VideoCommentService {

    @Autowired
    private VideoCommentDao videoCommentDao;

    @Autowired
    private VideoCommentCollectionLogDao videoCommentCollectionLogDao;

    @Autowired
    private FriendRingCommentDao friendRingCommentDao;

    @Autowired
    private FriendRingService friendRingService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public int deleteByPrimaryKey(Integer commentId) {
        return videoCommentDao.deleteByPrimaryKey(commentId);
    }

    /**
     * 给视频添加评论
     *
     * @param videoComment 评论信息
     * @return
     */
    @Override
    public Object insertSelective(VideoComment videoComment) {

        // 判断是否同步到朋友圈
        Integer friendId = friendRingService.findFriendIdByVideoId(videoComment.getVideoId());
        if (friendId != null) {

            FriendRingComment friendRingComment = new FriendRingComment();
            friendRingComment.setUserId(videoComment.getUserId());
            friendRingComment.setVideoCommentId(videoComment.getCommentId());
            friendRingComment.setCreateTime(new Date());
            friendRingComment.setContent(videoComment.getComment());
            friendRingComment.setFriendId(friendId);
            if (videoComment.getParentCommentId() != null && videoComment.getParentCommentId() != 0) {
                Integer friendCommentParentId = friendRingCommentDao.findFriendCommentIdByVideoCommentId(videoComment.getParentCommentId());
                friendRingComment.setParentCommentId(friendCommentParentId);
            }
            friendRingCommentDao.insertSelective(friendRingComment);
        }
        int i = videoCommentDao.insertSelective(videoComment);
        if (i > 0) {
            // 消息队列改变视频评论数量
            VideoCommentNumProduct.updateVideoCommentNumOne(videoComment.getVideoId(), 1);

            if (videoComment.getParentCommentId() == null || videoComment.getParentCommentId() == 0) {
                // 新添加评论
                VideoCommentProduct.pushVideoComment(videoComment.getCommentId());
            } else {
                // 回复评论
                VideoCommentProduct.pushVideoCommentReply(videoComment.getCommentId());
            }

        }

        // 查询评论总数量
        return R.result(1, "OK", videoCommentDao.findCommentTotalCount(videoComment.getVideoId()));
    }

    @Override
    public int save(VideoComment videoComment) {
        int i = videoCommentDao.insertSelective(videoComment);
        if (i > 0) {
            // 消息队列改变视频评论数量
            VideoCommentNumProduct.updateVideoCommentNumOne(videoComment.getVideoId(), 1);
        }
        return i;
    }

    /**
     * 点击评论图标, 弹出评论页面, 查询所有评论
     *
     * @param videoId  当前视频id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前用户
     * @return
     */
    @Override
    public Object findCommentByVideoId(Integer userId, Integer videoId, Integer currPage, Integer pageSize) {
        Map<String, Object> search = new HashMap<String, Object>(0);
        search.put("videoId", videoId);
        search.put("currPage", (currPage - 1) * pageSize);
        search.put("pageSize", pageSize);
        search.put("userId", userId);

        //  查询当前视频的评论(一级评论)
        List<VideoCommentSelectRowVo> videoCommentList = videoCommentDao.findVideoCommentByVideoId(search);

        if (videoCommentList == null || videoCommentList.size() < 1) {
            return R.ok().put("page", new PageUtils(new ArrayList<>(0), 0, pageSize, currPage));
        }

        // 查询子评论
        List<VideoCommentSelectRowVo> list = new ArrayList<>(0);
        findCommentByParent(videoCommentList, list);

        // 查询评论总数
        Integer commentTotalCount = videoCommentDao.findCommentTotalCount(videoId);

        for (VideoCommentSelectRowVo comment : list) {
            search.put("commentId", comment.getCommentId());

            // 判断此评论当前用户是否已经点赞
            comment.setIsLike(isLike(comment.getCommentId(), userId));
        }
        return R.ok().put("page", new PageUtils(list, commentTotalCount, pageSize, currPage));
    }

    /**
     * 递归查询评论
     *
     * @param commentList
     * @param list
     */
    private void findCommentByParent(List<VideoCommentSelectRowVo> commentList, List<VideoCommentSelectRowVo> list) {
        if (commentList == null || commentList.size() < 1) {
            return;
        }

        for (int i = 0; i < commentList.size(); i++) {
            VideoCommentSelectRowVo videoCommentSelectRowVo = commentList.get(i);

            list.add(videoCommentSelectRowVo);

            // 获取子评论信息
            List<VideoCommentSelectRowVo> childerComment = videoCommentDao.findCommentByParent(videoCommentSelectRowVo.getCommentId());
            findCommentByParent(childerComment, list);

        }

    }

    /**
     * 视频评论,用户点赞, 取消点赞
     *
     * @param commentId 视频评论id
     * @param userId    点赞人id
     * @return
     */
    @Override
    public Object updateCommentLike(Integer commentId, Integer userId) {
        int i = 0;
        int likeNum = 0;

        // 获取朋友圈评论id
        Integer friendCommentId = friendRingCommentDao.findFriendCommentIdByVideoCommentId(commentId);

        Map<String, Object> map = new HashMap<>(0);
        map.put("commentId", commentId);
        map.put("userId", userId);

        // 判断是否已经点赞
        if (isLike(commentId, userId) == 0) {
            // 没有点赞, 进行点赞
            map.put("createTime", new Date());
            i = videoCommentCollectionLogDao.insertSelective(map);
            likeNum = 1;
            // 存入redis
            redisUtils.set(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_" + userId, 1);
            // 同步到朋友圈
            if (friendCommentId != null) {
                friendRingService.addCommentLike(friendCommentId, userId, commentId);
            }
        } else {
            // 取消点赞
            i = videoCommentCollectionLogDao.deleteByUserIdAndCommentId(commentId, userId);
            likeNum = -1;
            redisUtils.delete(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_" + userId);

            // 同步到朋友圈
            if (friendCommentId != null) {
                friendRingService.delCommentLike(friendCommentId, userId);
            }
        }
        // 修改点赞数量
        if (i > 0) {
            VideoCommentLikeNumProduct.updateVideoCommentLikeNum(commentId, likeNum);
        }
        return R.result(1, "OK", "");
    }

    /**
     * 视频评论点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    @Override
    public int addCommentLike(Integer commentId, Integer userId) {
        Map<String, Object> map = new HashMap<>(0);
        map.put("commentId", commentId);
        map.put("userId", userId);
        map.put("createTime", new Date());
        int i = videoCommentCollectionLogDao.insertSelective(map);

        // 存入redis
        redisUtils.set(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_" + userId, 1);

        if (i > 0) {
            // 点赞数量+1
            VideoCommentLikeNumProduct.updateVideoCommentLikeNum(commentId, 1);
        }
        return i;
    }

    /**
     * 视频评论取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    @Override
    public int delCommentLike(Integer commentId, Integer userId) {
        // 删除点赞信息
        int i = videoCommentCollectionLogDao.deleteByUserIdAndCommentId(commentId, userId);
        // 删除redis点赞信息
        redisUtils.delete(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_" + userId);
        if (i > 0) {
            // 修改点赞数量
            VideoCommentLikeNumProduct.updateVideoCommentLikeNum(commentId, -1);
        }
        return i;
    }

    /**
     * 删除短视频评论
     *
     * @param commentId 评论id
     * @return
     */
    @Override
    @Transactional
    public Object deleteCommentById(Integer commentId, Integer videoId) {
        // 删除评论
        deleteCommentByCommentId(commentId);
        // 查询剩余评论数
        int commentNum = videoCommentDao.findCommentTotalCount(videoId);
        // MQ设置短视频数量
        VideoCommentNumProduct.updateVideoCommentNumMany(videoId, commentNum);
        return R.result(1, "删除成功", commentNum);

    }

    /**
     * 根据评论id查询视频id
     *
     * @param videoCommentId
     * @return
     */
    @Override
    public Integer findVideoIdByCommentId(Integer videoCommentId) {
        return videoCommentDao.findVideoIdByCommentId(videoCommentId);
    }

    /**
     * 递归删除评论
     *
     * @param commentId
     */

    private void deleteCommentByCommentId(Integer commentId) {
        // 查询是否拥有子评论
        List<Integer> childComment = videoCommentDao.findcommentIdByParentId(commentId);
        if (childComment != null && childComment.size() > 0) {
            for (Integer id : childComment) {
                deleteCommentByCommentId(id);
            }
        }

        // 删除评论点赞
        videoCommentCollectionLogDao.deleteByCommentId(commentId);
        redisUtils.delete(redisUtils.keys(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_*"));

        // 删除评论
        videoCommentDao.deleteByPrimaryKey(commentId);
    }

    /**
     * 判断是否已经点赞
     *
     * @param commentId
     * @param userId
     * @return
     */
    private Integer isLike(Integer commentId, Integer userId) {
        String isLike = redisUtils.get(RedisKeys.VIDEO_COMMENT_LIKE + commentId + "_" + userId);
        if (StringUtils.isBlank(isLike)) {
            return videoCommentCollectionLogDao.findLikeByUserIdAndCommentId(commentId, userId);
        }
        return 1;
    }
}
