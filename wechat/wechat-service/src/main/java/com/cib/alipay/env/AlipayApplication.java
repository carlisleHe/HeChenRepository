package com.cib.alipay.env;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


public class AlipayApplication{
	
	/**
	 * 性能测试模式
	 */
	public static final String ALIPAY_PERFORMANCE_TEST_MODE = "PERFORMANCE_MODE";
	
	private static Properties props;
	
	public static final String getSystemProperties(String propKey){
		String temp =  System.getProperty(propKey);
		if (temp == null) return "";
		return temp;
	}
	private static Properties getProps(){
		synchronized(AlipayApplication.class){
			if (props == null){
				System.out.println(AlipayApplication.class.getClassLoader().getResource("runtime.properties").getPath());
				InputStream in = AlipayApplication.class.getClassLoader().getResourceAsStream("runtime.properties");
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
	/**
	 * 支付宝公钥
	 * @return
	 */
	public static String getAlipayPublicKey(){
		String alipaypublickey = getProps().getProperty("alipaypublickey");
		if (StringUtils.isBlank(alipaypublickey)){
			throw new RuntimeException("alipaypublickey未配置");
		}
		return alipaypublickey;
	}
	/**
	 * 开发者私钥
	 * @return
	 */
	public static String getCusPrivateKey(){
		String cusprivatekey = getProps().getProperty("cusprivatekey");
		if (StringUtils.isBlank(cusprivatekey)){
			throw new RuntimeException("cusprivatekey未配置");
		}
		return cusprivatekey;
	}
	/**
	 * 支付宝网关地址
	 * @return
	 */
	public static String getAlipayGWUrl(){
		String alipay_gateway_url = getProps().getProperty("alipay_gateway_url");
		if (StringUtils.isBlank(alipay_gateway_url)){
			throw new RuntimeException("alipay_gateway_url未配置");
		}
		return alipay_gateway_url;
	}
	/**
	 * 开发者公钥
	 * @return
	 */
	public static String getCuspublickey(){
		String cuspublickey = getProps().getProperty("cuspublickey");
		if (StringUtils.isBlank(cuspublickey)){
			throw new RuntimeException("cuspublickey未配置");
		}
		return cuspublickey;
	}
	/**
	 * 获取系统运行环境
	 * @return
	 */
	public static String getRunEvnVar(){
		String run_env = getProps().getProperty("run_env");
		if (StringUtils.isBlank(run_env)){
			throw new RuntimeException("cuspublickey未配置");
		}
		return run_env;
	}
	/**
	 * 是否性能测试
	 * @return
	 */
	public static final boolean isPerformance(){
		String test = getSystemProperties(ALIPAY_PERFORMANCE_TEST_MODE);
		if (StringUtils.isBlank(test) || test.trim().equals("false")){
			return false;
		}
		return true;
	}
}
