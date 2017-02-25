/***********************************************************************
 * Module:  CreditcardAccount.java
 * Author:  dvlp
 * Purpose: Defines the Class CreditcardAccount
 ***********************************************************************/

package com.newland.alipayService.account.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.newland.base.common.Const;
/**
 * 信用卡账户
 * @author ShiZhenning
 * @since 2010-3-15
 */
public class CreditcardAccount extends AbstractAccount {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 移动电话
	 */
	private java.lang.String mobilePhone;
	/**
	 * 有效日期
	 */
	private java.lang.String validityDate;
	/**
	 * 信用卡CVV2
	 */
	private java.lang.String cvv2;
	/**
	 * 预留信息
	 */
	private java.lang.String secretWord;
	
	/**
	 * 申请日期
	 */
	private String openDate;
	/**
	 * 启用日期
	 */
	private String activeDate;
	
	private String autoRefundAcct;
	/**
	 * 额度信息
	 */
	private List<CreditCardLimit> limits = new ArrayList<CreditCardLimit>();
	/**
	 * 账单信息
	 */
	private List<CreditCardBill> bills = new ArrayList<CreditCardBill>(); 

	public CreditcardAccount() {
		this.setBranch(Const.CC_DEFAULT_BRANCH);
		this.setSubBranch(Const.CC_DEFAULT_SUBBRANCH);
	}

	public BigDecimal getBalance() {
		return BigDecimal.ZERO;
	}

	public BigDecimal getUsableBalance() {
		return BigDecimal.ZERO;
	}

	public java.lang.String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(java.lang.String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public java.lang.String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(java.lang.String validityDate) {
		this.validityDate = validityDate;
	}

	public java.lang.String getCvv2() {
		return cvv2;
	}

	public void setCvv2(java.lang.String cvv2) {
		this.cvv2 = cvv2;
	}

	public java.lang.String getSecretWord() {
		return secretWord;
	}

	public void setSecretWord(java.lang.String secretWord) {
		this.secretWord = secretWord;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}

	public String getBankNo() {
		return "08";
	}

	public List<CreditCardLimit> getLimits() {
		return limits;
	}

	public void setLimits(List<CreditCardLimit> limits) {
		this.limits = limits;
	}

	public List<CreditCardBill> getBills() {
		return bills;
	}

	public void setBills(List<CreditCardBill> bills) {
		this.bills = bills;
	}

	public String getAutoRefundAcct() {
		return autoRefundAcct;
	}

	public void setAutoRefundAcct(String autoRefundAcct) {
		this.autoRefundAcct = autoRefundAcct;
	}
}