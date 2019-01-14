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
import com.faceshow.common.utils.push.Jpush.JpushTemplet;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.gift.dao.GiftInfoDao;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.service.UserInfoService;
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
 * @Date:2018/4/28 11:19
 * @param: user_id 提现人 user_name提现人姓名  price提现额度价    `types` '1支付宝 2微信 4银行卡 5贝宝支付', create_time 0000-00-00 00:0:00 类型的字符串 注意格式化
 * @explain：系统通知 F币提现成功 的推送通知
 * @return:
 */
@Component
public class SysNotificationFSuccessCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private GiftInfoDao giftInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.WITHDRAW_SUCCESS_TYPE.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.WITHDRAW_SUCCESS_TYPE.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("F币提现成功共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        Map<String,Object> map = JsonUtils.jsonToPojo(logJsonStr,Map.class);
                         map.put("is_type",9);//9提现
                        giftInfoDao.insertGiveAwayLog(map);//插入总表
                            //3.推送
                            //插入站内信
                            userInfoDao.insertMessage_push_info(JpushTemplet.WITHDRAW_SUCCESS_TYPE,0,Integer.parseInt(map.get("user_id").toString()),new Date(), JpushTemplet.WITHDRAW_SUCCESS);//(@Param("type_id") int type_id,@Param("user_id") int user_id,@Param("account_id") int account_id,@Param("create_time") Date create_time);
                            Map<String, Object> accountId = messagePushSummaryUtlis.SelectMessage_push_user_set(Integer.parseInt(map.get("user_id").toString()));//查看通知开关是否是开启的

                                Map<String, String> JpushPrameter = new HashMap<>();//推送設置參數
                                JpushPrameter.put("type_id",JpushTemplet.WITHDRAW_SUCCESS_TYPE.toString());
                                JpushUtils.buildIOSParameter(JpushTemplet.WITHDRAW_SUCCESS,JpushPrameter,map.get("user_id").toString());//String alert, Map<String, String> map, String... alias
                                JpushUtils.buildAndroidParameter(JpushTemplet.WITHDRAW_SUCCESS, "",JpushPrameter,map.get("user_id").toString());//String alert, String title, Map<String, String> map, String... alias


                        logger.info("F币提现成功已经处理消息一条");
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
