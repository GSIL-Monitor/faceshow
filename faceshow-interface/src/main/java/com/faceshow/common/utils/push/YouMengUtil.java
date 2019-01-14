package com.faceshow.common.utils.push;

import com.faceshow.common.utils.push.android.AndroidUnicast;
import com.faceshow.common.utils.push.ios.IOSUnicast;
import com.faceshow.common.utils.push.android.AndroidBroadcast;
import com.faceshow.common.utils.push.ios.IOSBroadcast;

import java.util.Map;

/**
 * @author :ljh
 * @Description
 * @Date Created in 15:18,2017/12/17
 * @ModifyBy:
 */
public class YouMengUtil {  // 需要注意下 安卓和ios 可能需要不同的appkey和AppMasterSecrect

    private static String appkey ="5a2e4f3fb27b0a2587000016";
    private static String appMasterSecret = "x24dhymkb56c3ry6cwaprvzt7pzc0epg";
    private static String timestamp = null;
    private static PushClient client = new PushClient();

    /**
     * 安卓单推送
     * @throws Exception
     */

    public static void  sendAndroidUnicast(Map<String,String> map) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken( "ArSZKByWLgByIatWYSSUkwsV2FyRhnZaAPIQmICya0SM");
        unicast.setDeviceToken(map.get("deviceToken"));
        unicast.setTicker(map.get("ticker")); // 必填 通知栏提示文字
        unicast.setTitle(map.get("title"));    // 必填 通知标题
        unicast.setText(map.get("text"));     // 必填 通知文字描述
        unicast.setCustomField(map.get("custom"));       //DisplayType 为message 的时候需要添加
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        unicast.setProductionMode();
        // Set customized fields
        unicast.setExtraField("test", "helloworld");
        client.send(unicast);
    }


    /**
     * ios  单独推送
     * @throws Exception
     */
    public static void sendIOSUnicast(Map<String,String> map) throws Exception {
        IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken(map.get("deviceToken"));
        unicast.setAlert(map.get("alert"));
        unicast.setBadge( 0);
        unicast.setSound( "default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        unicast.setTestMode();
        // Set customized fields
        unicast.setCustomizedField("test", "helloworld");
        client.send(unicast);
    }

    /**
     * 安卓 全部发送
     * @throws Exception
     */
    public static void sendAndroidBroadcast(Map<String,String> map) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
        broadcast.setTicker(map.get("ticker"));
        broadcast.setTitle(map.get("title"));
        broadcast.setText(map.get("text"));
        broadcast.setCustomField(map.get("custom"));
        broadcast.goAppAfterOpen();
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setExtraField("test", "helloworld");
        client.send(broadcast);
    }


    /**
     * ios  全部推送
     * @param map
     * @throws Exception
     */

    public static void sendIOSBroadcast(Map<String,String> map) throws Exception {

        IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

        broadcast.setAlert(map.get("alert"));
        broadcast.setBadge( 0);
        broadcast.setSound( "default");
        // TODO set 'production_mode' to 'true' if your app is under production mode
        broadcast.setTestMode();
        // Set customized fields
        broadcast.setCustomizedField("test", "helloworld");
        client.send(broadcast);
    }



}
