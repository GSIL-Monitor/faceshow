package com.faceshow.modules.Broadcast;

import com.faceshow.common.utils.InvertCodeGenerator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: 生成推流和拉流的地址
 * 生成推流地址后面的签名  getFinalUrl ()方法
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
public class Broadcastutils {
    private final  static String KEY="31999df7e29c15a56dfcf00b8e4480e0";//推流防盗链Key
    private final  static String LIVEPUSHURL="rtmp://17250.livepush.myqcloud.com/live/";//推流
    private final  static String LIVEPLAYURL="http://17250.liveplay.myqcloud.com/live/";//拉流
    private final  static String RTMP="rtmp://17250.liveplay.myqcloud.com/live/";//拉流rtmp格式
   // public final  static String ZHUBOID= InvertCodeGenerator.genCodes(8, 1).get(0);
    //public final  static String STREAMID="17250_"+InvertCodeGenerator.genCodes(8, 1).get(0);
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
        long currentTime = System.currentTimeMillis()/1000+ 60*60*24;
        System.out.println(currentTime);
        return currentTime;
    }

    public static   Map<String, String> getLivePlayUrl (){
        String STREAMID="17250_"+InvertCodeGenerator.genCodes(8, 1).get(0);
        String finalUrl=LIVEPUSHURL+STREAMID+"?"+getSafeUrl(KEY,STREAMID,getTime());
        Map<String, String> map = new HashMap<String, String>();
        String rtmpUrl=RTMP+STREAMID;//播放地址 (RTMP)
        String flvUrl=LIVEPLAYURL+STREAMID+".flv";//播放地址 (FLV)
        String hlsUrl=LIVEPLAYURL+STREAMID+".m3u8";//播放地址 (HLS)
        map.put("rtmp_url",rtmpUrl);//拉一
        map.put("flv_url",flvUrl);//拉二
        map.put("hls_url",hlsUrl);//拉三
        map.put("finalUrl",finalUrl);//推流
        map.put("stream_id",STREAMID);//streamid
        return map;
    }
    public static void main(String[] args) {
        Map<String, String> livePlayUrl = getLivePlayUrl();//拉流
        Iterator<Map.Entry<String, String>> iterator = livePlayUrl.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key=" + key + " value=" + value);
        }
    }
}
