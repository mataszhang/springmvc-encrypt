package com.myproject.springmvc.gzip.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.druid.support.json.JSONUtils;
import com.myproject.springmvc.utils.HttpClientUtils;

public class GZipTest {

	/**
	 * 要求服务端对response进行GZip压缩的请求，请求header是：
	 * 

	    Status Code: 200 OK
	    Content-Encoding: gzip
	    Content-Length: 152
	    Content-Type: text/html; charset=utf-8
	    Server: Jetty(6.1.26)
	    
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
		jsonResult = HttpClientUtils.postRequestWithGZipResponse(url, jsonParams);
		
		System.out.println(jsonResult);
	}
}
