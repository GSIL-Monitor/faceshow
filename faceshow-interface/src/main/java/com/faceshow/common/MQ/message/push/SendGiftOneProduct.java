package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.PushBeanUtils;
import com.google.gson.Gson;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author: YS
 * @Date:2018/4/26 20:17
 * @param:
 * @explain：赠送礼物
 * @return:
 */
public class SendGiftOneProduct {
    public static void SendGiftProduct(Map<String,Object> map ) {
        String mssage = JsonUtils.objectToJson(map);
        ProductMQ.sendMessage(MqToppic.sendGiftOne, mssage);
    }
}
