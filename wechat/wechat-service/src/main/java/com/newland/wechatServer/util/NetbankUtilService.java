package com.newland.wechatServer.util;

import org.springframework.stereotype.Service;

/**
 * 网银工具类
 * @author ShiZhenning
 * @since 2009-10-21
 * @version 4.0
 */
@Service
public class NetbankUtilService {

	/**
	 * 生成短信口令对象
	 * @return
	 */
	public SmsSecurity genSmsSecurityObj(){
		return new SmsSecurity();
	}
	/**
	 * 生成8位随机登录密码
	 * @return
	 */
	public String genRandomLoginPwd(){
		WordGenerator words = new RandomWordGenerator("23456789abcdefghijkmnpqrstuvwxyz");
		return words.getWord(new Integer(8));
	}

}
