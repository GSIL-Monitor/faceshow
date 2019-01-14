package com.faceshow.common.utils.push.Jpush;

import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.PushModel;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;

public class NotificationFix  {
    public static Notification.Builder newBuilder() {
        return new Notification.Builder();
    }
    public static Notification ios(Object alert, Map<String, String> extras) {
        return newBuilder()
                .addPlatformNotification(IosNotification.newBuilder()
                        .setAlert(alert)
                        .addExtras(extras)
                        .setSound("default")
                        .build())
                .build();
    }

}
