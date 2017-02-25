package com.newland.alipayService.message.impl;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.newland.alipayService.message.MsgFormatTemplate;

/**
 * 信息格式化模板
 * @author ShiZhenning
 *
 * @since 2011-1-24上午10:25:50
 */
public class MsgFormatTemplateImpl implements MsgFormatTemplate {
	
	private MessageSource messageSource;
	
	private String template;
	
	public MsgFormatTemplateImpl(MessageSource messageSource, String template){
		this.messageSource = messageSource;
		this.template = template;
	}

	@Override
	public String format(Locale locale, Object... args) {
		return this.messageSource.getMessage(template, args, locale);
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
