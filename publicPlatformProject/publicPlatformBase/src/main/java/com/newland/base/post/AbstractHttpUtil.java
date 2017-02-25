package com.newland.base.post;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;




public abstract class AbstractHttpUtil {
	
	//取连接池
	//public   abstract  DefaultHttpClient getPoolHttpClient();
	
	/**
	 * 自定义私有类：绕开HTTPS证书校验
	 */
	 public static class EasyTrustManager implements X509TrustManager {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	}
	
	
	public static void setProxy(HttpClient hc) {
		String proxy = System.getProperty("Proxy");
		try{
			if (StringUtils.isNotBlank(proxy)) {
				/**
				 * 使用代理链接
				 */
				int index = proxy.indexOf(":");
				int port = 80;
				String ip = null;
				if (index == -1){
					ip = proxy.substring(0, proxy.length() - 1);
				}else{
					ip = proxy.substring(0, index);
					port = Integer.parseInt(proxy.substring(index + 1));
				}
				HttpHost Proxy = new HttpHost(ip, port);
				hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, Proxy);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setSSLParam(HttpClient httpclient)
			throws NoSuchAlgorithmException, KeyManagementException {
		Scheme http = new Scheme("http", 80,
				PlainSocketFactory.getSocketFactory());
		httpclient.getConnectionManager().getSchemeRegistry().register(http);
		// HTTPS应绕开证书验证
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] { new EasyTrustManager() },
				null);
		SSLSocketFactory factory = new SSLSocketFactory(context,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme https = new Scheme("https", 443, factory);
		httpclient.getConnectionManager().getSchemeRegistry().register(https);
	}

}
