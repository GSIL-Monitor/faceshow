package com.faceshow.common.MQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.faceshow.config.RocketMQConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * 消息队列产生类
 */
public class ProductMQ {

    private static final Log logger = LogFactory.getLog(ProductMQ.class);

    /**
     * 发送消息队列
     * @param topic    话题
     * @param message  消息内容
     * @return
     */
    public static ResultDto sendMessage(MqToppic topic, String message){
        if (message == null) {
            ResultDto resultDto = new ResultDto();
            resultDto.setResult(false);
            resultDto.setErrMsg("消息为空，请检查参数！");
            return resultDto;
        }
        Message msg = new Message(topic.toString(),// topic
                null,// tag
                null,// key
                message.getBytes());// body
        return sendMessage(msg);
    }
    /**
     * 发送消息队列
     * @param topic    话题
     * @param tag      标签
     * @param keys     关键词
     * @param message  消息内容
     * @return
     */
    public static ResultDto sendMessage(MqToppic topic, String tag, String keys, String message){
        if (message == null) {
            ResultDto resultDto = new ResultDto();
            resultDto.setResult(false);
            resultDto.setErrMsg("消息为空，请检查参数！");
            return resultDto;
        }
        Message msg = new Message(topic.toString(),// topic
                tag,// tag
                keys,// key
                message.getBytes());// body
        return sendMessage(msg);
    }
    /**
     * 发送消息队列
     * @param msg    消息
     * @return
     */
    public static ResultDto sendMessage(Message msg) {
        SendResult sendResult = null;
        ResultDto resultDto = new ResultDto();
        if (msg == null) {
            resultDto.setResult(false);
            resultDto.setErrMsg("消息为空，请检查参数！");
            return resultDto;
        }
        try {
            sendResult = RocketMQConfig.init().send(msg);
        } catch (MQClientException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现MQClientException异常：" + e.getMessage());
            logger.error("发送异常：" + e.getMessage() + JSON.toJSONString(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            logger.error("异常堆栈信息：" + exceptionMessage);
        } catch (RemotingException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现RemotingException异常：" + e.getMessage());
            logger.error("发送异常：" + e.getMessage() + JSON.toJSONString(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            logger.error("异常堆栈信息：" + exceptionMessage);
        } catch (MQBrokerException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现MQBrokerException异常：" + e.getMessage());
            logger.error("发送异常：" + e.getMessage() + JSON.toJSONString(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            logger.error("异常堆栈信息：" + exceptionMessage);
        } catch (InterruptedException e) {
            resultDto.setResult(false);
            resultDto.setErrMsg("发送出现InterruptedException异常：" + e.getMessage());
            logger.error("发送异常：" + e.getMessage() + JSON.toJSONString(msg));
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionMessage = sw.toString();
            logger.error("异常堆栈信息：" + exceptionMessage);
        }
        logger.info(sendResult);
        if (sendResult == null) {
            resultDto.setResult(false);
            logger.info("发送失败：" + JSON.toJSONString(msg));
            return resultDto;
        }
        if (sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
            resultDto.setResult(true);
            logger.info("发送成功：" + JSON.toJSONString(msg));
        }
        if (sendResult.getSendStatus().equals(SendStatus.FLUSH_DISK_TIMEOUT)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("发送成功，到那时服务器未刷盘！");
            logger.info("消息収送成功，但是服务器刷盘超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JSON.toJSONString(msg));
        }
        //暂时MQ没有集群，所以不会出现这种返回结果
        if (sendResult.getSendStatus().equals(SendStatus.FLUSH_SLAVE_TIMEOUT)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("发送成功，到那时服务器未同步slave！");
            logger.info("消息収送成功，但是服务器同步到 Slave 时超时，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JSON.toJSONString(msg));
        }
        if (sendResult.getSendStatus().equals(SendStatus.SLAVE_NOT_AVAILABLE)) {
            resultDto.setResult(true);
            resultDto.setErrMsg("消息发送成功，但是slave不可用");
            logger.info("消息収送成功，但是此时 slave 不可用，消息已经迕入服务器队列，只有此时服务器宕机，消息才会丢失" + JSON.toJSONString(msg));
        }
        return resultDto;
    }
    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
//            Message msg = new Message(MqToppic.top_reward.toString(),// topic
//                    "test",// tag
//                    "110",// key
//                    ("asdf" + i).getBytes());// body
//            ResultDto resultDto = sendMessage(msg);
            Message msg2 = new Message(MqToppic.top_pay.toString(),// topic
                    "ha",// tag
                    "11011",// key
                    ("top_pay---Message" + i).getBytes());// body

            //messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
//            msg2.setDelayTimeLevel(1);

            ResultDto resultDto2 = sendMessage(msg2);
            System.out.println(resultDto2.getErrCode());
        }
    }
}
