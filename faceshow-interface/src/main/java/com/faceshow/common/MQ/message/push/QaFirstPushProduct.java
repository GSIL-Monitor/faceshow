package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 问答评为第一名推送通知product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/26 13:49
 * -------------------------------------------------------------- <br>
 */
public class QaFirstPushProduct {

    /**
     * 问答评为第一名
     *
     * @param replyId 问答回复id
     * @param account 问答回复人id
     */
    public static void pushQaBest(Integer replyId, Integer account) {
        String mssage = JSON.toJSONString(new PushBeanUtils(replyId, null, account));
        ProductMQ.sendMessage(MqToppic.top_qaFirstPush, mssage);
    }
}
