package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 视频评论推送通知Product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class VideoCommentProduct {

    /**
     * 评论推送通知
     *
     * @param comment 评论id
     */
    public static void pushVideoComment(Integer comment) {
        String mssage = JSON.toJSONString(new PushBeanUtils(comment, null));
        ProductMQ.sendMessage(MqToppic.top_videoCommentPush, mssage);
    }

    /**
     * 评论回复推送
     *
     * @param comment 评论id
     */
    public static void pushVideoCommentReply(Integer comment) {
        String mssage = JSON.toJSONString(new PushBeanUtils(comment, null));
        ProductMQ.sendMessage(MqToppic.top_videoCommentReplyPush, mssage);
    }
}
