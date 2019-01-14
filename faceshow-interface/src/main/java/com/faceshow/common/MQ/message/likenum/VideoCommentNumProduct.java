package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 视频评论数量修改操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 15:09
 * -------------------------------------------------------------- <br>
 */
public class VideoCommentNumProduct {

    /**
     * 视频评论数量加一或者减一操作生产者
     *
     * @param videoId    视频id
     * @param commentNum +1 或者-1
     */
    public static void updateVideoCommentNumOne(Integer videoId, Integer commentNum) {
        String mssage = JSON.toJSONString(new NumUtils(videoId, commentNum));
        ProductMQ.sendMessage(MqToppic.top_videoCommentNumOne, mssage);
    }

    /**
     * 修改评论数量为固定值
     *
     * @param videoId    视频id
     * @param commentNum 数量
     */
    public static void updateVideoCommentNumMany(Integer videoId, Integer commentNum) {
        String mssage = JSON.toJSONString(new NumUtils(videoId, commentNum));
        ProductMQ.sendMessage(MqToppic.top_videoCommentNumMany, mssage);
    }
}
