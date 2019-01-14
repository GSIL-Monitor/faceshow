package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.modules.video.entity.VideoPlayLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户观看记录生产者
 */
public class VideoPlayLogProduct {

    public static void sendVideoPlayMessage(VideoPlayLog log){
        List<VideoPlayLog> logList = new ArrayList<>();
        logList.add(log);
        sendVideoPlayMessage(logList);
    }
    public static void sendVideoPlayMessage(List<VideoPlayLog> logList){
        String mssage = JSON.toJSONString(logList);
        ProductMQ.sendMessage(MqToppic.top_videoPlayLog, mssage);
    }
}
