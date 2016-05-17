package com.myproject.springmvc.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.springmvc.bootstrap.Bootstrap;

@Controller
@RequestMapping(value="/security")
public class SecurityController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Bootstrap bootstrap;
	
	@RequestMapping(value="/getPubKey")
	@ResponseBody
	public Map<String, Object> getPubKey() {
		String publicKeyStr = bootstrap.getRsaPublicKeyStr();
		RSAPublicKey publicKey = bootstrap.getRsaPublicKey();
		
		String publicKeyExponent = publicKey.getPublicExponent().toString(16);
		String publicKeyModules = publicKey.getModulus().toString(16);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("publicKeyStr", publicKeyStr);
		resultMap.put("publicKeyExponent", publicKeyExponent);
		resultMap.put("publicKeyModules", publicKeyModules);
		return resultMap;
	}
	
}
