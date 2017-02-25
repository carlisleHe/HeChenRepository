package com.newland.alipayService.common.encrypt.mac;

import java.io.UnsupportedEncodingException;

/**
 * 用于支持旧的网上支付的消息校验码算法接口
 * <p>
 * 和通用mac算法(定义于volans的encryption模块)的差别在于，
 * 网上支付的消息校验码并不要求支持多编码的方式，
 * 仅要求支持特定的，例如：<tt>GBK</tt>等方式编码。
 * 
 * 
 * @author lance
 * @since 2011/04/06
 */
public interface MsgAuthenticate {
	
	/**
	 * 根据密钥和内容生成消息校验码
	 * 
	 * @param seed 密钥
	 * @param content 内容
	 * @return 消息校验码
	 * @throws UnsupportedEncodingException
	 */
	public String genMac(String seed, String content) throws UnsupportedEncodingException;
	
	/**
	 * 消息校验码校验
	 * 
	 * @param seed 密钥
	 * @param content 内容
	 * @param mac 消息校验码
	 * @return 成功返回TRUE，失败返回FALSE
	 * @throws UnsupportedEncodingException
	 */
	public boolean checkMac (String seed,String content, String mac) throws UnsupportedEncodingException;

}