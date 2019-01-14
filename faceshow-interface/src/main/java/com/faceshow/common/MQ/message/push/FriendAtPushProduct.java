package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈@好友推送通知Product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class FriendAtPushProduct {

    /**
     * 朋友圈@好友推送通知
     *
     * @param friendId 动态id
     * @param userId   推送人id
     * @param account  接受者被@人
     */
    public static void pushFriendAt(Integer friendId, Integer userId, Integer account) {
        List<PushBeanUtils> list = new ArrayList<>(0);
        list.add(new PushBeanUtils(friendId, userId, account));
        String mssage = JSON.toJSONString(list);
        ProductMQ.sendMessage(MqToppic.top_friendAtPush, mssage);
    }

    /**
     * 朋友圈@好友推送通知
     *
     * @param friendId 动态id
     * @param userId   推送人id
     * @param userIds  接受者被@人
     */
    public static void pushFriendAtMany(Integer friendId, Integer userId, List<Integer> userIds) {
        if (userIds == null || userIds.size() < 1) {
            return;
        }
        List<PushBeanUtils> list = new ArrayList<>(0);
        for (Integer account : userIds) {
            list.add(new PushBeanUtils(friendId, userId, account));
        }
        String mssage = JSON.toJSONString(list);
        ProductMQ.sendMessage(MqToppic.top_friendAtPush, mssage);
    }
}
