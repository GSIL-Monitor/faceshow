package com.faceshow.modules.Broadcast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation:  生成推流地址后面的签名  getFinalUrl ()方法
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.Broadcast<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/3/9 9:49
 * -------------------------------------------------------------- <br>
 */
public class utils {
    private final  static String KEY="31999df7e29c15a56dfcf00b8e4480e0";//推流防盗链Key
    private final  static String BASEURL="rtmp://17250.livepush.myqcloud.com/live/";
    private final  static String ZHUBOID= UUID.randomUUID().toString();
    private final  static String STREAMID="17250_"+ZHUBOID;
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /*
                * KEY+ stream_id + txTime
                */
    private static String getSafeUrl(String key, String streamId, long txTime) {
        String input = new StringBuilder().
                append(key).
                append(streamId).
                append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            txSecret  = byteArrayToHexString(
                    messageDigest.digest(input.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return txSecret == null ? "" :
                new StringBuilder().
                        append("txSecret=").
                        append(txSecret).
                        append("&").
                        append("txTime=").
                        append(Long.toHexString(txTime).toUpperCase()).
                        toString();
    }

    private static String byteArrayToHexString(byte[] data) {
        char[] out = new char[data.length << 1];

        for (int i = 0, j = 0; i < data.length; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }
    /**
     *@Author:YS
     *@Description:
     * 获取24小时以后的时间
     * 我们的客户一般会将 txTime 设置为当前时间 24 小时以后过期，过期时间不要太短，
     * 当主播在直播过程中遭遇网络闪断时会重新恢复推流，如果过期时间太短，主播会因为推流 URL 过期而无法恢复推流。
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */

    private  static  Long getTime(){
        long currentTime = System.currentTimeMillis()+ 60*60*1000;
        return currentTime;
    }

    private static  String  getFinalUrl (){
        String finalUrl=BASEURL+STREAMID+"?"+getSafeUrl(KEY,STREAMID,getTime());
        return finalUrl;
    }
    public static void main(String[] args) {
        System.out.println( getFinalUrl ());
    }
}
