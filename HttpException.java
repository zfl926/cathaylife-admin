package org.httpclient;

public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
