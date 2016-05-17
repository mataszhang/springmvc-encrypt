package com.myproject.springmvc.response;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Foot implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long opt; // 服务器操作时间
	private String host; // 服务器hostName
	private static String LOCAL_HOST_NAME;
	
	public long getOpt() {
		opt = System.currentTimeMillis();
		return opt;
	}

	public String getHost() {
		host = getLocalHostName();
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取服务器name
	 * @return
	 */
	public static String getLocalHostName() {
		if (null == LOCAL_HOST_NAME || "".equals(LOCAL_HOST_NAME)) {
			try {
				InetAddress ia = InetAddress.getLocalHost();
				LOCAL_HOST_NAME = ia.getHostName();
			} catch (UnknownHostException e) {
				LOCAL_HOST_NAME = "localhost";
			}
		}
		return LOCAL_HOST_NAME;
	}
	
}
