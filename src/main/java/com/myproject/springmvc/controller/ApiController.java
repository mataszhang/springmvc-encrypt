package com.myproject.springmvc.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.springmvc.annotation.SmartRequestParam;
import com.myproject.springmvc.constants.ServiceResponseCode;
import com.myproject.springmvc.entity.SmartRequestParams;
import com.myproject.springmvc.response.JsonResponse;

@Controller
@RequestMapping(value = "/api")
public class ApiController {

	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@RequestMapping("/v1")
	@ResponseBody
	public JsonResponse v1(@SmartRequestParam(type=HashMap.class) SmartRequestParams<HashMap<String, Object>> requestParams) {
		JsonResponse response = JsonResponse.buildInstance();
		
		HashMap<String, Object> params = requestParams.getRequestBody();
		logger.info("params={}", params);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", "Shell");
		resultMap.put("age", 28);
		resultMap.put("high", "181.00");
		resultMap.put("isMarry", Boolean.FALSE);
		
		response.setCode(ServiceResponseCode.SUCCESS.getCode());
		response.setMsg(ServiceResponseCode.SUCCESS.getMsg());
		response.setResult(resultMap);
		
		return response;
	}
	
}
