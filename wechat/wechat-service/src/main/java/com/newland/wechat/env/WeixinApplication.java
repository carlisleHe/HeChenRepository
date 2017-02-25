package com.newland.wechat.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


@Service("weixinapplication")
public class WeixinApplication implements InitializingBean{
	/**
	 * 性能测试模式
	 */
	public static final String WEIXIN_PERFORMANCE_TEST_MODE = "PERFORMANCE_MODE";
	private static Properties props;
	
	private static Properties getProps(){
		synchronized(WeixinApplication.class){
			if (props == null){
				InputStream in = WeixinApplication.class.getClassLoader().getResourceAsStream("runtime.properties");
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
	
	
	public static String getAppId(){
		String appid = getProps().getProperty("appid");
		if (StringUtils.isBlank(appid)){
			throw new RuntimeException("appid未配置");
		}
		return appid;
	}
	
	public static String getAppsecret(){
		String Appsecret = getProps().getProperty("appsecret");
		if (StringUtils.isBlank(Appsecret)){
			throw new RuntimeException("appsecret未配置");
		}
		return Appsecret;
	}
	
	public static String getToken(){
		String apptoken = getProps().getProperty("apptoken");
		if (StringUtils.isBlank(apptoken)){
			throw new RuntimeException("apptoken未配置");
		}
		return apptoken;
	}
	
	public static String getOpenbankId(){
		String openbankid = getProps().getProperty("openbankid");
		if (StringUtils.isBlank(openbankid)){
			throw new RuntimeException("openbankid未配置"); 
		}
		return openbankid;
	}
	
	public static String getWechatId(){
		String wechatId = getProps().getProperty("wechatId");
		if (StringUtils.isBlank(wechatId)){
			throw new RuntimeException("wechatId未配置"); 
		}
		return wechatId;
	}
	
	public static String getEncodingAESKey(){
		String encodingaeskey = getProps().getProperty("encodingaeskey");
		if (StringUtils.isBlank(encodingaeskey)){
			throw new RuntimeException("encodingaeskey未配置"); 
		}
		return encodingaeskey;
	}
	/**
	 * 获取系统运行环境
	 * @return
	 */
	public static String getRunEvnVar(){
		String run_env = getProps().getProperty("run_env");
		if (StringUtils.isBlank(run_env)){
			throw new RuntimeException("run_env未配置");
		}
		return run_env;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("appid=" + WeixinApplication.getAppId());
		System.err.println("appsecret=" + WeixinApplication.getAppsecret());
		System.err.println("token=" + WeixinApplication.getToken());
		System.err.println("openbankid=" + WeixinApplication.getOpenbankId());
		System.err.println("wechatId=" + WeixinApplication.getWechatId());
	}
	/**
	 * 是否性能测试
	 * @return
	 */
	public static final boolean isPerformance(){
		String test = getSystemProperties(WEIXIN_PERFORMANCE_TEST_MODE);
		if (StringUtils.isBlank(test) || test.trim().equals("false")){
			return false;
		}
		return true;
	}
	public static final String getSystemProperties(String propKey){
		String temp =  System.getProperty(propKey);
		if (temp == null) return "";
		return temp;
	}
}
