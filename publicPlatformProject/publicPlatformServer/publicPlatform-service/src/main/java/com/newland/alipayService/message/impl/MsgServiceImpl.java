/*
 *
 * 功能：短信服务
 * 类名：MsgServiceImpl.java
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-9-2 	黄博飞         初版
 * 	V1.0.1	2013-10-28	黄博飞		由于短信平台系统升级，增加了单独支持短信口令发送的交易，部分改用3908交易替代3909（注：3908与3909交易接口一致）
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.newland.alipayService.message.impl;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.intensoft.exception.AppBizException;
import com.newland.alipayService.message.MessageTemplateManager;
import com.newland.alipayService.message.MsgService;
import com.newland.alipayService.message.NetbankSendMsgService;
import com.newland.alipayService.message.model.MessageTemplate;
import com.newland.base.util.NetbankUtilService;
import com.newland.base.util.SmsSecurity;

/**
 * 短信服务
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-9-2
 * @since MsgServiceImpl.java Ver 1.0
 */
@Service("msgService")
public class MsgServiceImpl implements MsgService {
	private static final Logger logger = LoggerFactory
			.getLogger(MsgServiceImpl.class);
	@Resource(name = "netbankUtilService")
	private NetbankUtilService netbankUtilService;
	@Resource(name = "messageTemplateManager")
	private MessageTemplateManager messageTemplateManager;
	@Resource(name = "netbankSendMsgService")
	private NetbankSendMsgService netbankSendMsgService;

	/**
	 * 发送短信
	 * 
	 * @param sms
	 * @param session
	 * @return
	 */
	@Override
	public SmsSecurity sendSms(MessageTemplate sms, Map<String, Object> session)
			throws AppBizException {
		Assert.notNull(sms);
		Assert.notNull(session);
		if (sms == null || StringUtils.isBlank(sms.getTemplateName())) {
			throw new AppBizException("error.no_found_template",
					"系统异常，获取短信模板失败！");
		}
		if (MessageTemplateManager.DEFULAT_SMS_TEMPLATE.equals(sms
				.getTemplateName())) {
			return sendDefaultMessage(sms, session);
		} else if (MessageTemplateManager.CREDITCARD_SET_PASSWORD_TEMPLATE
				.equals(sms.getTemplateName())) {

			return sendCreditCardSetPwdMessage(sms, session);
		}
		// /**
		// * 2013-1-17 hechen 快捷支付解冻
		// */
		// else if (MessageTemplateManager.EASYPAYMENT_THAW_SMS_TEMPLATE
		// .equals(sms.getTemplateName())) {
		// return sendEasyPaymentThawMessage(sms, session);
		// }
		return null;
	}

	@Override
	public void sendSms(MessageTemplate sms) throws AppBizException {
		if (sms == null || StringUtils.isBlank(sms.getTemplateName())) {
			throw new AppBizException("error.no_found_template",
					"系统异常，获取短信模板失败！");
		}

//		/**
//		 * 2014-8-29 hechen 行内转入协议签约成功 短信模版
//		 */
//		if (MessageTemplateManager.INNERBANK_TRANSFER_CONTRACT_SIGN_SUC_SMS_TEMPLATE
//				.equals(sms.getTemplateName())) {
//			sendInnerBankTransContractSucMessage(sms);
//		} else if (MessageTemplateManager.INTERBANK_TRANSFER_CONTRACT_SIGN_SUC_SMS_TEMPLATE
//				.equals(sms.getTemplateName())) {
//			sendInterBankTransContractSucMessage(sms);
//		}
	}

	/**
	 * 发送短信口令
	 * 
	 * @param sms
	 * @throws AppBizException
	 */
	private SmsSecurity sendDefaultMessage(MessageTemplate sms,
			Map<String, Object> session) throws AppBizException {
		SmsSecurity authCode = netbankUtilService.genSmsSecurityObj();

		Object[] args = { authCode.getSerials()};

		String msg = messageTemplateManager.format(Locale.CHINA,
				sms.getTemplateName(), args);

		netbankSendMsgService.sendAuthCodeSms(getMPhone(sms), msg);
		session.put(SMS_SENT, authCode);
		return authCode;
	}

