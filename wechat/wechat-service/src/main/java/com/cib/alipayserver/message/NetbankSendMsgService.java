package com.cib.alipayserver.message;

import java.io.File;

import com.intensoft.exception.AppBizException;

public interface NetbankSendMsgService {
	
	/**
	 * 发普通短信接口
	 * 调用3909
	 * @param mphone
	 * @param msg
	 * @throws AppBizException 
	 */
	void sendNormalSms(String mphone, String msg) throws AppBizException;
	/**
	 * 发短信口令接口
	 * 调用3908
	 * @param mphone
	 * @param msg
	 * @throws AppBizException 
	 */
	void sendAuthCodeSms(String mphone, String msg) throws AppBizException;
	
	/**
	 * 发送邮件
	 * @param mailAddr
	 * @param mailContent
	 */
	void sendMail(String mailAddr,String titls, String mailContent);
	/**
	 * 发送带附件的邮件
	 * @param mailAddr
	 * @param title
	 * @param mailContent
	 * @param appendix
	 */
	public void sendMail(String mailAddr, String title, String mailContent, File appendix);

  

}
