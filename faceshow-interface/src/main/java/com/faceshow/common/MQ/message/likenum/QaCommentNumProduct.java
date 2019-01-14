package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 问答评论数量操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 20:14
 * -------------------------------------------------------------- <br>
 */
public class QaCommentNumProduct {

    /**
     * 问答评论数量+1或者-1操作Product
     *
     * @param qaInfoId   问答id
     * @param commentNum 评论数数+1或者-1
     */
    public static void updateQaCommentNumOne(Integer qaInfoId, Integer commentNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaInfoId, commentNum));
        ProductMQ.sendMessage(MqToppic.top_qaCommentNumOne, mssage);
    }

    /**
     * 问答评论数量修改为固定值
     *
     * @param qaInfoId   问答id
     * @param commentNum 评论数数+1或者-1
     */
    public static void updateQaCommentNumMany(Integer qaInfoId, Integer commentNum) {
        String mssage = JSON.toJSONString(new NumUtils(qaInfoId, commentNum));
        ProductMQ.sendMessage(MqToppic.top_qaCommentNumMany, mssage);
    }
}