	/**
	 * 信用卡设置密码发送短信口令
	 * @param sms
	 * @param session
	 * @return
	 * @throws AppBizException
	 */
	private SmsSecurity sendCreditCardSetPwdMessage(MessageTemplate sms,
			Map<String, Object> session) throws AppBizException {

		SmsSecurity authCode = netbankUtilService.genSmsSecurityObj();

		Object[] args = { authCode.getSmsCode()};

		String msg = messageTemplateManager.format(Locale.CHINA,
				sms.getTemplateName(), args);

		netbankSendMsgService.sendAuthCodeSms(getMPhone(sms), msg);
		session.put(SMS_SENT, authCode);
		return authCode;
	}

	/**
	 * 发送快捷支付解冻短信口令
	 * 
	 * @param sms
	 * @param session
	 * @return
	 * @throws AppBizException
	 */
	// private SmsSecurity sendEasyPaymentThawMessage(MessageTemplate sms,
	// Map<String, Object> session) throws AppBizException {
	// SmsSecurity authCode = netbankUtilService.genSmsSecurityObj();
	//
	// String message = dictionaryUtilsService.getPropValue(
	// "SMS_TEMPLATE_CFG",
	// MessageTemplateManager.EASYPAYMENT_THAW_SMS_TEMPLATE);
	// String msg = null;
	// if (message == null || "".equals(message)) {
	// logger.error("短信配置信息不存在，使用Spring默认配置");
	// msg = messageTemplateManager.format(this.getLocale(), sms
	// .getTemplateName(), getTradeAcctNoFour(sms.getAccount()
	// .getAccountNo()), sms.getSmsContext(), authCode
	// .getSerials());
	// } else {
	// try {
	// Object[] args = { getTradeAcctNoFour(sms.getAccount().getAccountNo()),
	// sms.getSmsContext(), authCode.getSerials() };
	// msg = MessageFormat.format(message, args);
	// } catch (Exception e) {
	// logger.error("短信配置信息有误，使用Spring默认配置：" + e.getMessage(), e);
	// msg = messageTemplateManager.format(this.getLocale(), sms
	// .getTemplateName(), getTradeAcctNoFour(sms.getAccount()
	// .getAccountNo()), sms.getSmsContext(), authCode
	// .getSerials());
	// }
	// }
	//
	// netbankSendMsgService.sendSms(sms.getAccount(), getMPhone(sms), msg);
	// session.put(SMS_SENT, authCode);
	// return authCode;
	// }


	/**
	 * 获取手机号
	 * 
	 * @param account
	 * @return
	 * @throws AppBizException
	 */
	private String getMPhone(MessageTemplate sms) throws AppBizException {
		if (sms == null) {
			throw new AppBizException("error.no_found_sms", "短信模板不能为空！");
		}
		if (StringUtils.isNotBlank(sms.getmPhone())) {
			return sms.getmPhone();
		}else{
			throw new AppBizException("error.sms_get_phone", "获取手机号失败");
		}
//		if (sms.getAccount() == null
//				|| StringUtils.isBlank(sms.getAccount().getCibAccountNo())) {
//			throw new AppBizException("error.no_found_account_no",
//					"短信模板，转出账户不能为空！");
//		}
//		AcctProtectServ acctProtectServ = acctProtectedService
//				.queryAcctProtectServ(sms.getAccount(),
//						UnionAuthTradeModel.FWZL_SMS);
//		if (acctProtectServ.isProtectedBySMS()) {
//			return acctProtectServ.getMobilePhone();
//		} else {
//			throw new AppBizException("error.sms_func_disabled", "未开通短信保护功能！");
//		}

	}
}
