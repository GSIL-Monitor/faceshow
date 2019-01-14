package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * @author: YS
 * @Date:2018/4/26 11:26
 * @param:
 * @explain： 主播开播 给关注的人发送开播信息
 * @return:
 */
public class LivePushProduct {
    public static void livePushProduct(Integer userId) {
        String mssage = JSON.toJSONString(new PushBeanUtils(userId));
        ProductMQ.sendMessage(MqToppic.live_info, mssage);
    }
}
