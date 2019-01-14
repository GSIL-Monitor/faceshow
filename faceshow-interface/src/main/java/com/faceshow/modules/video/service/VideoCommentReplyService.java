package com.faceshow.modules.video.service;

import com.faceshow.modules.video.entity.VideoCommentReply;

/**
 * 视频评论回复Service接口
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:56
 * -------------------------------------------------------------- <br>
 */
public interface VideoCommentReplyService {

    int insertSelective(VideoCommentReply record);
}
