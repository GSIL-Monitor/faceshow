package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.JsonUtils;

import java.util.Map;

/**
 * @author: YS
 * @Date:2018/4/27 9:27
 * @param:
 * @explain： 送礼物插入总表
 * @return:
 */
public class SendGiftProduct {
    public static void SendGiftProduct(Map<String,Object> map ) {
        String mssage = JsonUtils.objectToJson(map);
        ProductMQ.sendMessage(MqToppic.senGift, mssage);
    }
}
