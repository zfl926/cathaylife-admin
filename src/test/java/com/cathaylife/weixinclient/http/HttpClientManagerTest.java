package com.cathaylife.weixinclient.http;

import org.junit.Test;

public class HttpClientManagerTest {
	
	private static final HttpClientManager manager = HttpClientManager.getInstatnce();
	private static final String TOKEN_URL = 
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0d88b07d309933d8&secret=59ea320516c051166889ea4a98407786";
	
	@Test
	public void testGetWeixinAppAccessToken(){
		String response = manager.get(TOKEN_URL, null);
		System.out.println(response);
	}
	
}
