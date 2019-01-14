package com.faceshow.modules.Broadcast;
import com.faceshow.common.utils.HttpClientUtil;

import java.security.MessageDigest;
/**
 * CopyRright (c)2007-2018: 国超软件研发部<br>
 * Explanation: getSign() 获取API的签名 2分钟后过期
 * Project: faceshow<br>
 * Class Type: <br>
 * Comments: 无<br>
 * JDK version: 1.8.0 <br>
 * Namespace: com.faceshow.modules.Broadcast<br>
 * extends：<br>
 * implements：<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 杨森 on 2018/3/9 11:36
 * -------------------------------------------------------------- <br>
 */
public class BroadcastApiutils {
    public final static String APPID="1252173102";//APPID直播
    public final static String KEY="0221ad6e7101a726cdd9faaa5f9f031d";//API鉴权key

    /**
     *@Author:YS
     *@Description: 获取时间 2分钟后URL过期
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */

   public   static String getTime(){
       long currentTime = System.currentTimeMillis()/1000+ 2*60;
       System.out.println(currentTime);
       return currentTime+"";
   }
    public   static String TengetTime(){
        long currentTime = System.currentTimeMillis()/1000+ 10*60;
        System.out.println(currentTime);
        return currentTime+"";
    }

    /**
     *@Author:YS
     *@Description: 获取Sign
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */

    public  static String getSign(){
       return md5(KEY+getTime());
    }
    //十分钟后过期
    public  static String TengetSign(){
        return md5(KEY+TengetTime());
    }




    /**
     *@Author:YS
     *@Description: MD5
     *@Date:2018/3/9
     *@param:No such property: code for class: Script1
     */

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(md5("0221ad6e7101a726cdd9faaa5f9f031d1521162389"));//获取时间和SIGN
        String app_id = BroadcastApiutils.APPID;
        String URL = "http://fcgi.video.qcloud.com/common_access?t=%s&appid=%s&sign=%s&interface=%s";//业务API
        String format = String.format(URL, BroadcastApiutils.getTime(), app_id, BroadcastApiutils.getSign(), "Mix_StreamV2");
        System.out.println("format后的URL为--------"+format);
           String json="{\n" +
                   "    \"timestamp\": 1521456375,\n" +
                   "    \"eventId\": 1521456375,\n" +
                   "    \"interface\": {\n" +
                   "        \"interfaceName\": \"Mix_StreamV2\",\n" +
                   "        \"para\": {\n" +
                   "            \"app_id\": 1252173102,\n" +
                   "            \"interface\": \"mix_streamv2.start_mix_stream_advanced\",\n" +
                   "            \"mix_stream_session_id\": \"11111_enson\",\n" +
                   "            \"output_stream_id\": \"17250_2ddtnt0a\",\n" +
                   "            \"output_stream_type\": 0,\n" +
                   "            \"input_stream_list\": [\n" +
                   "                {\n" +
                   "                    \"input_stream_id\": \"17250_i6ekzm5w\",\n" +
                   "                    \"layout_params\": {\n" +
                   "                        \"image_layer\": 1,\n" +
                   "                        \"input_type\": 3,\n" +
                   "                        \"color\": \"0x000000\",\n" +
                   "                        \"image_height\": 1920,\n" +
                   "                        \"image_width\": 1080\n" +
                   "                    }\n" +
                   "                },\n" +
                   "                {\n" +
                   "                    \"input_stream_id\": \"17250_2ddtnt0a\",\n" +
                   "                    \"layout_params\": {\n" +
                   "                        \"image_layer\": 2,\n" +
                   "                        \"image_width\": 405,\n" +
                   "                        \"image_height\": 720,\n" +
                   "                        \"location_x\": 100,\n" +
                   "                        \"location_y\": 150\n" +
                   "                    }\n" +
                   "                },\n" +
                   "                {\n" +
                   "                    \"input_stream_id\": \"17250_72rllt72\",\n" +
                   "                    \"layout_params\": {\n" +
                   "                        \"image_layer\": 3,\n" +
                   "                        \"image_width\": 405,\n" +
                   "                        \"image_height\": 720,\n" +
                   "                        \"location_x\": 555,\n" +
                   "                        \"location_y\": 150\n" +
                   "                    }\n" +
                   "                },\n" +
                   "                {\n" +
                   "                    \"input_stream_id\": \"17250_al1zhuw2\",\n" +
                   "                    \"layout_params\": {\n" +
                   "                        \"image_layer\": 4,\n" +
                   "                        \"image_width\": 405,\n" +
                   "                        \"image_height\": 720,\n" +
                   "                        \"location_x\": 100,\n" +
                   "                        \"location_y\": 920\n" +
                   "                    }\n" +
                   "                },\n" +
                   "                {\n" +
                   "                    \"input_stream_id\": \"17250_112s7soi\",\n" +
                   "                    \"layout_params\": {\n" +
                   "                        \"image_layer\": 5,\n" +
                   "                        \"image_width\": 405,\n" +
                   "                        \"image_height\": 720,\n" +
                   "                        \"location_x\": 555,\n" +
                   "                        \"location_y\": 920\n" +
                   "                    }\n" +
                   "                }\n" +
                   "            ]\n" +
                   "        }\n" +
                   "    }\n" +
                   "}";
        String date = HttpClientUtil.doPostJson(format, json);
        System.out.println("腾讯返回的数据为--------"+date);
    }
}
