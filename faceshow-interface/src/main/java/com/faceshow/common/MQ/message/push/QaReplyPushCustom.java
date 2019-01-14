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
import com.faceshow.modules.qa.dao.QaInfoDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问答回复推送通知Custom
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/26 13:52
 * -------------------------------------------------------------- <br>
 */
@Component
public class QaReplyPushCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private QaInfoDao qaInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_qaReplyPush.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_qaReplyPush.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 问答回复推送消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                        // 根据问答id查询问答发布人
                        Integer userId = qaInfoDao.selectUserIdByInfoId(pushBeanUtils.getInfoId());

                        // 根据问答回复人id查询问答回复人昵称
                        String nickName = userInfoDao.selectNick_name(pushBeanUtils.getUserId());

                        // 设置推送内容
                        String format = MessageFormat.format(JpushTemplet.QA_REPLY, nickName);

                        // 向站内信插入一条数据
                        userInfoDao.insertMessage_push_info(JpushTemplet.QA_REPLY_TYPE, pushBeanUtils.getUserId(), userId, new Date(), format);

                        // 查询通知开关状态是否开启
                        Map<String, Object> map = messagePushSummaryUtlis.SelectMessage_push_user_set(userId);
                        if (map.get("is_qa").toString().equalsIgnoreCase("1")) {
                            // 推送设置参数
                            Map<String, String> JpushPrameter = new HashMap<>(0);
                            JpushPrameter.put("type_id", JpushTemplet.QA_REPLY_TYPE.toString());
                            JpushPrameter.put("infoId", pushBeanUtils.getInfoId().toString());

                            // 开始推送
                            JpushUtils.buildIOSParameter(format, JpushPrameter, userId.toString());
                            JpushUtils.buildAndroidParameter(format, "", JpushPrameter, userId.toString());
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
