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
import com.faceshow.modules.friend.dao.FriendRingDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 朋友圈点赞数量修改操作消费者
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/23 16:11
 * -------------------------------------------------------------- <br>
 */
@Component
public class FriendLikeNumCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private FriendRingDao friendRingDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_friendLikeNum.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_friendLikeNum.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 用户朋友圈点赞记录消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        NumUtils numUtils = JSON.parseObject(logJsonStr, NumUtils.class);
                        friendRingDao.updateLikeNum(numUtils);
                        int userId = friendRingDao.selectUserIdByFriendId(numUtils.getId());//  <!--根据主键查询userId-->
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
