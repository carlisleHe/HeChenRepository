package com.newland.alipayService.account.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @ClassName: TransDetail  
 * @Description: 交易明细 
 * @author: xuzz 
 * @date:2014-3-20 上午11:29:30
 */
public class TransDetail {
	/**
	 * 交易时间
	 */
	private Date transTime;
	/**
	 * 借贷标记
	 */
	private String loanFlag;
	/**
	 * 交易金额
	 */
	private BigDecimal transAmount;
	/**
	 * 账户金额
	 */
	private BigDecimal balanceAmount;
	/**
	 * 交易柜员
	 */
	private String transTeller;
	/**
	 * 摘要代号
	 */
	private String digestCode;
	/***
	 * 备注
	 */
	private String remark;
	public Date getTransTime() {
		return transTime;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getTransTeller() {
		return transTeller;
	}
	public void setTransTeller(String transTeller) {
		this.transTeller = transTeller;
	}
	public String getDigestCode() {
		return digestCode;
	}
	public void setDigestCode(String digestCode) {
		this.digestCode = digestCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
