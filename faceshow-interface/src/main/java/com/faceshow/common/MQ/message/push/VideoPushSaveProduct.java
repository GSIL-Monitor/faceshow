package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.common.utils.PushBeanUtils;
import com.faceshow.modules.user.vo.UserAttentionSetRowVo;

import java.util.List;

/**
 * 发布视频推送至粉丝的站内信保存到数据库
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
public class VideoPushSaveProduct {

    /**
     * 发布视频推送至粉丝
     *
     * @param attentionSets 保存站内信信息
     */
    public static void pushVideoSave(List<UserAttentionSetRowVo> attentionSets) {
        String mssage = JSON.toJSONString(attentionSets);
        ProductMQ.sendMessage(MqToppic.top_videoPushSave, mssage);
    }
}
