package com.Jpush;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpushUtils {
    private static  final Logger LOG = LoggerFactory.getLogger(JpushUtils.class);//添加莎拉分日志，方便查看错误日志
    private static final String APP_KEY = "737cea7b852bf8c60cecbf51"; // 必填，请看应用
    private static final String MASTER_SECRET = "c0e2c47796f2095cbc56a516";// ;//必填，每个应用都对应一个masterSecret
    public static void main(String[] args) {
       JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            SMS sms = SMS.content("Test SMS", 10);//sms 消息内容体。是被推送到客户端的内容。与 notification 一起二者必须有其一，可以二者并存
            PushResult sendNotificationAll = jpushClient.sendNotificationAll("123");//JPushClient已经封装了很多方法
            LOG.info("Got result - " + sendNotificationAll);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


}
