package com.faceshow.common.MQ.message.tencent;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.alipay.MailMessageBean;

/**
 * 邮件发送生产者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 20:07
 * -------------------------------------------------------------- <br>
 */
public class EmailSendProduct {

    /**
     * 邮件发送生产者
     *
     * @param email   邮件地址
     * @param tital   邮件标题
     * @param content 邮件内容
     */
    public static void pushFriendAt(String email, String tital, String content) {
        MailMessageBean mailMessageBean = new MailMessageBean(email, tital, content);
        String mssage = JSON.toJSONString(mailMessageBean);
        ProductMQ.sendMessage(MqToppic.top_emailSend, mssage);
    }
}
