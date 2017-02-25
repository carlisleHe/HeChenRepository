/*
 * MessageTemplate.java
 * 功能：发送短信模板
 * 类名：MessageTemplate
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-2-28 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.newland.alipayService.message.model;

import java.math.BigDecimal;
import java.util.Date;

import com.newland.alipayService.account.model.AbstractAccount;

/**
 * 发送短信模板
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-2-28
 * @since MessageTemplate Ver 1.0
 */
public class MessageTemplate {

	/**
	 * 转出账户
	 */
	private AbstractAccount account;
	/**
	 * 手机号
	 */
	private String mPhone;
	/**
	 * 回单号
	 */
	private String netTraceNo;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 备用短信信息
	 */
	private String smsContext;
	/**
	 * 对方户名
	 */
	private String targetAccountName;
	/**
	 * 对方卡号
	 */
	private String targetAccountNo;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 转账金额
	 */
	private BigDecimal transferAmount;
	/**
	 * 业务名称
	 */
	private String businessName;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 交易时间
	 */
	private Date traceTime;

	public Date getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(Date traceTime) {
		this.traceTime = traceTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AbstractAccount getAccount() {
		return account;
	}

	public String getmPhone() {
		return mPhone;
	}

	public String getNetTraceNo() {
		return netTraceNo;
	}

	public String getPwd() {
		return pwd;
	}

	public String getSmsContext() {
		return smsContext;
	}

	public String getTargetAccountName() {
		return targetAccountName;
	}

	public String getTargetAccountNo() {
		return targetAccountNo;
	}

	public String getTemplateName() {
		return templateName;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setAccount(AbstractAccount account) {
		this.account = account;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public void setNetTraceNo(String netTraceNo) {
		this.netTraceNo = netTraceNo;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setSmsContext(String smsContext) {
		this.smsContext = smsContext;
	}

	public void setTargetAccountName(String targetAccountName) {
		this.targetAccountName = targetAccountName;
	}

	public void setTargetAccountNo(String targetAccountNo) {
		this.targetAccountNo = targetAccountNo;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
