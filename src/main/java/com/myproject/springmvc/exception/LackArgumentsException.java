package com.myproject.springmvc.exception;

public class LackArgumentsException extends RuntimeException {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 963120315937306542L;

	/**
     * 异常代码，可用于在返回错误代码
     */
    private String code;
    
    private String msg;

    public LackArgumentsException() {
    	super();
    }

    public LackArgumentsException(Throwable e) {
    	super(e);
    }

	public LackArgumentsException(String msg, Throwable e) {
		super(e);
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public LackArgumentsException(String code) {
		super();
		this.code = code;
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
