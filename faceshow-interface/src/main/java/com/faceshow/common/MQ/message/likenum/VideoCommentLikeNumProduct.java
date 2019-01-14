package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 视频评论点赞数量修改操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 15:09
 * -------------------------------------------------------------- <br>
 */
public class VideoCommentLikeNumProduct {

    /**
     * 视频评论点赞数量加一或者减一操作生产者
     *
     * @param commentId 视频评论id
     * @param likeNum   +1 或者-1
     */
    public static void updateVideoCommentLikeNum(Integer commentId, Integer likeNum) {
        String mssage = JSON.toJSONString(new NumUtils(commentId, likeNum));
        ProductMQ.sendMessage(MqToppic.top_videoCommentLikeNum, mssage);
    }

}
