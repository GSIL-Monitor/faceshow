package com.faceshow;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.Message;
import com.faceshow.common.MQ.ProductMQ;
import com.faceshow.config.RocketMQConfig;
import com.faceshow.modules.gift.entity.GiftInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FaceshowApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(FaceshowApplication.class, args);
//redis


		 //database();

	}
//yibu
	void database(){

		//操作数据库
//		GiftInfo giftInfo = new GiftInfo();
//		giftInfo.setGiftId(3232);
//		giftInfo.setGname("5145456");
//		Message msg=new Message(RocketMQConfig.TOPPIC_REWARD, JSON.toJSONString(giftInfo).getBytes());
//		msg.setKeys(giftInfo.getGiftId().toString());
//		ProductMQ.sendMessage(msg);


	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FaceshowApplication.class);
	}
}
