package com.cathaylife.weixinclient;

public class WeiXinException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errMsg = null;
	
	
	public WeiXinException(){
		super();
	}
	
	public WeiXinException(String errMsg){
		super();
		this.errMsg = errMsg;
	}
	
}
