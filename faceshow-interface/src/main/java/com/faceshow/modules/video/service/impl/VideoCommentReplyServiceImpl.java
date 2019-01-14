package com.faceshow.modules.video.service.impl;

import com.faceshow.modules.video.dao.VideoCommentReplyDao;
import com.faceshow.modules.video.entity.VideoCommentReply;
import com.faceshow.modules.video.service.VideoCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 视频评论回复Service实现类
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/2/1 17:56
 * -------------------------------------------------------------- <br>
 */
@Service
public class VideoCommentReplyServiceImpl implements VideoCommentReplyService {

    @Autowired
    private VideoCommentReplyDao videoCommentReplyDao;

    @Override
    public int insertSelective(VideoCommentReply record) {
        return videoCommentReplyDao.insertSelective(record);
    }
}
