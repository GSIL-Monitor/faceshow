package com.faceshow.common.utils;

import com.faceshow.common.utils.mapbean.MapResultBean;

/**
 * 百度地图api
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/14 10:35
 * -------------------------------------------------------------- <br>
 */
public class BaiduMapUtils {

    /**
     * 百度地图的应用key
     */
    public static final String KEY = "7Oe1KUO6iS8kL5r7GowblrV9Q6aRyNEx";

    /**
     * 返回访问百度地图的url路径
     *
     * @param location 经纬度, 34.79893834575089,113.5379050027842
     * @return
     */
    private static String url(String location) {
        return "http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=" + location + "&output=json&pois=1&ak=" + KEY;
    }

    /**
     * 根据经纬度获取地址信息
     *
     * @param location 经纬度, 34.79893834575089,113.5379050027842
     * @return
     */
    public static MapResultBean getMsg(String location) {
        String result = HttpClientUtil.doGet(url(location));
        String substring = result.substring(result.indexOf("(") + 1);
        String substring1 = substring.substring(0, substring.lastIndexOf(")"));
        System.out.println(substring1);
        return JsonUtils.jsonToPojo(substring1, MapResultBean.class);
    }

    public static void main(String[] args) {
        MapResultBean msg = getMsg("39.89721007843248,116.33980999999993");
        String country_code_iso2 = msg.getResult().getAddressComponent().getCountry_code_iso2();
        System.out.println(country_code_iso2);
    }
}
