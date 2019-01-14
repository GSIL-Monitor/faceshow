package com.faceshow.common.utils.alipay;

import java.util.Random;

public class OrderCodeTool {
	

	/**
	 * 
	 * @Description: 生成订单标号
	 * @return   
	 * @return String  
	 * @author wsw
	 * @date 2017-8-18 上午9:54:11
	 */
	public static String getOrderCode() {
		//Calendar  calendar = Calendar.getInstance();
		//int year = calendar.get(Calendar.YEAR);
		//int month = calendar.get(Calendar.MONTH)+1;
		//int day = calendar.get(Calendar.DAY_OF_MONTH);
		Random random = new Random();
		return "BZ6" + random.nextInt(10000) + System.currentTimeMillis();
	}

}