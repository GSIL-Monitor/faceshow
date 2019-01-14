package com.faceshow.common.MQ;

import java.util.List;
  
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;  
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;  
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;  
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;  
import com.alibaba.rocketmq.client.exception.MQClientException;  
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;  
import com.alibaba.rocketmq.common.message.Message;  
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.config.RocketMQConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * mq消费者
 */
@Component
public class Customs implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void run(String... strings) throws Exception {
        //支付消费
        initTop_pay();
        //一对一
        initTop_reward();
    }

    private void initTop_pay(){
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_pay.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_pay.toString(), null);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                                ConsumeConcurrentlyContext Context) {
                    for (MessageExt msg: list) {
                        System.out.println("top_pay__收到数据：" + new String(msg.getBody()));
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
    private void initTop_reward(){
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_reward.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_reward.toString(), null);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                                ConsumeConcurrentlyContext Context) {
                    for (MessageExt msg: list) {
                        System.out.println("top_reward__收到数据：" + new String(msg.getBody()));
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