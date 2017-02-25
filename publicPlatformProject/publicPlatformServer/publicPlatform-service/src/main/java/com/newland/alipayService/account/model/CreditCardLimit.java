package com.newland.alipayService.account.model;

import java.math.BigDecimal;
/**
 * 
 * @ClassName: CreditCardLimit  
 * @Description: 信用卡额度 
 * @author: xuzz 
 * @date:2014-3-26 下午12:54:31
 */
public class CreditCardLimit {
	/**
	 * 信用卡卡号
	 */
	private String  acctNo;
	/**
	 * 信用额度
	 */
	private BigDecimal creditLimitAmount;
	/**
	 * 可用额度
	 */
	private BigDecimal useableLimitAmount;
	/**
	 * 账面额度
	 */
	private BigDecimal acctLimitAmount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 账面额度符号
	 */
	private String amountSymbol; 
	/**
	 * 预借现金可用额度
	 */
	private BigDecimal useableMoneyLimitAmount;

	
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public BigDecimal getCreditLimitAmount() {
		return creditLimitAmount;
	}
	public void setCreditLimitAmount(BigDecimal creditLimitAmount) {
		this.creditLimitAmount = creditLimitAmount;
	}
	public BigDecimal getUseableLimitAmount() {
		return useableLimitAmount;
	}
	public void setUseableLimitAmount(BigDecimal useableLimitAmount) {
		this.useableLimitAmount = useableLimitAmount;
	}
	public BigDecimal getAcctLimitAmount() {
		return acctLimitAmount;
	}
	public void setAcctLimitAmount(BigDecimal acctLimitAmount) {
		this.acctLimitAmount = acctLimitAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmountSymbol() {
		return amountSymbol;
	}
	public void setAmountSymbol(String amountSymbol) {
		this.amountSymbol = amountSymbol;
	}
	public BigDecimal getUseableMoneyLimitAmount() {
		return useableMoneyLimitAmount;
	}
	public void setUseableMoneyLimitAmount(BigDecimal useableMoneyLimitAmount) {
		this.useableMoneyLimitAmount = useableMoneyLimitAmount;
	}
	

}
