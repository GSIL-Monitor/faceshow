package com.faceshow.modules.qa.controller;

import com.faceshow.common.annotation.SysLog;
import com.faceshow.modules.qa.entity.QaInfoComment;
import com.faceshow.modules.qa.entity.QaReplyComment;
import com.faceshow.modules.qa.service.QaInfoCommentService;
import com.faceshow.modules.qa.service.QaReplyCommentService;
import com.faceshow.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 问答评论操作controller作用<br>
 * Project: faceshow<br>
 * Class Type: Controller<br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.qa.controller<br>
 * extends：AbstractController<br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/4 11:26
 * -------------------------------------------------------------- <br>
 */
@RestController
@RequestMapping("/tokens/qareply/comment")
public class QaReplyCommentController extends AbstractController {

    @Autowired
    private QaReplyCommentService qaReplyCommentService;

    /**
     * 点击评论按钮, 弹出评论列表
     *
     * @param map 查询条件
     *            limit 每页显示信息数
     *            page 当前页
     *            userId 当前用户id
     *            replyId 问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/findComment")
    public Object findComment(@RequestParam Map<String, Object> map) {
        map.put("userId", getUserInfoId());
        return qaReplyCommentService.findCommentListByReplyId(map);
    }

    /**
     * 添加问答回复评论
     *
     * @param qaReplyComment 评论详情
     * @return
     */
    @SysLog
    @PostMapping("/save")
    public Object save(QaReplyComment qaReplyComment) {
        qaReplyComment.setUserId(getUserInfoId());
        return qaReplyCommentService.save(qaReplyComment);
    }

    /**
     * 删除问答回复评论
     *
     * @param commentId 评论id
     * @param replyId    问答回复id
     * @return
     */
    @SysLog
    @PostMapping("/delete")
    public Object delete(Integer commentId, Integer replyId) {
        return qaReplyCommentService.delete(commentId, replyId);
    }

    /**
     * 点赞或者取消点赞
     *
     * @param commentId 评论id
     * @param userId    用户id
     * @return
     */
    @SysLog
    @PostMapping("/addLike")
    public Object addLike(Integer commentId, Integer userId) {
        return qaReplyCommentService.addOrDelLike(commentId, getUserInfoId());
    }


}
