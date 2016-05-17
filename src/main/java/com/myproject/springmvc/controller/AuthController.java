package com.myproject.springmvc.controller;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.springmvc.bootstrap.Bootstrap;
import com.myproject.springmvc.constants.ServiceResponseCode;
import com.myproject.springmvc.response.JsonResponse;
import com.myproject.springmvc.rsa.RSAHelper;
import com.myproject.springmvc.utils.StringUtils;

@Controller
@RequestMapping(value="/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Bootstrap bootstrap;
	
	@RequestMapping("/login")
	public String toLoin(HttpServletRequest request) {
		String publicKeyStr = bootstrap.getRsaPublicKeyStr();
		
		RSAPublicKey publicKey = bootstrap.getRsaPublicKey();
		String publicKeyExponent = publicKey.getPublicExponent().toString(16);
		String publicKeyModules = publicKey.getModulus().toString(16);
		
		request.setAttribute("publicKeyStr", publicKeyStr);
		request.setAttribute("publicKeyExponent", publicKeyExponent);
		request.setAttribute("publicKeyModules", publicKeyModules);
		return "login";
	}
	
	// TODO 公钥加密后的密文传递不过来
	@RequestMapping(value="/authCheck")
	@ResponseBody
	public JsonResponse authCheck(String adminAccount, String adminPass, String password) {
		JsonResponse response = JsonResponse.buildInstance();
		String privatKey = bootstrap.getRsaPrivageKeyStr();
		RSAHelper rsa = bootstrap.getRsa();
		try {
			adminPass = rsa.decryptByPrivateKey(adminPass, privatKey);
			logger.info("adminPass={}", adminPass);
			
		} catch (Exception e) {
			response.setCode(ServiceResponseCode.FAILURE.getCode());
			response.setMsg("参数错误");
		}
		return response;
	}
	
}
