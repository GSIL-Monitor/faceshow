package com.faceshow.common.utils.push.Jpush;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JpushUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JpushUtils.class);//添加莎拉分日志，方便查看错误日志
    private static final String APP_KEY = "4d1af1532f9751f62f1f56b1"; // 必填，请看应用
    private static final String MASTER_SECRET = "7bd78864fa9da9351877f68f";// ;//必填，每个应用都对应一个masterSecret


    public static void main(String[] args) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("extras", "azhao");
            TagAliasResult deviceTagAlias = jpushClient.getDeviceTagAlias("1114a8979284799728d");
            buildIOSParameter("收到了吧", hashMap, "18595906069");//String alert,Map<String,String>map,String... alias
            // buildAndroidParameter("收到了没有","",hashMap,"315");//String alert,  String title,Map<String,String>map,String... alias
            LOG.info("Got result - " + deviceTagAlias);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * @author: YS
     * @Date:2018/4/18 14:24
     * @param:
     * @explain： 带额外的参数 仅仅推送IOS 端口 带额外参数放Map里面跳转页面的时候使用
     * @return:
     */
    public static void buildIOSParameter(String alert, Map<String, String> map, String... alias) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(IOSParameterAlias(alert, map, alias));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


    /**
     * @author: YS
     * @Date:2018/4/18 14:24
     * @param:
     * @explain： 带额外的参数 仅仅推送IOS 端口 带额外参数放Map里面跳转页面的时候使用
     * @return:
     */
    public static void buildIOSParameter(String alert, Map<String, String> map, Collection<String> aliases) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(IOSParameterAliasCollection(alert, map, aliases));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * @author: YS
     * @Date:2018/4/18 14:24
     * @param:
     * @explain： 带额外的参数 仅仅推送安卓端口 带额外参数放Map里面跳转页面的时候使用
     * @return:
     */

    public static void buildAndroidParameter(String alert, String title, Map<String, String> map, String... alias) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(androidParameterAlias(alert, title, map, alias));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    public static void buildAndroidParameter(String alert, String title, Map<String, String> map, Collection<String> aliases) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(androidParameterAliasCollection(alert, title, map, aliases));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


    /**
     * @author: YS
     * @Date:2018/4/23 08:55
     * @param:
     * @explain： 带额外的参数 仅仅推送IOS 端口 带额外参数放Map里面跳转页面的时候使用
     * @return:
     */
    public static void IOSParameterTag(String alert, Map<String, String> map, String... tagValue) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(IOSParameterTagString(alert, map, tagValue));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * @author: YS
     * @Date:2018/4/23 08:55
     * @param:
     * @explain： 带额外的参数 仅仅推送IOS 端口 带额外参数放Map里面跳转页面的时候使用
     * @return:
     */
    public static void IOSParameterTag(String alert, Map<String, String> map, Collection<String> tagValues) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(IOSParameterTagCollection(alert, map, tagValues));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * @author: YS
     * @Date:2018/4/23 8:59
     * @param:
     * @explain：安卓带标签推送
     * @return:
     */
    public static void androidParameterTag(String alert, String title, Map<String, String> map, String... tagValue) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(androidParameterTagString(alert, title, map, tagValue));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


    /**
     * @author: YS
     * @Date:2018/4/23 8:59
     * @param:
     * @explain：安卓带标签推送
     * @return:
     */
    public static void androidParameterTag(String alert, String title, Map<String, String> map, Collection<String> tagValues) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            PushResult result = jpushClient.sendPush(androidParameterTagCollection(alert, title, map, tagValues));
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


    /**
     * @author: YS
     * @Date:2018/4/23 8:59
     * @param:
     * @explain：标签的设置
     * @return:
     */
    public static void updateDeviceTagAlias(String registrationId, String alias, Set<String> tagsToAdd, Set<String> tagsToRemove) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            DefaultResult result = jpushClient.updateDeviceTagAlias(registrationId, alias, tagsToAdd, tagsToRemove);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }

    /**
     * @author: YS
     * @Date:2018/4/23 9:53
     * @param:
     * @explain： 清除标签或者别名
     * @return:
     */

    public static void updateDeviceTagAlias(String registrationId, boolean clearAlias, boolean clearTag) {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        try {
            DefaultResult result = jpushClient.updateDeviceTagAlias(registrationId, clearAlias, clearTag);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }

    }


    //////////////////////////////////////////////////以下为封装的方法

    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送IOS 带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 别名推送 可变参数
     * @return:
     */
    private static PushPayload IOSParameterAlias(String alert, Map<String, String> map, String... alias) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + alias);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(NotificationFix.ios(alert, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }

    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送IOS 带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 别名推送 集合参数
     * @return:
     */
    private static PushPayload IOSParameterAliasCollection(String alert, Map<String, String> map, Collection<String> aliases) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + aliases);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(aliases))
                .setNotification(NotificationFix.ios(alert, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }


    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送IOS 带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 标签推送  可变参数
     * @return:
     */
    private static PushPayload IOSParameterTagString(String alert, Map<String, String> map, String... tagValue) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + tagValue);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagValue))
                .setNotification(NotificationFix.ios(alert, map))
                .setOptions(Options.sendno())
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }

    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送IOS 带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 集合参数 标签推送
     * @return:
     */
    private static PushPayload IOSParameterTagCollection(String alert, Map<String, String> map, Collection<String> tagValues) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + tagValues);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagValues))
                .setNotification(NotificationFix.ios(alert, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }


    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送android    带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 安卓别名 可变参数
     * @return:
     */
    private static PushPayload androidParameterAlias(String alert, String title, Map<String, String> map, String... alias) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + alias);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }

    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 仅仅推送android    带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 安卓别名 集合参数
     * @return:
     */
    private static PushPayload androidParameterAliasCollection(String alert, String title, Map<String, String> map, Collection<String> aliases) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + aliases);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(aliases))
                .setNotification(Notification.android(alert, title, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }


    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 根据标签 仅仅推送android    带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 安卓标签 可变参数
     * @return:
     */
    private static PushPayload androidParameterTagString(String alert, String title, Map<String, String> map, String... tagValue) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + tagValue);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagValue))
                .setNotification(Notification.android(alert, title, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }

    /**
     * @author: YS
     * @Date:2018/4/18 9:46
     * @param: 根据标签 仅仅推送android    带额外的参数 跳转页面需要使用 额外的参数放到 map里面
     * @explain： 安卓标签 集合参数
     * @return:
     */
    private static PushPayload androidParameterTagCollection(String alert, String title, Map<String, String> map, Collection<String> tagValues) {
        LOG.info("Got alert - " + alert + "---------------registrationId" + tagValues);
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tagValues))
                .setNotification(Notification.android(alert, title, map))
                .setOptions(OptionsFix.TimeToLive())
                .build();
    }

}
