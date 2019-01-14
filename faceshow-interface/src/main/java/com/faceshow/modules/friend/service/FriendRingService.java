package com.faceshow.modules.friend.service;

import com.faceshow.common.utils.RedisKeys;
import com.faceshow.common.utils.RedisUtils;
import com.faceshow.modules.friend.entity.FriendRing;
import com.faceshow.modules.friend.entity.FriendRingComment;
import com.faceshow.modules.friend.entity.FriendRingCommentLike;
import com.faceshow.modules.friend.entity.FriendRingLike;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 朋友圈操作Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
public interface FriendRingService {

    /**
     * 发布朋友圈动态, 添加消息
     *
     * @param friendRing 朋友圈发布内容
     * @param files      所有上传文件
     * @param users      @用户
     * @param videoType  视频所属类型
     * @param musicId    音乐id
     * @param isSource   视频来源
     * @return
     */
    Object insertSelective(FriendRing friendRing, String users, MultipartFile[] files, Integer videoType, Integer musicId, Byte isSource);

    /**
     * 分页查询朋友圈动态
     *
     * @param userId   当前用户id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @return
     */
    Object findFriendRing(Integer userId, Integer currPage, Integer pageSize);

    /**
     * 查看朋友圈内容详情
     *
     * @param friendId 朋友圈id
     * @param currPage 评论当前页
     * @param pageSize 每页显示评论数量
     * @param userId   当前用户id
     * @return
     */
    Object findFriendRingOne(Integer userId, Integer friendId, Integer currPage, Integer pageSize);

    /**
     * 添加朋友圈内容评论
     *
     * @param friendRingComment
     * @return
     */
    Object addFriendComment(FriendRingComment friendRingComment);

    /**
     * 根据评论id删除朋友圈评论内容
     *
     * @param commentId 评论id
     * @return
     */
    Object deleteFriendComment(Integer commentId);

    /**
     * 朋友圈消息,用户点赞, 取消点赞
     *
     * @param friendId 朋友圈内容id
     * @param userId   点赞人id
     * @return
     */
    Object addOrDelFriendLike(Integer friendId, Integer userId);

    /**
     * 根据id删除当前发布的朋友圈消息
     *
     * @param friendId 朋友圈id
     * @return
     */
    Object deleteFriendRingById(Integer friendId);

    /**
     * 朋友圈消息内容评论点赞
     *
     * @param search 点赞信息
     * @return
     */
    Object addOrDelCommentLike(Map<String, Object> search);

    /**
     * 根据视频id查找朋友圈id
     *
     * @param videoId 视频id
     * @return
     */
    Integer findFriendIdByVideoId(Integer videoId);

    /**
     * 查看某个用户的朋友圈动态
     *
     * @param map 查询条件
     *            userId 当前用户
     *            account 被查看用户
     *            limit 每页显示信息数 默认10
     *            page 当前页 默认1
     * @return
     */
    Object findFriendRingByUserId(Map<String, Object> map);

    /**
     * 查看自己发布的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数 默认10
     *            page 当前页 默认1
     * @return
     */
    Object findOneselfFriendRing(Map<String, Object> map);

    /**
     * 查看附近朋友圈动态, 查询三天内附近的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            latitude 维度(当前用户所在位置)
     *            longitude 经度(当前用户所在位置)
     * @return
     */
    Object findNearbyFriendRing(Map<String, Object> map);

    /**
     * 朋友圈消息点赞+1
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    int addFriendLike(Integer friendId, Integer userId, Integer videoId);

    /**
     * 朋友圈消息点赞-1
     *
     * @param friendId 朋友圈id
     * @param userId   用户id
     * @return
     */
    int delFriendLike(Integer friendId, Integer userId);

    /**
     * 朋友圈评论点赞+1
     *
     * @param commentId 朋友圈评论id
     * @param userId    用户id
     * @return
     */
    int addCommentLike(Integer commentId, Integer userId, Integer videoCommentId);

    /**
     * 朋友圈评论点赞-1
     *
     * @param commentId 朋友圈评论id
     * @param userId    用户id
     * @return
     */
    int delCommentLike(Integer commentId, Integer userId);
}
