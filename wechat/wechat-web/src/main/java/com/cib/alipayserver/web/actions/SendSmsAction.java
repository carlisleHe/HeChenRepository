/*
 * SendSmsAction.java
 * 功能：发送短信公共模块
 * 类名：SendSmsAction
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-2-25 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.cib.alipayserver.web.actions;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cib.alipayserver.web.annotation.ExecuteResult;
import com.intensoft.exception.AppBizException;
import com.newland.wechatServer.message.MessageTemplateManager;
import com.newland.wechatServer.message.MsgService;
import com.newland.wechatServer.message.model.MessageTemplate;
import com.newland.wechatServer.util.SmsSecurity;

/**
 * 发送短信公共模块
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-2-25
 * @since SendSmsAction Ver 1.0
 */
@ParentPackage("alipay-default")
@Namespace("/base")
@Action(value = "sendSms")
@Results({ @Result(name = "success", type = "json") })
public class SendSmsAction extends BaseAction {
	private static final long serialVersionUID = 3516115759188981822L;
	@Resource(name = "msgService")
	private MsgService msgService;
	/**
	 * 手机号码
	 */
	private String telPhone;
	/**
	 * 短信码序列号
	 */
	private String seqence;
	private boolean showSeqence = false;// 默认不显示序号

	public boolean isShowSeqence() {
		return showSeqence;
	}

	public String getSeqence() {
		return seqence;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	/**
	 * 返回信息
	 */
	private String result;

	public String getResult() {
		return result;
	}

	@Override
	@ExecuteResult(name = GLOBAL_JSON_ERROR)
	public String execute() throws AppBizException {
		MessageTemplate mt = getMessageTemplate();
		if (mt == null) {
			throw new AppBizException("error.no_found_template",
					getText("error.no_found_template"));
		}
		msgService.sendSms(mt, getSessionMap());
		return SUCCESS;
	}
}
