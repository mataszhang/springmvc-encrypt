package com.myproject.springmvc.response;


import org.apache.commons.lang.StringUtils;

import com.github.pagehelper.PageInfo;
import com.myproject.springmvc.constants.ErrorCodeEnum;
import com.myproject.springmvc.constants.ServiceResponseCode;

/**
 * @Description
 * <b>
 *	返回json封装类
 * </b>
 * 
 * @author Rogers
 * 
 * @date   2015年9月11日 下午3:01:21
 */
public class JsonResponse implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String msg;					//本次请求返回的信息
	private String code;				//本次请求返回的代码
	private String header;				//返回的header头
	private Object result;				//本次请求返回的结果集
	private PageInfo<?> pageInfo;		//分页
	private Foot foot;					//其他信息
	
	public static JsonResponse buildInstance() {
		return new JsonResponse();
	}
	
	public JsonResponse() {
		this.code = ServiceResponseCode.SUCCESS.getCode();
		this.msg = ServiceResponseCode.SUCCESS.getMsg();
	}
	
	public JsonResponse(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JsonResponse(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}


	public String getHeader() {
		return StringUtils.isEmpty(header) ? "" : header;
	}

	public void setHeader(String header) {
		this.header = header;
		
	}

	public PageInfo<?> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<?> pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Foot getFoot() {
		if (null == foot)
			foot = new Foot();
		return foot;
	}

	public void setFoot(Foot foot) {
		this.foot = foot;
	}

}
