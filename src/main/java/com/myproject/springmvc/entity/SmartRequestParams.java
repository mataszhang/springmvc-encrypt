package com.myproject.springmvc.entity;

public class SmartRequestParams<T> {

	/**
	 * 请求的接口版本
	 */
	private String apiVersion;
	
	/**
	 * 客户端操作系统类型，如IOS、Android
	 */
	private String clientOS;
	
	/**
	 * 客户端版本
	 */
	private String clientVersion;
	
	/**
	 * MD5加密后
	 */
	private String signKey;
	
	/**
	 * App下载渠道
	 */
	private String channel;
	
	private T requestBody;

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getClientOS() {
		return clientOS;
	}

	public void setClientOS(String clientOS) {
		this.clientOS = clientOS;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public T getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(T requestBody) {
		this.requestBody = requestBody;
	}
	
}
