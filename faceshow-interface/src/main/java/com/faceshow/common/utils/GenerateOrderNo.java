package com.faceshow.common.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * CopyRright (c)2007-2013: 依合软件部<br>
 * deprecated: 使用UUID生成订单唯一方法
 * Comments: 无<br>
 * JDK version: 1.7.0_01 <br>
 * Namespace: com.thinkgem.jeesite.common.Broadcastutils <br>
 * extends： <br>
 * implements：无<br>
 * -------------------------------------------------------------- <br>
 * V1.0 Created by GaoSX on 2017/01/11 10:21
 * -------------------------------------------------------------- <br>
 */
public class GenerateOrderNo {

    public static String getOrderIdByUUId() {
//        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型 return machineId + String.format("%015d", hashCodeV);
        return newDate()+String.format("%d", hashCodeV);
    }

    private static String newDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

//    public static void main(String[] args) {
//        System.out.println(getOrderIdByUUId());
//        System.out.println(newDate());
//        System.out.println("长度："+getOrderIdByUUId());
//    }

}




