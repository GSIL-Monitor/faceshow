package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.PushBeanUtils;
import com.faceshow.common.utils.push.Jpush.JpushTemplet;

/**
 * @author: YS
 * @Date:2018/4/26 9:19
 * @param:
 * @explain：添加好友的推送
 * @return:
 */


//  新粉丝关注   public static final Integer ATTENTION_TYPE = 14;

public class FriendAddPushProduct {
    public static void pushFriendAdd(Integer userId, Integer account) {
        String mssage = JsonUtils.objectToJson(new PushBeanUtils(JpushTemplet.ATTENTION_TYPE,userId, account));
        ProductMQ.sendMessage(MqToppic.user_attention, mssage);
    }
}
