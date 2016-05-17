package com.myproject.springmvc.bootstrap;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myproject.springmvc.rsa.RSAHelper;
import com.myproject.springmvc.utils.PropertiesConfigUtils;

@Service
public class Bootstrap {

	private final static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	
	private String rsaPublicKeyStr;
	
	private String rsaPrivageKeyStr;
	
	private RSAPublicKey rsaPublicKey;
	
	private RSAPrivateKey rsaPrivateKey;
	
	private RSAHelper rsa;
	
	@PreDestroy
	public void destroy() {
		
	}
	
	@PostConstruct
	public void init() {
		
		// 1.根据RSA种子，生成公钥私钥
		// 每次重启服务器，都会重新生成公钥私钥对
		String seedKey = PropertiesConfigUtils.getProperty("rsa", "seed");
		this.rsa = RSAHelper.LazyHolder.rsaHelper;
		try {
			rsa.generateKeyPair(seedKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		this.rsaPublicKeyStr = rsa.getPublicKeyStr();
		this.rsaPrivageKeyStr = rsa.getPrivateKeyStr();
		
		this.rsaPublicKey = rsa.getPublicKey();
		this.rsaPrivateKey = rsa.getPrivateKey();
		
		
	}

	public String getRsaPublicKeyStr() {
		return rsaPublicKeyStr;
	}

	public String getRsaPrivageKeyStr() {
		return rsaPrivageKeyStr;
	}

	public RSAHelper getRsa() {
		return rsa;
	}

	public RSAPublicKey getRsaPublicKey() {
		return rsaPublicKey;
	}

	public RSAPrivateKey getRsaPrivateKey() {
		return rsaPrivateKey;
	}
	
	
}
