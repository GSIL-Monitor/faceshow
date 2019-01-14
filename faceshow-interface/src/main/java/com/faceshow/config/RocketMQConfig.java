package com.faceshow.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.faceshow.common.MQ.ProductMQ;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * mq初始化类
 */
@Configuration
public class RocketMQConfig {
    private static final Log logger = LogFactory.getLog(RocketMQConfig.class);

    //消息生产
    private static DefaultMQProducer SENDER;


    private static String NAME_SERVER="43.249.223.101:9876";
    @Value("${rocketmq.producer.namesrvAddr}")
    public void setNameServer(String nameServer) {
        NAME_SERVER = nameServer;
    }

    private static int sendMsgTimeout=100;
    @Value("${rocketmq.producer.sendMsgTimeout}")
    public void setSendMsgTimeout(String sendMsgTimeout) {
        sendMsgTimeout = sendMsgTimeout;
    }

    //生产者组名
    private final static String PRODUCER_GROUP_NAME="faceShowMQ";


    //消费者组名
    private final static String CONSUMER_GROUP_NAME="faceShowMQ";

    /**
     * 生成生产者
     * @return
     */
    public static DefaultMQProducer init() {
        if(SENDER != null) return SENDER;
        synchronized (RocketMQConfig.class){
            if(SENDER != null) return SENDER;

            logger.info("Producer init name_server:" + NAME_SERVER);

            SENDER = new DefaultMQProducer(PRODUCER_GROUP_NAME);
            SENDER.setNamesrvAddr(NAME_SERVER);
            SENDER.setInstanceName(UUID.randomUUID().toString());
            try {
                SENDER.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }

            SENDER.setSendMsgTimeout(sendMsgTimeout);
            return SENDER;
        }

    }
    /**
     * 生成消费者
     * @return
     */
    public static DefaultMQPushConsumer initConsumer() {
        return initConsumer(CONSUMER_GROUP_NAME);
    }
    /**
     * 生成消费者
     * @return
     */
    public static DefaultMQPushConsumer initConsumer(String consumerGroup) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(NAME_SERVER);
        consumer.setConsumeMessageBatchMaxSize(1);
        logger.info("mq消费者 name_server:"+ NAME_SERVER+"  生成consumerGroup:" + consumerGroup);
        return consumer;
    }


}
