/*
 * SmsTag.java
 * 功能：发送短信控件TAG
 * 类名：SmsTag
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-2-25 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
*/
package com.newland.alipayserver.tags.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 发送短信控件TAG
 * @author   黄博飞
 * @version  Ver 1.0 2011-2-25
 * @since    SmsTag Ver 1.0
 */
public class SmsTag extends AbstractUITag{
	private static final long serialVersionUID = 7274712952665518391L;
	/**
	 * 输入框控件p的Class
	 */
	private String pClass;
	/**
	 * 标签的Class
	 */
	private String labelClass;
	/**
	 * 标签显示
	 */
	private String labelValue;
	/**
	 * 短信输入框的Class
	 */
	private String inputClass;
	/**
	 * 输入框的值
	 */
	private String inputValue;
	/**
	 * 点击输入值
	 */
	private String buttonValue;
	/**
	 * 短信输入框ID
	 */
	private String id;
	/**
	 * 输入框最大值
	 */
	private String maxLength;
	/**
	 * 先发送后倒计时标志
	 */
	private boolean sendLater;
	/**
	 * 是否显示免费信息
	 */
	private boolean freeInfo;
	/**
	 * 短信是否为必输入 加*标示
	 */
	private boolean noStar;
	/**
	 * 页面手机号ID
	 */
	private String telPhoneId;
	/**
	 * 是否外部发送短信
	 */
	private boolean fromOut;
	/**
	 * 发送完成后显示文字
	 */
	private String sentMsg;
//	/**
//	 * 是否校验码(当前页验证)
//	 */
//	private boolean validSms;
	/**
	 * 是否自动判断已发送
	 * 规则：适用于页面手动触发发送短信验证码，初始进页面时未发送短信页面可触发发送，
	 * 下一步报错返回时验证码未失效时，页面显示验证码倒计时。
	 * 判断依据：是否能取到缓存的短信。
	 * 前提：初始进入含短信验证码页面时，先清除缓存的短信
	 */
	private boolean autoJudge;
	/**
	 * 可重发或失效时发送按钮名称
	 */
	private String btnResendValue;
	/**
	 * 发送短信校验码触发事件<br/>
	 * 不配置时，使用模板中默认的sendSms()方法<br/>
	 * 适用于：页面 手动触发短信校验码并且发送短信前需加自定义脚本判断，甚至异步调用时使用
	 */
	private String onClick;
	
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
        /**
     * 回单查询
     * 手机短信方式发送受理单查询号
     * 当该属性为true时，sendLatter直接设置为true，页面不显示剩余时间和序号
     * 
     */
        private boolean receiptBusiness;
	public void setBtnResendValue(String btnResendValue) {
		this.btnResendValue = btnResendValue;
	}
	public void setAutoJudge(boolean autoJudge) {
		this.autoJudge = autoJudge;
	}
//	public void setValidSms(boolean validSms) {
//		this.validSms = validSms;
//	}
	public void setSentMsg(String sentMsg) {
		this.sentMsg = sentMsg;
	}
	public void setFromOut(boolean fromOut) {
		this.fromOut = fromOut;
	}
	public void setTelPhoneId(String telPhoneId) {
		this.telPhoneId = telPhoneId;
	}
	public void setNoStar(boolean noStar) {
		this.noStar = noStar;
	}
	public void setFreeInfo(boolean freeInfo) {
		this.freeInfo = freeInfo;
	}
	public void setSendLater(boolean sendLater) {
		this.sendLater = sendLater;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setpClass(String pClass) {
		this.pClass = pClass;
	}
	public void setLabelClass(String labelClass) {
		this.labelClass = labelClass;
	}
	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	public void setInputClass(String inputClass) {
		this.inputClass = inputClass;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public void setButtonValue(String buttonValue) {
		this.buttonValue = buttonValue;
	}
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new Sms(arg0,arg1,arg2);
	}
	protected void populateParams(){
		super.populateParams();
		Sms p = (Sms)component;
		p.setId(id);
		p.setpClass(pClass);
		p.setLabelClass(labelClass);
		p.setLabelValue(labelValue);
		p.setInputClass(inputClass);
		p.setInputValue(inputValue);
		p.setMaxLength(maxLength);
		p.setButtonValue(buttonValue);
		p.setSendLater(sendLater);
		p.setFreeInfo(freeInfo);
		p.setNoStar(noStar);
		p.setTelPhoneId(telPhoneId);
		p.setFromOut(fromOut);
		p.setSentMsg(sentMsg);
//		p.setValidSms(validSms);
		p.setAutoJudge(autoJudge);
                p.setReceiptBusiness(receiptBusiness);
		p.setBtnResendValue(btnResendValue);
		p.setOnClick(onClick);
	}


    public void setReceiptBusiness(boolean receiptBusiness) {
        this.receiptBusiness = receiptBusiness;
    }
        
}
