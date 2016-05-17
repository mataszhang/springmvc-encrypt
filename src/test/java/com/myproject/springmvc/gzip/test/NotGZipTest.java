package com.myproject.springmvc.gzip.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.myproject.springmvc.utils.HttpClientUtils;

public class NotGZipTest {

	/**
	 * 这是普通的请求，请求header是：
	 * 

	    Status Code: 200 OK
	    Content-Type: text/html; charset=utf-8
	    Server: Jetty(6.1.26)
	    Transfer-Encoding: chunked
	  
	  	服务端对response没有进行gzip压缩

	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String url = "http://localhost:8080/springmvc-encrypt/api/v1?apiVersion=1.0&clientOS=android&clientVersion=4.7&signKey=123123&channel=free";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key1", "qingshagn");
		params.put("key2", 12);
		String jsonParams = JSONUtils.toJSONString(params);
		
		String jsonResult = "";
		jsonResult = HttpClientUtils.postRequest(url, jsonParams);
		
		System.out.println(jsonResult);
		
	}
}
