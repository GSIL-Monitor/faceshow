package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;

/**
 * 问答指定最佳答案推送通知product
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/26 13:49
 * -------------------------------------------------------------- <br>
 */
public class QaBestPushProduct {

    /**
     * 问答指定最佳答案
     *
     * @param replyId 问答回复id
     * @param userId  问答发布人id
     * @param account 问答回复人id
     */
    public static void pushQaBest(Integer replyId, Integer userId, Integer account) {
        String mssage = JSON.toJSONString(new PushBeanUtils(replyId, userId, account));
        ProductMQ.sendMessage(MqToppic.top_qaBestPush, mssage);
    }
}
