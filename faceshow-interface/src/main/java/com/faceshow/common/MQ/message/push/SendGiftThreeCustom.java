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
import com.faceshow.modules.live.dao.LiveInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendGiftThreeCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;
    @Autowired
    private LiveInfoDao liveInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.sendGiftThree.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.sendGiftThree.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("送礼物MQ共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        Map<String,Object> map =JSON.parseObject(logJsonStr, Map.class);


                        int  diamond=Integer.parseInt(map.get("diamond").toString());
                        int  price=  Integer.parseInt(map.get("price").toString());
                        int  numbers=   Integer.parseInt(map.get("numbers").toString());
                        int interval = price * numbers * 60;
                        map.put("interval", interval);
                        Map<String, Object> time = liveInfoDao.selectaddTime(map);
                        liveInfoDao.addTime(time);//1V1更新时间
                        map.put("ITEM_ID", map.get("finish_id"));//插入总表用--------
                        SendGiftProduct.SendGiftProduct(map);
                        logger.info("Three送礼物消息已经处理一条");


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
