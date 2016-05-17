package com.myproject.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.springmvc.exception.LackArgumentsException;
import com.myproject.springmvc.response.JsonResponse;

@Controller
@RequestMapping("/exception")
public class ExceptionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 方式1：拦截指定异常
	 * 和dispatcher-servlet.xml文件中写出的异常相对应
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/lackArgumentsException")
	@ResponseBody
	public JsonResponse lackArgumentsException(HttpServletRequest request) {
		JsonResponse response = JsonResponse.buildInstance();
		LackArgumentsException ex = (LackArgumentsException) request.getAttribute("lackArgumentsException");
		response.setCode(ex.getCode());
		response.setMsg(ex.getMsg());
		return response;
	}
	
}
