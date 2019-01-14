package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 动态点赞推送通知Product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class FriendLikePushProduct {

    /**
     * 动态点赞推送通知
     *
     * @param friendId 被点赞动态
     * @param userId   点赞人
     */
    public static void pushFriendLike(Integer friendId, Integer userId) {
        String mssage = JSON.toJSONString(new PushBeanUtils(friendId, userId));
        ProductMQ.sendMessage(MqToppic.top_friendLikePush, mssage);
    }
}
