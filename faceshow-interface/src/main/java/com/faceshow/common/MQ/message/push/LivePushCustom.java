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
 * @author: YS
 * @Date:2018/4/26 11:30
 * @param:
 * @explain： 主播开播的消费者
 * @return:
 */
@Component
public class LivePushCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;


    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.live_info.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.live_info.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("主播开播消息共收到:" + list.size());

                    for (MessageExt msg : list) {
                        logger.info("主播开播处理消息:" );
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);
                        String nick_name = userInfoDao.selectNick_name(pushBeanUtils.getUserId());
                        List<Map<String, Object>> userId = messagePushSummaryUtlis.selectIs_anchor(pushBeanUtils.getUserId());
                        for (Map<String, Object> account : userId) {
                            //添加到站内信
                            userInfoDao.insertMessage_push_info(JpushTemplet.ANCHOR_TYPE,pushBeanUtils.getUserId(),Integer.parseInt(account.get("user_id").toString()), new Date(), MessageFormat.format(JpushTemplet.LIVE_START,nick_name));//(@Param("type_id") int type_id,@Param("user_id") int user_id,@Param("account_id") int account_id,@Param("create_time") Date create_time);

                            if (account.get("is_anchor").toString().equalsIgnoreCase("1")) {//如果通知是打开的 就发送通知
                                Map<String, String> JpushPrameter = new HashMap<>();//推送設置參數
                                JpushPrameter.put("type_id", JpushTemplet.ANCHOR_TYPE.toString());
                                JpushUtils.IOSParameterTag( MessageFormat.format(JpushTemplet.LIVE_START,nick_name), JpushPrameter, pushBeanUtils.getUserId().toString());//
                                JpushUtils.androidParameterTag( MessageFormat.format(JpushTemplet.LIVE_START,nick_name), "", JpushPrameter, pushBeanUtils.getUserId().toString());//String alert,  String title,Map<String,String>map,String... registrationId
                            }
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
