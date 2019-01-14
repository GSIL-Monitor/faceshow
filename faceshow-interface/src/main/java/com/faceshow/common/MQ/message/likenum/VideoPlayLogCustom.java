package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.video.dao.VideoInfoDao;
import com.faceshow.modules.video.dao.VideoPlayLogDao;
import com.faceshow.modules.video.entity.VideoPlayLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户观看记录消费者
 */
@Component
public class VideoPlayLogCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private VideoPlayLogDao videoPlayLogDao;

    @Autowired
    private VideoInfoDao videoInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_videoPlayLog.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_videoPlayLog.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                                ConsumeConcurrentlyContext Context) {
                    logger.info("custom 用户观看记录消费 条数:" + list.size());
                    List<VideoPlayLog> saveList = new ArrayList<>();
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        List<VideoPlayLog> videoPlayLogs = JSON.parseArray(logJsonStr, VideoPlayLog.class);
                        videoInfoDao.addplayNumByVideoId(videoPlayLogs);
                        saveList.addAll(videoPlayLogs);
                    }
                    videoPlayLogDao.saveBatch(saveList);

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
