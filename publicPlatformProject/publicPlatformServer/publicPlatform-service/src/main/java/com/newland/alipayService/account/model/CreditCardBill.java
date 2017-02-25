/* CreditCardBill.java
 *
 * 功能：
 * 类名：CreditCardBill.java
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-4-25 	zhengmj         初版
 *
 * Copyright (c) 2006, 2011 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
*/
package com.newland.alipayService.account.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 信用卡账单
 * @author   zhengmj
 * @version  Ver 1.0 2011-4-25
 * @since    CreditCardBill.java Ver 1.0
 */
public class CreditCardBill {
	/**
	 * 信用卡卡号
	 */
	private String acctNo;
	/**
	 * 自动还款账号
	 */
	private String autoRefundAcct;
	/**
	 * 账单日期
	 */
	private Date billDate;
	/**
	 * 截止还款日期
	 */
	private Date endRefundDate;
	/**
	 * 应还款额
	 */
	private BigDecimal dueRepayAmount;
	/**
	 * 最低还款额
	 */
	private BigDecimal lowPayment;
	/**
	 * 本期账单金额
	 */
	private BigDecimal billAmount;
	/**
	 * 账单金额符号
	 */
	private String billAmountSign;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 未出账单金额符号
	 */
	private String unsettleBillAmountSign;
	/**
	 * 未出账单金额
	 */
	private BigDecimal unsettleBillAmount;
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAutoRefundAcct() {
		return autoRefundAcct;
	}
	public void setAutoRefundAcct(String autoRefundAcct) {
		this.autoRefundAcct = autoRefundAcct;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Date getEndRefundDate() {
		return endRefundDate;
	}
	public void setEndRefundDate(Date endRefundDate) {
		this.endRefundDate = endRefundDate;
	}
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public String getBillAmountSign() {
		return billAmountSign;
	}
	public void setBillAmountSign(String billAmountSign) {
		this.billAmountSign = billAmountSign;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getDueRepayAmount() {
		return dueRepayAmount;
	}
	public void setDueRepayAmount(BigDecimal dueRepayAmount) {
		this.dueRepayAmount = dueRepayAmount;
	}
	public String getUnsettleBillAmountSign() {
		return unsettleBillAmountSign;
	}
	public void setUnsettleBillAmountSign(String unsettleBillAmountSign) {
		this.unsettleBillAmountSign = unsettleBillAmountSign;
	}
	public BigDecimal getUnsettleBillAmount() {
		return unsettleBillAmount;
	}
	public void setUnsettleBillAmount(BigDecimal unsettleBillAmount) {
		this.unsettleBillAmount = unsettleBillAmount;
	}
	public BigDecimal getLowPayment() {
		return lowPayment;
	}
	public void setLowPayment(BigDecimal lowPayment) {
		this.lowPayment = lowPayment;
	}
	
}

