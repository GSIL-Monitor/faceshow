package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 问答回复数量修改+1或者-1
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 10:14
 * -------------------------------------------------------------- <br>
 */
public class QaReplyNumProduct {

    /**
     * 问答回复数量+1或者-1操作Product
     *
     * @param qaInfoId 问答id
     * @param replyNum 回复数+1或者-1
     */
    public static void updateReplyNum(Integer qaInfoId, Integer replyNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaInfoId, replyNum));
        ProductMQ.sendMessage(MqToppic.top_qaReplyNum, mssage);
    }
}
