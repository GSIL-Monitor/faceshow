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
import com.faceshow.modules.sys.dao.SysFastdfsDao;
import com.faceshow.modules.sys.entity.SysFastdfs;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.video.dao.VideoInfoDao;
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
 * 视频点赞推送通知Custom
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
@Component
public class VideoLikePushCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private VideoInfoDao videoInfoDao;

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_videoLikePush.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_videoLikePush.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 视频点赞消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                        // 获取视频发布者id
                        Integer accountId = videoInfoDao.findUserIdByVideoId(pushBeanUtils.getInfoId());

                        // 获取点赞人的昵称
                        String nickName = userInfoDao.selectNick_name(pushBeanUtils.getUserId());

                        // 保存推送内容
                        String format = MessageFormat.format(JpushTemplet.VIDEO_LIKE, nickName);

                        // 把站内信存储到数据库
                        userInfoDao.insertMessage_push_info(JpushTemplet.VIDEO_LIKE_TYPE, pushBeanUtils.getUserId(), accountId, new Date(),format);

                        // 查询通知开关状态是否开启
                        Map<String, Object> account = messagePushSummaryUtlis.SelectMessage_push_user_set(accountId);
                        if (account.get("is_like").toString().equalsIgnoreCase("1")) {
                            // 推送设置参数
                            Map<String, String> JpushPrameter = new HashMap<>(0);
                            JpushPrameter.put("type_id", JpushTemplet.VIDEO_LIKE_TYPE.toString());
                            //用户ID 请注意：如果你是点击自己的个人主页 那么这个字段传递自己的ID 如果你点击的是别人的主页那么这个字段传递查看人的ID https://www.easyapi.com/api/view/93328?documentId=12042&themeId=&categoryId=21905
                            JpushPrameter.put("userId", pushBeanUtils.getUserId().toString());
                            JpushPrameter.put("account", accountId.toString());
                            JpushPrameter.put("videoId", pushBeanUtils.getInfoId().toString());

                          //  MessagePushSummary message = messagePushSummaryUtlis.getRedisMessage_push_summary(1);

                            // 开始推送
                         //   JpushUtils.buildIOSParameter(nickName + message.getBeforeContent() + message.getAfterContent(), JpushPrameter, accountId.toString());
                         //   JpushUtils.buildAndroidParameter(nickName + message.getBeforeContent() + message.getAfterContent(), "", JpushPrameter, accountId.toString());
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
