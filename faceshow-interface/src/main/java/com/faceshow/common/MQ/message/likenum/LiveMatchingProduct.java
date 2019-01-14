package com.faceshow.common.MQ.message.likenum;

import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.JsonUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author: YS
 * @Date:2018/4/25 9:32
 * @param:
 * @explain： 1V1匹配成功后 需要插入数据库记录
 * @return:
 */
public class LiveMatchingProduct {

    public static void intervalLiveMatchingFinish(Map<String,Object>map) {
        String mssage = JsonUtils.objectToJson(map);
        ProductMQ.sendMessage(MqToppic.live_matching_finish, mssage);
    }
}
