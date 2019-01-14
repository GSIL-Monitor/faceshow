package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 发布视频推送至粉丝Product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class VideoPushManyProduct {

    /**
     * 发布视频推送至粉丝
     *
     * @param userId  视频发布人
     * @param videoId 视频id
     */
    public static void pushVideoLike(Integer videoId, Integer userId) {
        String mssage = JSON.toJSONString(new PushBeanUtils(videoId, userId));
        ProductMQ.sendMessage(MqToppic.top_videoPushMany, mssage);
    }
}
