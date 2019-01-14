package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.NumUtils;

/**
 * 朋友圈点赞数量修改操作生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 16:11
 * -------------------------------------------------------------- <br>
 */
public class FriendLikeNumProduct {

    /**
     * 视朋友圈点赞数量加一或者减一操作生产者
     *
     * @param friendId 朋友圈id
     * @param likeNum  +1 或者-1
     */
    public static void updateFriendLikeNum(Integer friendId, Integer likeNum) {
        String mssage = JSON.toJSONString(new NumUtils(friendId, likeNum));
        ProductMQ.sendMessage(MqToppic.top_friendLikeNum, mssage);
    }
}
