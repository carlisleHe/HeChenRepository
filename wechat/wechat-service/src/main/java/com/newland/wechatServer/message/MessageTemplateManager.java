package com.newland.wechatServer.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.message.impl.MsgFormatTemplateImpl;
@Service
public class MessageTemplateManager implements InitializingBean{
	/**
	 * 默认资源文件名称
	 */
	public static final String DEFAULT_MESSAGE_SOURCE = "MessageTemplate";
	/**
	 * 默认短信模板
	 * {0} 短信口令
	 * {1} 业务描述
	 */
	public static final String DEFULAT_SMS_TEMPLATE = "DEFULAT";
	
	
	/**
	 * 信用卡设置密码
	 * {0} 短信口令
	 */
	public static final String CREDITCARD_SET_PASSWORD_TEMPLATE="CREDITCARD_SET_PASSWORD_TEMPLATE";
	
	/**
	 * 快捷支付解冻模版
	 * {0} 账号
	 * {1} 商户名称
	 * {2} 短信口令
	 */
	public static final String EASYPAYMENT_THAW_SMS_TEMPLATE="EASYPAYMENT_THAW_SMS_TEMPLATE";
	
	
	/**
	 * 资源文件名称
	 */
	private String messageSourceName = DEFAULT_MESSAGE_SOURCE;
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	
	private Map<String, MsgFormatTemplate> templateMap;
	

	
	public void init(){
		templateMap = new HashMap<String, MsgFormatTemplate>();
		
		templateMap.put(CREDITCARD_SET_PASSWORD_TEMPLATE, new MsgFormatTemplateImpl(messageSource, CREDITCARD_SET_PASSWORD_TEMPLATE));
		templateMap.put(EASYPAYMENT_THAW_SMS_TEMPLATE, new MsgFormatTemplateImpl(messageSource, EASYPAYMENT_THAW_SMS_TEMPLATE));
	}
	public String format(Locale locale,String templateName, Object ...args){
		MsgFormatTemplate mft = this.templateMap.get(templateName);
		return mft.format(locale, args);
	}
	
	public static String renderAccountNo(String accountNo){
		if (StringUtils.isBlank(accountNo)){
			return "";
		}
		if (accountNo.length() < 5) return accountNo;
		return accountNo.substring(accountNo.length()-5,accountNo.length()-1);
	}

	public String getMessageSourceName() {
		return messageSourceName;
	}

	public void setMessageSourceName(String messageSourceName) {
		this.messageSourceName = messageSourceName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}
}
