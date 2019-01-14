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
import com.faceshow.modules.user.vo.MessagePushSummary;
import com.faceshow.modules.video.dao.VideoInfoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author: YS
 * @Date:2018/4/26 9:05
 * @param:
 * @explain：添加好友的推送
 * @return:
 */
@Component
public class FriendAddPushCustom implements CommandLineRunner {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserInfoDao userInfoDao;


    @Autowired
    private MessagePushSummaryUtlis messagePushSummaryUtlis;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = RocketMQConfig.initConsumer(MqToppic.user_attention.toString());

        try {
            // 设置topic和标签
            consumer.subscribe(MqToppic.user_attention.toString(), null);
            consumer.setConsumeMessageBatchMaxSize(100);
            // 程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    logger.info("好友添加共接收到" + list.size()+"条");
                    int i=0;
                    for (MessageExt msg : list) {
                        i+=i;
                        logger.info("好友添加信息已经处理" + i+"条");
                        String logJsonStr = new String(msg.getBody());
                        PushBeanUtils pushBeanUtils = JSON.parseObject(logJsonStr, PushBeanUtils.class);

                /* 推送开始
                  * 1.先插入站内信一条记录 message_push_summary这个表
                  * 2.查询该用户的类型开关是否是打开状态 如果是关闭状态不再推送 结束
                  * 3.如果该状态的开关是打开状态则开始推送 推送IOS 和推送安卓 填入参数
                  * 4.其中Map 是额外带的参数 该参数根据跳转页面需要的接口参数 填入相应的参数
                  *
                  */
                        Map<String, String> JpushPrameter = new HashMap<String, String>();

                        String nick_name = userInfoDao.selectNick_name(pushBeanUtils.getUserId());//查询昵称

                        Set<String> tagsToAdd = new HashSet<>();//增加标签
                        tagsToAdd.add(pushBeanUtils.getAccount()+"");//增加标签

                        //给这个用户打上标签 标签就是关注人的ID 以后这个人开播的时候就按照ID推送
                        JpushUtils.updateDeviceTagAlias(null,null,tagsToAdd,null);//String registrationId, String alias, Set<String> tagsToAdd, Set<String> tagsToRemove
                        //添加到站内信
                        userInfoDao.insertMessage_push_info(pushBeanUtils.getInfoId(),pushBeanUtils.getUserId(),pushBeanUtils.getAccount(),new Date(), MessageFormat.format(JpushTemplet.ATTENTION,nick_name));//(@Param("type_id") int type_id,@Param("user_id") int user_id,@Param("account_id") int account_id,@Param("create_time") Date create_time);

                        Map<String, Object> account = messagePushSummaryUtlis.SelectMessage_push_user_set(pushBeanUtils.getAccount());//查詢通知開關
                        if (account.get("is_fance").toString().equalsIgnoreCase("1")){//如果通知是打开的 就发送通知
                            JpushPrameter.put("userId",pushBeanUtils.getAccount()+"");//用户ID 请注意：如果你是点击自己的个人主页 那么这个字段传递自己的ID 如果你点击的是别人的主页那么这个字段传递查看人的ID https://www.easyapi.com/api/view/93328?documentId=12042&themeId=&categoryId=21905
                            JpushPrameter.put("account",pushBeanUtils.getUserId()+"");
                            JpushPrameter.put("type_id",JpushTemplet.ATTENTION_TYPE.toString());
                            JpushUtils.buildIOSParameter(MessageFormat.format(JpushTemplet.ATTENTION,nick_name),JpushPrameter,pushBeanUtils.getAccount()+"");// (String alert, Map<String, String> map, String... alias)
                            JpushUtils.buildAndroidParameter(MessageFormat.format(JpushTemplet.ATTENTION,nick_name), "",JpushPrameter,pushBeanUtils.getAccount()+"");//String alert, String title, Map<String, String> map, String... alias
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
