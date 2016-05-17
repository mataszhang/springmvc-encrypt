package com.myproject.springmvc.resolver;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.myproject.springmvc.annotation.SmartRequestParam;
import com.myproject.springmvc.constants.ServiceResponseCode;
import com.myproject.springmvc.entity.SmartRequestParams;
import com.myproject.springmvc.exception.ErrorJsonFormatException;
import com.myproject.springmvc.utils.JsonBinder;

public class RequestParamResolver implements HandlerMethodArgumentResolver {

	private static final JsonBinder JSON_BINDER = JsonBinder.buildNormalBinder();
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(SmartRequestParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		SmartRequestParam attribute = parameter.getParameterAnnotation(SmartRequestParam.class);
		Object requestBody = webRequest.getAttribute("requestBody", RequestAttributes.SCOPE_REQUEST);
		Class<?> paramType = attribute.type();
		
		SmartRequestParams<Object> requestedParams = new SmartRequestParams<Object>();
		requestedParams.setApiVersion(webRequest.getParameter("apiVersion"));
		requestedParams.setClientOS(webRequest.getParameter("clientOS"));
		requestedParams.setClientVersion(webRequest.getParameter("clientVersion"));
		requestedParams.setSignKey(webRequest.getParameter("signKey"));
		requestedParams.setChannel(webRequest.getParameter("channel"));
		
		if ( null != requestBody) {
			try {
				Object requestBodyObj = JSON_BINDER.fromJson(requestBody.toString(), paramType);
				requestedParams.setRequestBody(requestBodyObj);				
			} catch (Exception e) {
				// json格式错误
				throw new ErrorJsonFormatException(ServiceResponseCode.FAILURE.getCode(), "json格式不正确");
			}
		}
		return requestedParams;
	}

    public void setObjectMapper(ObjectMapper objectMapper) {
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON_BINDER.setMapper(objectMapper);
    }
    
}
