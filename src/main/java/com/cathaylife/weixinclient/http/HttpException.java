package com.cathaylife.weixinclient.http;

public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String HTTP_ERR_IO = "Http client IO exception";
	public static final String HTTP_ERR_STATUS = "Http client invalidate status"; 	
	
	
	private String errMsg;
	
	public HttpException(Throwable t, String errMsg){
		super(t);
		this.errMsg = errMsg;
	}
	
	
	public HttpException(String errMsg){
		this.errMsg = errMsg;
	}
	
	public String getErr(){
		return errMsg;
	} 	

}
