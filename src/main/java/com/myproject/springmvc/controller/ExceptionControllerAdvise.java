package com.myproject.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.myproject.springmvc.exception.ErrorJsonFormatException;
import com.myproject.springmvc.response.JsonResponse;

@ControllerAdvice
public class ExceptionControllerAdvise {

	/**
	 * 方式2：
	 *  @ControllerAdvice 和 @ExceptionHandler 配合使用，拦截全局异常
	 * 
	 * @param e
	 * @param req
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ErrorJsonFormatException.class)
	@ResponseBody
	public JsonResponse ErrorJsonFormatException(final Exception e, final WebRequest req, final HttpServletRequest request) {
		JsonResponse response = JsonResponse.buildInstance();
		
		ErrorJsonFormatException ex = (ErrorJsonFormatException) e;
		response.setCode(ex.getCode());
		response.setMsg(ex.getMsg());
		return response;
	}
	
}
