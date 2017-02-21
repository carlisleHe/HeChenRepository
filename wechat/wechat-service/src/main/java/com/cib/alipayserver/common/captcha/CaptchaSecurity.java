/*
 * CaptchaSecurity.java
 * 功能：附加码类
 * 类名：CaptchaSecurity
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-3-14 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.cib.alipayserver.common.captcha;

import java.util.Random;

import com.intensoft.exception.AppRTException;

/**
 * 附加码类
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-3-14
 * @since CaptchaSecurity Ver 1.0
 */
public class CaptchaSecurity {
	/**
	 * 存放在Session中附加码值的名称
	 */
	public final static String IDENTIFYING_CODE = "identifying_code";
	/**
	 * 默认过期日间5分钟
	 */
	private static long DEFAULT_TIMEOUT = 300000;
	/**
	 * 附加码默认长度
	 */
	private static final int DEFAULT_SMS_LENGTH = 4;
	/**
	 * 附加码
	 */
	private String captcha;
	/**
	 * 生成时毫秒数
	 */
	private long timemillisecond;
	/**
	 * 用于测试系统时跳过附加码验证
	 */
	private final boolean needVerify;

	public CaptchaSecurity(boolean needVerify) {
		this(DEFAULT_SMS_LENGTH, needVerify);
	}

	public CaptchaSecurity(int smsLength, boolean needVerify) {
		this.needVerify = needVerify;
		this.genNewCaptcha();
	}
	/**
	 * 生成新的附加码
	 */
	public void genNewCaptcha(){
		this.timemillisecond = System.currentTimeMillis();
		Random random = new Random();
		this.captcha = this.convert4zero(random.nextInt(10000) + "", 4);
	}
	/**
	 * 验证附加码
	 * 
	 * @param code
	 * @return
	 */
	public boolean valid(String code) {
		if(this.needVerify){
			if (this.expire()){
				throw new AppRTException("SMS_INVALIDATION", "附加码已超时失效，请重试");
			}
			if (this.captcha.equalsIgnoreCase(code)) {
				return true;
			} else {
				return false;
			}
		}else{
			return true;
		}
		
	}

	/**
	 * 是否过期
	 * 
	 * @return
	 */
	private boolean expire() {
		long sysmillisecond = System.currentTimeMillis();
		if ((sysmillisecond - this.timemillisecond) > DEFAULT_TIMEOUT) {
			return true;
		}
		return false;
	}

	/**
	 * 如果指定串不足指定长度（length)，则在前面补0
	 * 
	 * @param str
	 *            指定串
	 * @param length
	 *            指定长度
	 * @return a String
	 */
	private String convert4zero(String str, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = length - str.length(); i > 0; i--) {
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}

	public String getCaptcha() {
		return captcha;
	}
	public boolean isNeedVerify() {
		return needVerify;
	}
}
