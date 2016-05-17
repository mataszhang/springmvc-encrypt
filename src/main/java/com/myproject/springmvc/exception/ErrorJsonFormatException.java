package com.myproject.springmvc.exception;

/**
 * <b>
 *	前端Body体中，参数不是正确的json格式
 * </b>
 * 
 * @author Rogers
 * @date 2016-5-10 上午10:12:12
 *
 */
public class ErrorJsonFormatException extends RuntimeException {

    private String code;
    
    private String msg;
    
    public ErrorJsonFormatException(Throwable t) {
    	super(t);
    }
    
    public ErrorJsonFormatException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ErrorJsonFormatException(String code, String message, Throwable t) {
    	this(t);
    	this.code = code;
    	this.msg = message;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
