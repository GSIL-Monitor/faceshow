package com.imTest;


import java.util.UUID;

public class Imutils {
    public final  static  String BASISAPI="https://console.tim.qq.com/";//基础URL
    //密钥生成的加密字段
    public final  static  String USERSIG ="eJxlj81Og0AYRfc8xYStRoafIWDipnVIUamYQhq7IZQZxs-SgcDQosZ3t8Umkni359zc3C8NIaQnT6ubvCjqXqpMfTRcR7dIx-r1H2waYFmuMrtl-yAfGmh5lpeKtyM0CSEWxlMHGJcKSrgYOduDnOCO7bJx47fvnMqu7fveVAExwoim8-BlvhhCSw3MewfZByK1W0oerkT5unZcXg6bKKaqquNqu6UiFM8MjkGVsmVyNHZsvZkZM*PtfmlKsz50n33wGC8Su6CFF9G7yaSCPb8cMl3ie9iZXjrwtoNajoKFT4pl43N07Vv7AXExXaM_";
    public final  static  String IDENTIFIER ="admin";//管理员账号
    public final  static  String SDKAPPID ="1400063998";//APPID
    public final  static  String RANDOM =UUID.randomUUID().toString();//随机数
    public final  static  String CONTENTTYPE ="json";//返回类型
    public final  static  String PUBLIC ="?usersig="+USERSIG+"&identifier="+IDENTIFIER+"&sdkappid="+SDKAPPID+"&random="+RANDOM+"&contenttype="+CONTENTTYPE;//共有参数



}
