package com.faceshow.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author :ljh
 * @Description   高德地图 获取距离或者经纬度
 * @Date Created in 10:39,2017/12/25
 * @ModifyBy:
 */
public class GetDistance {

    public   static  String  key ="fd167c767a5606555397066de97da319";// 高德地图的应用key

    public static void main(String[] args){
        String start = "郑州市金水区北三环索凌路豫军长基花园五号楼1007";
        String end = "金水区东风路经三路";

        String startLonLat = getLonLat(start);//经纬度字符串:113.681543,34.796965
        String endLonLat = getLonLat(end);

        System.out.println(startLonLat);
        System.out.println(endLonLat);
        Long dis = getDistance(startLonLat,endLonLat);
        System.out.println(dis);
    }

    public static String getLonLat(String address){
        //返回输入地址address的经纬度信息, 格式是 经度,纬度
        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key="+key+"&address="+address;
        String queryResult = getResponse(queryUrl);  //高德接品返回的是JSON格式的字符串
        JSONObject jo = new JSONObject().fromObject(queryResult);
        System.out.println(jo.toString());
        JSONArray ja = jo.getJSONArray("geocodes");
        return new JSONObject().fromObject(ja.getString(0)).get("location").toString();
    }

    public static Long getDistance(String startLonLat, String endLonLat){
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = new Long(0);
     //   String queryUrl = "http://restapi.amap.com/v3/distance?key=<span style=\"font-family:Arial, Helvetica, sans-serif;\">fd167c767a5606555397066de97da319</span><span style=\"font-family:Arial, Helvetica, sans-serif;\">&origins="+startLonLat+"&destination="+endLonLat+"</span>";
        String queryUrl= "http://restapi.amap.com/v3/distance?origins="+startLonLat+"&destination="+endLonLat+"&key="+key;

        String queryResult = getResponse(queryUrl);
        JSONObject jo = new JSONObject().fromObject(queryResult);
        JSONArray ja = jo.getJSONArray("results");

        result = Long.parseLong(new JSONObject().fromObject(ja.getString(0)).get("distance").toString());
        return result;
//        return queryResult;
    }

    public static String getResponse(String serverUrl){
        //用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = in.readLine()) != null){
                result.append(line);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
