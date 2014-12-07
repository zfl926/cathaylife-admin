package org.httpclient;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientManager implements AutoCloseable {
	
	private static final HttpClientManager _INSTANCE = new HttpClientManager();
	private static final String HTTP_ERR_IO = "Http client IO exception";
	private static final String HTTP_ERR_STATUS = "Http client invalidate status"; 
	
	private CloseableHttpClient httpClient = null;

	private static class IdleConnectionMonitorRunner implements Runnable {

		private HttpClientConnectionManager connMgr = null;
		private volatile boolean shutdown = false;
		
		
		public IdleConnectionMonitorRunner(HttpClientConnectionManager connMgr){
			this.connMgr = connMgr;
			
		}
		
		public void run() {
			try {
				while ( !shutdown ){
						wait(5000);
						// close expired connections
						connMgr.closeExpiredConnections();
						// close idle time is bigger than 300 seconds
						connMgr.closeIdleConnections(300, TimeUnit.SECONDS);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void shutdown(){
			this.shutdown = true;
		}
		
	}
	
	/* register all the component(http, https) for http client */
	private void HttpManager(){
		init();
	}
	
	private void init() {
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		SSLConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> httpRegister = RegistryBuilder.<ConnectionSocketFactory>create()
		        .register("http", plainsf)
		        .register("https", sslsf)
		        .build();
		
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(httpRegister);
		httpClient = HttpClients.custom()
					.setConnectionManager(cm)
					.build();
		// run the monitor runner
		new Thread(new IdleConnectionMonitorRunner(cm)).start();
	}
	
	
	public static HttpClientManager getInstatnce(){
		return _INSTANCE;
	}
	
	/**
	 * @param url
	 * @param params
	 * @return
	 */
	public String get(String url, Map<String, String> params){
		HttpGet httpGet = new HttpGet(url);
		try(CloseableHttpResponse resp = httpClient.execute(httpGet)) {
			if ( resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
				return EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
			} else {
				throw new HttpException(HTTP_ERR_STATUS + " " + resp.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			throw new HttpException(e, HTTP_ERR_IO);
		}
		
	}

	/**
	 *  close the client
	 */
	public void close() throws Exception {
		httpClient.close();
	}

}
