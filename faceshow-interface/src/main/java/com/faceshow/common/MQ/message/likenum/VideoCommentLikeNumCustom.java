package com.faceshow.common.MQ.message.likenum;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.faceshow.common.MQ.MqToppic;
import com.faceshow.common.utils.NumUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.video.dao.VideoCommentDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 视频评论点赞数量修改操作消费者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 15:15
 * -------------------------------------------------------------- <br>
 */
@Component
public class VideoCommentLikeNumCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private VideoCommentDao videoCommentDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_videoCommentLikeNum.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_videoCommentLikeNum.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                                ConsumeConcurrentlyContext Context) {
                    logger.info("custom 视频评论点赞数量消费 条数:" + list.size());
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        NumUtils numUtils = JSON.parseObject(logJsonStr, NumUtils.class);
                        videoCommentDao.updateCommentLikeNum(numUtils);
                        int userId = videoCommentDao.selectUserIdByCommentId(numUtils.getId());// <!--根据评论查看userId-->
                        userInfoDao.updateCharm_values(numUtils.getNum(),userId);//根据userId更新魅力值

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
