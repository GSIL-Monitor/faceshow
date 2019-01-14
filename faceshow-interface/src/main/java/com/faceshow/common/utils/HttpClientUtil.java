package com.faceshow.common.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	public static String doGet(String url, Map<String, String> param) {

		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();

		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);

			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	public static String doGet(String url) {
		return doGet(url, null);
	}

	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}

	public static String doPost(String url) {
		return doPost(url, null);
	}
	
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resultString;
	}

	public static void main(String[] args) {
	  Map <String,String>pram=new HashMap<String,String>();
	  pram.put("address","郑州市金水区");
	  pram.put("output","json");
	  String s=doGet("http://api.map.baidu.com/geocoder",pram);

		JSONObject  jasonObject = JSONObject.fromObject(s);
		Map map = (Map)jasonObject;

		System.out.println(map);

		String str =map.get("result").toString();


		JSONObject  jasonObject2 = JSONObject.fromObject(str);
		Map map2 = (Map)jasonObject2;
        String l =map2.get("location").toString();


		JSONObject  jasonObject3 = JSONObject.fromObject(l);
		Map map3 = (Map)jasonObject3;

		System.out.println(map3.get("lng"));

		System.out.println(map3.get("lat"));

		/*116.68071
		40.18633*/

	//	System.out.println(s);//该套API免费对外开放，无需申请ak。 不限制调用次数
/*分类 	未认证 	个人认证 	企业认证
日配额（次） 	2,000 	30,000 	300,000
分钟并发数（次/分钟） 	120 	3,000 	12,000 */
/*http://api.map.baidu.com/place/v2/search?query=海底捞&location=39.915,116.404&radius=5000000&output=json&ak=hLldpyiPrXtpafakMIDlSOX9VGlS2zji*/
//		Map <String,String>pram2=new HashMap<String,String>();
//		pram2.put("query","海底捞");
//		pram2.put("location","34.798278,113.698171");
//		pram2.put("radius","5000000");
//		pram2.put("output","json");
//		pram2.put("ak","hLldpyiPrXtpafakMIDlSOX9VGlS2zji");
//		//String s1=doGet("http://api.map.baidu.com/place/v2/search?query=海底捞&location=39.915,116.404&radius=5000000&output=json&ak=hLldpyiPrXtpafakMIDlSOX9VGlS2zji");
//		String s2=doGet("http://api.map.baidu.com/place/v2/search",pram2);
//		System.out.println(s2);
	}
}
