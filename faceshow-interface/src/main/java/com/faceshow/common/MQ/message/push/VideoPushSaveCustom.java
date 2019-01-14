package com.faceshow.common.MQ.message.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.utils.MessagePushSummaryUtlis;
import com.faceshow.common.utils.PushBeanUtils;
import com.faceshow.common.utils.push.Jpush.JpushTemplet;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.vo.UserAttentionSetRowVo;
import com.faceshow.modules.video.entity.VideoPlayLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 发布视频推送至粉丝的站内信保存到数据库
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
@Component
public class VideoPushSaveCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_videoPushSave.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_videoPushSave.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 视频发布推送至粉丝记录保存到数据库消费 条数:" + list.size());
                    List<UserAttentionSetRowVo> attentions = new ArrayList<>(0);
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        List<UserAttentionSetRowVo> userAttentionSetRowVos = JSON.parseArray(logJsonStr, UserAttentionSetRowVo.class);
                        String nickName = userInfoDao.selectNick_name(Integer.parseInt(userAttentionSetRowVos.get(0).getUserId()));
                        int size = userAttentionSetRowVos.size();
                        for (int i = 0; i < size; i++) {
                            attentions.add(userAttentionSetRowVos.get(i));
                            if ((i % 15 == 0) || (i == size - 1)) {
                                userInfoDao.insertMessage_push_info_batch(attentions, new Date(), JpushTemplet.VIDEO_CREATE_TYPE, MessageFormat.format(JpushTemplet.VIDEO_PUSH, nickName));
                                attentions = new ArrayList<>(0);
                            }
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
