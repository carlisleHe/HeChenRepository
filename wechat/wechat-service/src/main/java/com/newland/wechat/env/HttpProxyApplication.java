package com.newland.wechat.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;


public class HttpProxyApplication implements InitializingBean{
	

	private static Properties props;
	
	private static Properties getProps(){
		synchronized(HttpProxyApplication.class){
			if (props == null){
				InputStream in = HttpProxyApplication.class.getClassLoader().getResourceAsStream("runtime.properties");
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
	
	
	
	public static String getPyMaxTotal(){
		String pyMaxTotal = getProps().getProperty("py_maxTotal");
		if (StringUtils.isBlank(pyMaxTotal)){
			throw new RuntimeException("py_maxTotal未配置");
		}
		return pyMaxTotal;
	}
	
	
	public static String getPyMaxPerRoute(){
		String pyMaxPerRoute = getProps().getProperty("py_maxPerRoute");
		if (StringUtils.isBlank(pyMaxPerRoute)){
			throw new RuntimeException("py_maxPerRoute未配置");
		}
		return pyMaxPerRoute;
	}
	
	public static String getPyConnectionTimeout(){
		String pyConnectionTimeout = getProps().getProperty("py_connectionTimeout");
		if (StringUtils.isBlank(pyConnectionTimeout)){
			throw new RuntimeException("py_connectionTimeout未配置");
		}
		return pyConnectionTimeout;
	}
	
	public static String getPySoTimeout(){
		String pySoTimeout = getProps().getProperty("py_soTimeout");
		if (StringUtils.isBlank(pySoTimeout)){
			throw new RuntimeException("py_soTimeout未配置");
		}
		return pySoTimeout;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("py_maxTotal=" + HttpProxyApplication.getPyMaxTotal());
		System.err.println("py_maxPerRoute=" + HttpProxyApplication.getPyMaxPerRoute());
		System.err.println("py_soTimeout=" + HttpProxyApplication.getPySoTimeout());
		System.err.println("py_connectionTimeout=" + HttpProxyApplication.getPyConnectionTimeout());
		
	}


}
