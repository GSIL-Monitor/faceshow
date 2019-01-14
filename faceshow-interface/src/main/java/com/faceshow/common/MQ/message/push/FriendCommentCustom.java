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
import com.faceshow.modules.friend.dao.FriendRingCommentDao;
import com.faceshow.modules.friend.dao.FriendRingDao;
import com.faceshow.modules.friend.entity.FriendRingComment;
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
 * 朋友圈评论推送通知Custom
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
@Component
public class FriendCommentCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private FriendRingCommentDao friendRingCommentDao;

    @Autowired
    private FriendRingDao friendRingDao;

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        // 动态评论
        pushFriendComment();
        // 动态回复评论
        pushFriendCommentReply();
    }

    private void pushFriendCommentReply() {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_friendCommentReplyPush.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_friendCommentReplyPush.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 朋友圈评论回复推送消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                        // 根据朋友圈动态评论回复id获取动态发布者
                        FriendRingComment friendRingComment = friendRingCommentDao.selectByPrimaryKey(pushBeanUtils.getInfoId());

                        // 获取推送者昵称
                        String nickName = userInfoDao.selectNick_name(friendRingComment.getUserId());

                        // 获得推送接受者用户id
                        Integer userId = friendRingCommentDao.selectUserIdByCommentId(friendRingComment.getParentCommentId());

                        // 推送内容
                        String format = MessageFormat.format(JpushTemplet.COMMENT_REPLY, nickName);

                        // 把站内信存储到数据库
                        userInfoDao.insertMessage_push_info(JpushTemplet.FRIEND_COMMENT_REPLY_TYPE, friendRingComment.getUserId(), userId, new Date(), format);

                        // 查询通知开关状态是否开启
                        Map<String, Object> account = messagePushSummaryUtlis.SelectMessage_push_user_set(userId);
                        if (account.get("is_reply_comment").toString().equalsIgnoreCase("1")) {
                            // 推送设置参数
                            Map<String, String> JpushPrameter = new HashMap<>(0);
                            JpushPrameter.put("type_id", JpushTemplet.FRIEND_COMMENT_REPLY_TYPE.toString());
                            JpushPrameter.put("friendId", friendRingComment.getFriendId().toString());

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

    private void pushFriendComment() {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_friendCommentPush.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_friendCommentPush.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 朋友圈评论推送消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                        // 根据朋友圈动态评论id获取动态发布者
                        FriendRingComment friendRingComment = friendRingCommentDao.selectByPrimaryKey(pushBeanUtils.getInfoId());

                        // 获取推送者昵称
                        String nickName = userInfoDao.selectNick_name(friendRingComment.getUserId());

                        // 获得推送接受者用户id
                        Integer userId = friendRingDao.selectUserIdByFriendId(friendRingComment.getFriendId());

                        // 推送内容
                        String format = MessageFormat.format(JpushTemplet.COMMENT, nickName, friendRingComment.getContent());

                        // 把站内信存储到数据库
                        userInfoDao.insertMessage_push_info(JpushTemplet.FRIEND_COMMENT_TYPE, friendRingComment.getUserId(), userId, new Date(), format);

                        // 查询通知开关状态是否开启
                        Map<String, Object> account = messagePushSummaryUtlis.SelectMessage_push_user_set(userId);
                        if (account.get("is_comment").toString().equalsIgnoreCase("1")) {
                            // 推送设置参数
                            Map<String, String> JpushPrameter = new HashMap<>(0);
                            JpushPrameter.put("type_id", JpushTemplet.FRIEND_COMMENT_TYPE.toString());
                            JpushPrameter.put("friendId", friendRingComment.getFriendId().toString());

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
