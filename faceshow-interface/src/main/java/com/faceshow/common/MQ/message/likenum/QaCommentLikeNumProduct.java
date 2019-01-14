package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 问答评论点赞数量修改+1或者-1
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 10:14
 * -------------------------------------------------------------- <br>
 */
public class QaCommentLikeNumProduct {

    /**
     * 问答评论点赞数量+1或者-1操作Product
     *
     * @param commentId 问答评论id
     * @param likeNum   点赞数+1或者-1
     */
    public static void updateQaCommentLikeNum(Integer commentId, Integer likeNum) {
        String mssage = JSON.toJSONString(new NumUtils(commentId, likeNum));
        ProductMQ.sendMessage(MqToppic.top_qaCommentLikeNum, mssage);
    }
}
