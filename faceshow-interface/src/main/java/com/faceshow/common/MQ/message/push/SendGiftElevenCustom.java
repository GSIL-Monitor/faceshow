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

@Component
public class SendGiftElevenCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private GiftInfoDao giftInfoDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.Eleven.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.Eleven.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("不是好友想要聊天共接到消息:" + list.size()+"条");
                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        Map<String,Object> map = JsonUtils.jsonToPojo(logJsonStr,Map.class);
                        Map<String, Object> userChat = userInfoService.selectUser_Chat(map);
                        if (userChat==null){
                            //如果为NULL 说明还不是好友 必须赠送礼物才可以聊天
                            //1.插入user_chat表
                            userInfoDao.insertUser_chat(map);
                            //2.赠送礼物
                            SendGiftProduct.SendGiftProduct(map);//插入总表
                            //3.推送
                            //插入站内信
                            userInfoDao.insertMessage_push_info(JpushTemplet.GIFT_CHAT_TYPE,Integer.parseInt(map.get("user_id").toString()),Integer.parseInt(map.get("account_id").toString()),new Date(), MessageFormat.format(JpushTemplet.l2l_GIFT,map.get("user_name"),map.get("gname")));//(@Param("type_id") int type_id,@Param("user_id") int user_id,@Param("account_id") int account_id,@Param("create_time") Date create_time);
                            Map<String, Object> accountId = messagePushSummaryUtlis.SelectMessage_push_user_set(Integer.parseInt(map.get("account_id").toString()));//查看通知开关是否是开启的
                            if (accountId.get("is_gift_chat").toString().equalsIgnoreCase("1")){//如果通知是打开的 就发送通知
                                Map<String, String> JpushPrameter = new HashMap<>();//推送設置參數
                                JpushPrameter.put("type_id",JpushTemplet.GIFT_CHAT_TYPE.toString());
                                JpushUtils.buildIOSParameter(MessageFormat.format(JpushTemplet.l2l_GIFT,map.get("user_name"),map.get("gname")),JpushPrameter,map.get("account_id").toString());//String alert, Map<String, String> map, String... alias
                                JpushUtils.buildAndroidParameter(MessageFormat.format(JpushTemplet.l2l_GIFT,map.get("user_name"),map.get("gname")), "",JpushPrameter,map.get("account_id").toString());//String alert, String title, Map<String, String> map, String... alias
                            }
                        }
                        logger.info("不是好友想要聊天已经处理消息一条");
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
