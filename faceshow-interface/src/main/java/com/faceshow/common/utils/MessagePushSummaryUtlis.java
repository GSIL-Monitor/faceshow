package com.faceshow.common.utils;

import com.faceshow.modules.user.service.UserInfoService;
import com.faceshow.modules.user.vo.MessagePushSummary;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MessagePushSummaryUtlis {

    @Autowired
    private  UserInfoService userInfoService;

    /*消息推送通知用户设置ID主键  查看是否打开了消息推送*/
    public  Map<String,Object> SelectMessage_push_user_set(Integer userId){
        Map<String, Object> message_push_user_set = userInfoService.SelectMessage_push_user_set(userId);
        return message_push_user_set;
    }

    /*消息推送通知用户设置ID主键  查看是否打开了消息推送  主播专用*/
    public   List<Map<String, Object>> selectIs_anchor(Integer userId){
        List<Map<String, Object>> message_push_user_set = userInfoService.selectIs_anchor(userId);
        return message_push_user_set;
    }
}
