package com.faceshow.common.MQ.message.tencent;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.alipay.SMSMessageBean;

/**
 * 短信发送生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 20:07
 * -------------------------------------------------------------- <br>
 */
public class SMSSendProduct {

    /**
     * 短信发送生产者
     *
     * @param type     短信类型
     * @param areaCode 国际区号
     * @param mobile   电话号码
     * @param content  短信内容
     */
    public static void pushFriendAt(Integer type, String areaCode, String mobile, String content) {
        SMSMessageBean smsMessageBean = new SMSMessageBean(type, areaCode, mobile, content);
        String mssage = JSON.toJSONString(smsMessageBean);
        ProductMQ.sendMessage(MqToppic.top_smsSend, mssage);
    }
}
