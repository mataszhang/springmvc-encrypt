package com.myproject.springmvc.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.myproject.springmvc.bootstrap.Bootstrap;
import com.myproject.springmvc.constants.ServiceResponseCode;
import com.myproject.springmvc.exception.LackArgumentsException;

public class CheckRequestParamInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Bootstrap bootstrap;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String requestBody = getPostData(request);
		
		// 解析前端传递报文头，如果header中有encrypt:true，则表示rsa加密过了,通过私钥来解密
		String isEncrypted = request.getHeader("encrypt");
		if (StringUtils.isNotBlank(isEncrypted)) {
			if (isEncrypted.equals("true")) {
				String rsaPrivateKey = bootstrap.getRsaPrivageKeyStr();
				requestBody = bootstrap.getRsa().decryptByPrivateKey(requestBody, rsaPrivateKey);
			}
		}
		
		String apiVersion = request.getParameter("apiVersion");
		String clientOS = request.getParameter("clientOS");
		String clientVersion = request.getParameter("clientVersion");
		String signKey = request.getParameter("signKey");
		String channel = request.getParameter("channel");
		
		if (StringUtils.isBlank(requestBody)) {
			LackArgumentsException ex = new LackArgumentsException(ServiceResponseCode.FAILURE.getCode());
			ex.setMsg("body体无任何参数");
			throw ex;
		}
		if (!checkIllegalParam(apiVersion, clientOS, clientVersion, channel, signKey, requestBody)) {
//			return false;
			LackArgumentsException ex = new LackArgumentsException(ServiceResponseCode.FAILURE.getCode());
			ex.setMsg("请求URL缺少参数");
			throw ex;
		}
		request.setAttribute("requestBody", requestBody);
		return true;
	}

	/**
	 * 获取http post请求的body体
	 * 
	 * @param request
	 * @return
	 */
	private String getPostData(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(
										new InputStreamReader(
												request.getInputStream()));
			String s = "";
			while ( (s=br.readLine()) != null ) {
				buffer.append(s);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return buffer.toString();
	}
	
	/**
	 * 检查参数
	 * 
	 * @param apiVersion
	 * @param clientOS
	 * @param clientVersion
	 * @param channel
	 * @return
	 */
	private boolean checkIllegalParam(String apiVersion, String clientOS, String clientVersion, String channel,
			String signKey, String requestBody) {
		if (StringUtils.isBlank(apiVersion) 
				|| StringUtils.isBlank(clientOS)
				|| StringUtils.isBlank(clientVersion)
				|| StringUtils.isBlank(channel)) {
			return false;
		}
		return true;
	}
	
	
}
