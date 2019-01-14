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
import com.faceshow.common.utils.R;
import com.faceshow.common.utils.push.Jpush.JpushTemplet;
import com.faceshow.common.utils.push.Jpush.JpushUtils;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.user.dao.UserInfoDao;
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.user.vo.UserAttentionSetRowVo;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 发布视频推送至粉丝Custom
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/24 16:55
 * -------------------------------------------------------------- <br>
 */
@Component
public class VideoPushManyCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private VideoInfoDao videoInfoDao;

    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.top_videoPushMany.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.top_videoPushMany.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("custom 视频发布推送至粉丝消费 条数:" + list.size());

                    for (MessageExt msg : list) {
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                        // 获取视频发布人的昵称
                        String nickName = userInfoDao.selectNick_name(pushBeanUtils.getUserId());

                        // 根据视频id查询视频使用音乐的音乐发起人
                        Integer musicUserId = videoInfoDao.findMusicUserIdByVideoId(pushBeanUtils.getInfoId());
                        if (musicUserId != null && musicUserId > 0) {
                            // 使用了别人的原声音乐发布了视频, 发送推送消息到音乐所属用户
                            // 生成推送消息
                            String format = MessageFormat.format(JpushTemplet.VIDEO_MUSIC, nickName);

                            // 把站内信存储到数据库
                            userInfoDao.insertMessage_push_info(JpushTemplet.VIDEO_MUSIC_TYPE, pushBeanUtils.getUserId(), musicUserId, new Date(), format);

                            // 查询通知开关状态是否开启
                            Map<String, Object> account = messagePushSummaryUtlis.SelectMessage_push_user_set(musicUserId);
                            if (account.get("is_music").toString().equalsIgnoreCase("1")) {
                                // 推送设置参数
                                Map<String, String> JpushPrameter = new HashMap<>(0);
                                JpushPrameter.put("type_id", JpushTemplet.VIDEO_MUSIC_TYPE.toString());
                                JpushPrameter.put("videoId", pushBeanUtils.getInfoId().toString());

                                // 开始推送
                                JpushUtils.buildIOSParameter(format, JpushPrameter, musicUserId.toString());
                                JpushUtils.buildAndroidParameter(format, "", JpushPrameter, musicUserId.toString());
                            }
                        }

                        // 获取视频发布人所有的粉丝
                        List<UserAttentionSetRowVo> attentionSets = userInfoDao.findVideoAttentionSet(pushBeanUtils.getUserId());

                        // 判断是否拥有粉丝
                        if (attentionSets == null || attentionSets.size() < 1) {
                            continue;
                        }

                        // 将推送消息站内信保存到数据库, 使用消息队列
                        VideoPushSaveProduct.pushVideoSave(attentionSets);

                        // 获取所有接收通知的用户信息
                        attentionSets = attentionSets.stream().filter(attentionSet -> attentionSet.getVideoSet() == 1).collect(Collectors.toList());

                        // 获取出所有需要推送的别名
                        List<String> collect = attentionSets.stream().map(UserAttentionSetRowVo::getAccountId).collect(Collectors.toList());

                        // 推送设置参数
                        Map<String, String> JpushPrameter = new HashMap<>(0);
                        JpushPrameter.put("type_id", JpushTemplet.VIDEO_CREATE_TYPE.toString());
                        //用户ID 请注意：如果你是点击自己的个人主页 那么这个字段传递自己的ID 如果你点击的是别人的主页那么这个字段传递查看人的ID https://www.easyapi.com/api/view/93328?documentId=12042&themeId=&categoryId=21905
                        JpushPrameter.put("userId", pushBeanUtils.getUserId().toString());
                        JpushPrameter.put("videoId", pushBeanUtils.getInfoId().toString());

                        // 开始推送
                        List<String> aliases = new ArrayList<>(0);
                        int size = collect.size();
                        for (int i = 1; i <= size; i++) {
                            aliases.add(collect.get(i - 1));
                            if (i % 1000 == 0 || i == size) {
                                // 数量达到1000条开始一次推送, 或者到最后开始推送
                                JpushUtils.buildIOSParameter(MessageFormat.format(JpushTemplet.VIDEO_PUSH, nickName), JpushPrameter, aliases);
                                JpushUtils.buildAndroidParameter(MessageFormat.format(JpushTemplet.VIDEO_PUSH, nickName), "", JpushPrameter, aliases);
                                aliases = new ArrayList<>(0);
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
