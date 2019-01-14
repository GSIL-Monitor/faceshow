package com.faceshow.modules.video.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.common.utils.R;
import com.faceshow.modules.sys.controller.AbstractController;
import com.faceshow.modules.video.entity.VideoComment;
import com.faceshow.modules.video.entity.VideoCommentReply;
import com.faceshow.modules.video.service.VideoCommentReplyService;
import com.faceshow.modules.video.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 短视频评论操作Controller<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.video.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:09
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/videoComment")
public class VideoCommentController extends AbstractController {

    @Autowired
    private VideoCommentService videoCommentService;

    @Autowired
    private VideoCommentReplyService videoCommentReplyService;

    /**
     * 点击评论图标, 弹出评论页面, 查询所有评论
     *
     * @param videoId  当前视频id
     * @param currPage 当前页
     * @param pageSize 每页显示信息数
     * @param userId   当前用户
     * @return
     */
    @SysLog
    @PostMapping("/findCommentByVideoId")
    public Object findCommentByVideoId(Integer userId, Integer videoId, @RequestParam(defaultValue = "1") Integer currPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return videoCommentService.findCommentByVideoId(getUserInfoId(), videoId, currPage, pageSize);
    }

    /**
     * 给视频添加评论
     *
     * @param videoComment 评论信息
     * @return
     */
    @SysLog
    @PostMapping("/addVideoComment")
    public Object addVideoComment(VideoComment videoComment) {
        videoComment.setUserId(getUserInfoId());
        videoComment.setCreateTime(new Date());
        return videoCommentService.insertSelective(videoComment);
    }

    /**
     * 回复视频评论表 -- 暂时不用
     *
     * @param videoCommentReply
     * @return
     */
    @SysLog
    @PostMapping("/replyComment")
    public Object replyComment(VideoCommentReply videoCommentReply) {
        videoCommentReply.setCreateTime(new Date());
        videoCommentReply.setUserId(getUserInfoId());
        int i = videoCommentReplyService.insertSelective(videoCommentReply);
        if (i > 0) {
            // 评论成功
            return R.result(1, "回复评论成功", "");
        }
        return R.result(0, "回复评论失败", "");
    }

    /**
     * 视频评论,用户点赞, 取消点赞
     *
     * @param commentId 视频评论id
     * @param userId    点赞人id
     * @return
     */
    @SysLog
    @PostMapping("/updateCommentLike")
    public Object updateCommentLike(Integer commentId, Integer userId) {
        return videoCommentService.updateCommentLike(commentId, getUserInfoId());
    }

    /**
     * 删除短视频评论
     *
     * @param commentId 删除评论
     * @return
     */
    @SysLog
    @PostMapping("/deleteCommentById")
    public Object deleteCommentById(Integer commentId, Integer videoId) {
        return videoCommentService.deleteCommentById(commentId, videoId);
    }
}
