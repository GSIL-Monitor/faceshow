package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;
import com.faceshow.common.utils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 视频@好友推送通知Product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class VideoAtPushProduct {

    /**
     * 视频@好友推送通知
     *
     * @param videoId 视频id
     * @param userId  推送人id
     * @param account 接受者被@人
     */
    public static void pushVideoAt(Integer videoId, Integer userId, Integer account) {
        List<PushBeanUtils> list = new ArrayList<>(0);
        list.add(new PushBeanUtils(videoId, userId, account));
        String mssage = JSON.toJSONString(list);
        ProductMQ.sendMessage(MqToppic.top_videoAtPush, mssage);
    }

    /**
     * 视频@好友推送通知
     *
     * @param videoId 视频id
     * @param userId  推送人id
     * @param userIds 接受者被@人
     */
    public static void pushVideoAtMany(Integer videoId, Integer userId, List<Integer> userIds) {
        if (userIds == null || userIds.size() < 1) {
            return;
        }

        List<PushBeanUtils> list = new ArrayList<>(0);
        for (Integer account : userIds) {
            list.add(new PushBeanUtils(videoId, userId, account));
        }
        String mssage = JSON.toJSONString(list);
        ProductMQ.sendMessage(MqToppic.top_videoAtPush, mssage);
    }
}
