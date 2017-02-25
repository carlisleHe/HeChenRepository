/*
 *
 * 功能：短信服务
 * 类名：MsgService.java
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-9-2 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.newland.alipayService.message;

import java.util.Map;

import com.intensoft.exception.AppBizException;
import com.newland.alipayService.message.model.MessageTemplate;
import com.newland.base.util.SmsSecurity;

/**
 * 短信服务
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-9-2
 * @since MsgService.java Ver 1.0
 */
public interface MsgService {
	public static final String SMS_SENT = "SMS_SENT_MESSAGE";
	public static final String SENT_RECIEPT_COUNT = "SENT_RECIEPT_COUNT";// 已发送的回单次数
	public static final String VALIDATED_TELPHONE = "VALIDATED_TELPHONE";// 已校验的手机号
	/**
	 * 发送短信
	 * 
	 * @param sms
	 * @param session
	 * @return
	 */
	SmsSecurity sendSms(MessageTemplate sms, Map<String, Object> session) throws AppBizException;
	
	/**
	 * 发送短信
	 * <p>发送不包含短信验证码的情况下使用</p>
	 * @param sms
	 * @author hechen
	 */
	void sendSms(MessageTemplate sms)  throws AppBizException;
}
