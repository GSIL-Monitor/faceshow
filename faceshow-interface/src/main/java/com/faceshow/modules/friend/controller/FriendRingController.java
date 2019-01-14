package com.faceshow.modules.friend.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.friend.entity.FriendRing;
import com.faceshow.modules.friend.entity.FriendRingComment;
import com.faceshow.modules.friend.service.FriendRingService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 朋友圈操作Controller<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.friend.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/1/26 14:53
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/friend")
public class FriendRingController extends AbstractController {

    @Autowired
    private FriendRingService friendRingService;


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
    @SysLog
    @PostMapping("/addFriendRing")
    public Object addFriendRing(FriendRing friendRing, String users, MultipartFile[] files, Integer videoType, Integer musicId, Byte isSource) {
        // 保存发布消息
        friendRing.setUserId(getUserInfoId());
        return friendRingService.insertSelective(friendRing, users, files, videoType, musicId, isSource);
    }

    /**
     * 分页查看朋友圈动态
     *
     * @param userId 当前用户id
     * @return
     */
    @SysLog
    @PostMapping("/findFriendRingAll")
    public Object findFriendRing(Integer userId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return friendRingService.findFriendRing(getUserInfoId(), currPage, pageSize);
    }

    /**
     * 查看朋友圈内容详情
     *
     * @param friendId 朋友圈id
     * @param currPage 评论当前页
     * @param pageSize 每页显示评论数量
     * @param userId   当前用户id
     * @return
     */
    @SysLog
    @PostMapping("/findFriendRingOne")
    public Object findFriendRingOne(Integer userId, Integer friendId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return friendRingService.findFriendRingOne(getUserInfoId(), friendId, currPage, pageSize);
    }

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
    @SysLog
    @PostMapping("/findFriendRingByUserId")
    public Object findFriendRingByUserId(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return friendRingService.findFriendRingByUserId(map);
    }

    /**
     * 查看自己发布的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数 默认10
     *            page 当前页 默认1
     * @return
     */
    @SysLog
    @PostMapping("/findOneselfFriendRing")
    public Object findOneselfFriendRing(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return friendRingService.findOneselfFriendRing(map);
    }

    /**
     * 查看附近朋友圈动态, 查询三天内附近的动态
     *
     * @param map 查询条件
     *            userId 当前用户id
     *            limit 每页显示信息数
     *            page 当前页
     *            latitude 维度(当前用户所在位置)
     *            longitude 经度(当前用户所在位置)
     *            length 查询距离
     * @return
     */
    @SysLog
    @PostMapping("/findNearbyFriendRing")
    public Object findNearbyFriendRing(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return friendRingService.findNearbyFriendRing(map);
    }

    /**
     * 添加朋友圈内容评论
     *
     * @param friendRingComment
     * @return
     */
    @SysLog
    @PostMapping("/addFriendComment")
    public Object addFriendComment(FriendRingComment friendRingComment) {
        friendRingComment.setUserId(getUserInfoId());
        return friendRingService.addFriendComment(friendRingComment);
    }

    /**
     * 根据评论id删除朋友圈评论内容
     *
     * @param commentId 评论id
     * @return
     */
    @SysLog
    @PostMapping("/deleteFriendComment")
    public Object deleteFriendComment(Integer commentId) {
        return friendRingService.deleteFriendComment(commentId);
    }


    /**
     * 朋友圈消息,用户点赞, 取消点赞
     *
     * @param friendId 朋友圈内容id
     * @param userId   点赞人id
     * @return
     */
    @SysLog
    @PostMapping("/addFriendLike")
    public Object addOrDelFriendLike(Integer friendId, Integer userId) {
        return friendRingService.addOrDelFriendLike(friendId, getUserInfoId());
    }

    /**
     * 朋友圈消息内容评论点赞
     *
     * @param search 点赞信息
     *               # commentId 朋友圈内容评论id
     *               # userId 点赞人id
     * @return
     */
    @SysLog
    @PostMapping("/addCommentLike")
    public Object addOrDelCommentLike(@RequestParam Map<String, Object> search) {
        search.put("userId", getUserInfoId());
        return friendRingService.addOrDelCommentLike(search);
    }

    /**
     * 根据id删除当前发布的朋友圈消息
     *
     * @param friendId 朋友圈id
     * @return
     */
    @SysLog
    @PostMapping("/deleteFriendRing")
    public Object deleteFriendRing(Integer friendId) {
        return friendRingService.deleteFriendRingById(friendId);
    }
}
