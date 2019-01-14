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
public class QaReplyLikeOrMakeNumProduct {

    /**
     * 问答回复数量+1或者-1操作Product
     *
     * @param qaReplyId 问答回复id
     * @param likeNum   点赞数+1或者-1
     */
    public static void updateReplyLikeNum(Integer qaReplyId, Integer likeNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaReplyId, likeNum));
        ProductMQ.sendMessage(MqToppic.top_qaReplyLikeNum, mssage);
    }

    /**
     * 问答回复数量+1或者-1操作Product
     *
     * @param qaReplyId 问答回复id
     * @param makeNum   点踩数+1或者-1
     */
    public static void updateReplyMakeNum(Integer qaReplyId, Integer makeNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaReplyId, makeNum));
        ProductMQ.sendMessage(MqToppic.top_qaReplyMakeNum, mssage);
    }
}
