package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 问答回复推送通知product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/26 13:49
 * -------------------------------------------------------------- <br>
 */
public class QaReplyPushProduct {

    /**
     * 问答回复推送通知
     *
     * @param qaId   问答id
     * @param userId 回复人id
     */
    public static void pushQaReply(Integer qaId, Integer userId) {
        String mssage = JSON.toJSONString(new PushBeanUtils(qaId, userId));
        ProductMQ.sendMessage(MqToppic.top_qaReplyPush, mssage);
    }
}
