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
import com.faceshow.config.RocketMQConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendGiftSevenCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.sendGiftSeven.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.sendGiftSeven.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("送礼物MQ共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);


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
