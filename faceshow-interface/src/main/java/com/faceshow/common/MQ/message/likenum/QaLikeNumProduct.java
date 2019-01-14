package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 问答点赞数量操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 20:14
 * -------------------------------------------------------------- <br>
 */
public class QaLikeNumProduct {

    /**
     * 问答点赞数量+1或者-1操作Product
     *
     * @param qaInfoId 问答id
     * @param likeNum  点赞数+1或者-1
     */
    public static void updateQaLikeNum(Integer qaInfoId, Integer likeNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaInfoId, likeNum));
        ProductMQ.sendMessage(MqToppic.top_qaLikeNum, mssage);
    }
}
