package com.myproject.springmvc.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.springmvc.all.encrypt.AESUtil;
import com.myproject.springmvc.bootstrap.Bootstrap;
import com.myproject.springmvc.utils.JsonUtil;
import com.myproject.springmvc.utils.PropertiesConfigUtils;

public class CustomMappingJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Bootstrap bootstrap;
	
	// 这个方法可以不用重写，为了是返回的数据加密
	// 参考：http://www.scienjus.com/custom-http-message-converter/
	/* (non-Javadoc)
	 * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)
	 */
	/*
	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		//使用fastxml jackson的ObjectMapper将Java对象转换成Json String
		ObjectMapper mapper = super.objectMapper;
		String json = mapper.writeValueAsString(object);
		
		logger.info("json=%s", json);
		
		// 返回的数据，统一用加密
		String aesKey = PropertiesConfigUtils.getProperty("aes", "aes.key");
		String encryptText = null;
		try {
			encryptText = AESUtil.encrypt(json, aesKey);
		} catch (Exception e) {
			throw new RuntimeException(String.format("返回的数据【%s】加密出错", json));
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("text", encryptText);
		String resultText = JsonUtil.toJson(resultMap);
		logger.info("result=%s", resultText);
		
		// 输出
		outputMessage.getBody().write(resultText.getBytes());
		
	}
	*/
}
