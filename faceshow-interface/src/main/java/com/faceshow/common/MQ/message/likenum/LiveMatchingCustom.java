package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.utils.JsonUtils;
import com.faceshow.common.utils.NumUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.friend.dao.FriendRingDao;
import com.faceshow.modules.live.dao.LiveInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: YS
 * @Date:2018/4/25 9:32
 * @param:
 * @explain： 1V1匹配成功后 消费者
 * @return:
 */
@Component
public class LiveMatchingCustom implements CommandLineRunner {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private LiveInfoDao liveInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.live_matching_finish.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.live_matching_finish.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("1V1匹配结束共条数:" + list.size());
                     int i=0;
                    for (MessageExt msg : list) {
                        i+=1;
                        logger.info("1V1匹配结束 处理"+i+"条数:");
                        String logJsonStr = new String(msg.getBody());
                        Map<String, Object> map = JsonUtils.jsonToPojo(logJsonStr, new HashMap<String, Object>().getClass());
                        Map<String, Object> historyMatching = liveInfoDao.historyMatching(map);
                        if (!ObjectUtils.isEmpty(historyMatching)) {//如果匹配过了 就在匹配次数上面加一 并且更新匹配时间
                            historyMatching.put("update_time", new Date());
                            liveInfoDao.updateNum(historyMatching);
                        } else {
                            map.put("create_time", new Date());
                            map.put("update_time", new Date());
                            liveInfoDao.intervalLiveMatchingFinish(map);// <!--插入1V1匹配中表-->
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
