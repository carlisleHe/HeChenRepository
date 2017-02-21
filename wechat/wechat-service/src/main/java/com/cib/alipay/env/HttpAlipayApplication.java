package com.cib.alipay.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;


public class HttpAlipayApplication implements InitializingBean{
	

	private static Properties props;
	
	private static Properties getProps(){
		synchronized(HttpAlipayApplication.class){
			if (props == null){
				InputStream in = HttpAlipayApplication.class.getClassLoader().getResourceAsStream("runtime.properties");
				props = new Properties();
				try {
					props.load(in);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return props;
	}
	
	
	
	public static String getWxMaxTotal(){
		String wxMaxTotal = getProps().getProperty("wx_maxTotal");
		if (StringUtils.isBlank(wxMaxTotal)){
			throw new RuntimeException("wx_maxTotal未配置");
		}
		return wxMaxTotal;
	}
	
	
	public static String getWxMaxPerRoute(){
		String wxMaxPerRoute = getProps().getProperty("wx_maxPerRoute");
		if (StringUtils.isBlank(wxMaxPerRoute)){
			throw new RuntimeException("wx_maxPerRoute未配置");
		}
		return wxMaxPerRoute;
	}
	
	public static String getWxConnectionTimeout(){
		String wxConnectionTimeout = getProps().getProperty("wx_connectionTimeout");
		if (StringUtils.isBlank(wxConnectionTimeout)){
			throw new RuntimeException("wx_connectionTimeout未配置");
		}
		return wxConnectionTimeout;
	}
	
	public static String getWxSoTimeout(){
		String wxSoTimeout = getProps().getProperty("wx_soTimeout");
		if (StringUtils.isBlank(wxSoTimeout)){
			throw new RuntimeException("wx_soTimeout未配置");
		}
		return wxSoTimeout;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("wx_maxTotal=" + HttpAlipayApplication.getWxMaxTotal());
		System.err.println("wx_maxPerRoute=" + HttpAlipayApplication.getWxMaxPerRoute());
		System.err.println("wx_soTimeout=" + HttpAlipayApplication.getWxSoTimeout());
		System.err.println("wx_connectionTimeout=" + HttpAlipayApplication.getWxConnectionTimeout());
		
	}


}
