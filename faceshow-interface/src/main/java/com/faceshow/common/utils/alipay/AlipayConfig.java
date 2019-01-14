package com.faceshow.common.utils.alipay;

import com.faceshow.common.utils.weixinpay.PropertyUtil;

public class AlipayConfig {
	
		//ALI_GATEWAY
	    public static String ALI_GATEWAY = "https://openapi.alipay.com/gateway.do";

	    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	    public static String PID = "2088821844332322";

	    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	    public static String PRIVATE_KEY = PropertyUtil.getInstance().getProperty("ali.PRIVATE_KEY");

	    //MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg/eUBvOB1Yu1eUb1D0l+E/rQ3ECF+nz3VqizRBdkAfg86R66dXMnRI4WzmWwOt53uw/t6IjK6XFpPhyqV0MoS+SxUi6nKi3bjAP1n7ZnUdR2dI4nFLC4K43Ex0YnNBNFcT26oiKoTT7VFcVZ+8obvwSArTjb/mVtu6sJh8nPCHPUs0ItC4B1XVwJlWOrFqro7Akapw6jmICfZycuAlMrKb1uX1aBmrV6efuMMJ1t6gWETjRibKeltyT2HSA37kIW+OOZVf/9RKtIfYzU6FhKVTy45mCowW/PIQsPUZVBcXn4hB4xhRMGNqlEd9d6cVEWAuvAo9SkXaTAj7xkQCR8PwIDAQAB
	    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner//
	    public static String ALIPAY_PUBLIC_KEY = PropertyUtil.getInstance().getProperty("ali.PUBLIC_KEY");

	    // 签名方式
	    public static String SIGN_TYPE = "RSA2";

	    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	    public static String LOG_PATH ="E:/apache-tomcat-7.0.69/logs/banzhi/";

	    // 字符编码格式 目前支持 gbk 或 utf-8
	    public static String INPUT_CHARSET = "utf-8";

	    // 接收通知的接口名
	    public static String notifyurl = PropertyUtil.getInstance().getProperty("ali.notifyurl");
	    //public static String service = "mobile.securitypay.pay";

	    //APPID
	    public static String APP_ID=PropertyUtil.getInstance().getProperty("ali.APP_ID");

	}
