package com.faceshow.common.MQ.message.push;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.MessagePushSummaryUtlis;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.gift.dao.GiftInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.Map;

@Component
public class SendGiftCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private GiftInfoDao giftInfoDao;

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.senGift.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.senGift.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("插入总表共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        Map<String,Object> map = JsonUtils.jsonToPojo(logJsonStr,Map.class);
                        giftInfoDao.insertGiveAwayLog(map);
                        Map<String, Object> giftHaving = giftInfoDao.giftHaving(map);//用户是否拥有这个礼物 有的话在原来的基础上加上刚才赠送的 没有的话插入表一条记录
                        if (ObjectUtils.isEmpty(giftHaving)) {
                            //如果是空 就说明这个人原来没有这个礼物 需要插入用户拥有礼品表一条记录
                            giftInfoDao.insertGiftUserInfo(map);
                        } else {
                            map.put("info_id", giftHaving.get("info_id"));
                            giftInfoDao.updateNumber(map);
                        }
                        if (map.get("diamond")!=null){//==NULL 説明是自己的礼物赠送
                            int  diamond=Integer.parseInt(map.get("diamond").toString());
                            int  price= Integer.parseInt(map.get("price").toString());
                            int  numbers= Integer.parseInt(map.get("numbers").toString());
                            map.put("diamond", (diamond - price * numbers));
                            giftInfoDao.updateMoney(map);//更新钻石的数量
                        }

                        logger.info("插入总表已经处理消息一条");
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
