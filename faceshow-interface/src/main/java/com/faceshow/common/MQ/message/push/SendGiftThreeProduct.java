package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;

import java.util.Map;

/**
 * @author: YS
 * @Date:2018/4/26 20:17
 * @param:
 * @explain：赠送礼物
 * @return:
 */
public class SendGiftThreeProduct {
    public static void SendGiftProduct(Map<String,Object> map ) {
        String mssage = JSON.toJSONString(map);
        ProductMQ.sendMessage(MqToppic.sendGiftThree, mssage);
    }
}
