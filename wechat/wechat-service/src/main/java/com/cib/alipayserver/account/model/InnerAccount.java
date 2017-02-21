/***********************************************************************
 * Module:  InnerAccount.java
 * Author:  dvlp
 * Purpose: Defines the Class InnerAccount
 ***********************************************************************/

package com.cib.alipayserver.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ShiZhenning
 * @since 2010-03-15 内部账号 1. 每个账号对应不同的业务类型 2. 小序号为001的为人民币活期结算账号
 * */
public class InnerAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 小序号
	 */
	private java.lang.String seqence;
	/**
	 * 开户日期
	 */
	private Date openDate;
	/**
	 * 到期日期
	 */
	private Date maturity;
	/**
	 * 相关业务代码
	 */
	private java.lang.String relativeBusinessNo;
	/**
	 * 相关业务名称
	 */
	private java.lang.String ralativeBusinessName;
	/**
	 * 账户余额 
	 */
	private BigDecimal balance;
	/**
	 * 可用余额 
	 */
	private BigDecimal usableBalance;
	/**
	 * 冻结余额 
	 */
	private BigDecimal freezeAmount;
	/**
	 * 储种
	 */
	private String depositType;
	/**
	 * 存期
	 */
	private String term;
	/**
	 * 续存存期
	 */
	private String continueTerm;
	/**
	 * 记录状态
	 */
	private String recordStatus;
	/**
	 * 开户金额
	 */
	private BigDecimal openAmount;
	/**
	 * 客户姓名
	 */
	private java.lang.String customerName;
	/**
	 * 账户账号
	 */
	private java.lang.String accountNo;
	/**
	 * 钞汇标志
	 */
	private String cashFlag;
	/**
	 * 货币
	 */
	private String currency;
	
	
	public java.lang.String getSeqence() {
		return seqence;
	}
	public void setSeqence(java.lang.String seqence) {
		this.seqence = seqence;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getMaturity() {
		return maturity;
	}
	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}
	public java.lang.String getRelativeBusinessNo() {
		return relativeBusinessNo;
	}
	public void setRelativeBusinessNo(java.lang.String relativeBusinessNo) {
		this.relativeBusinessNo = relativeBusinessNo;
	}
	public java.lang.String getRalativeBusinessName() {
		return ralativeBusinessName;
	}
	public void setRalativeBusinessName(java.lang.String ralativeBusinessName) {
		this.ralativeBusinessName = ralativeBusinessName;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getUsableBalance() {
		return usableBalance;
	}
	public void setUsableBalance(BigDecimal usableBalance) {
		this.usableBalance = usableBalance;
	}
	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}
	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getContinueTerm() {
		return continueTerm;
	}
	public void setContinueTerm(String continueTerm) {
		this.continueTerm = continueTerm;
	}
	public BigDecimal getOpenAmount() {
		return openAmount;
	}
	public void setOpenAmount(BigDecimal openAmount) {
		this.openAmount = openAmount;
	}
	public java.lang.String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(java.lang.String customerName) {
		this.customerName = customerName;
	}
	public java.lang.String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(java.lang.String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getCashFlag() {
		return cashFlag;
	}
	public void setCashFlag(String cashFlag) {
		this.cashFlag = cashFlag;
	}

	private static InnerAccount defaultAccount;
	
	public static synchronized InnerAccount newNullInstance(){
		if (defaultAccount == null) {
			defaultAccount = new InnerAccount();
			defaultAccount.setAccountNo("");
			defaultAccount.setBalance(new BigDecimal(0));
			defaultAccount.setCashFlag("0");
			defaultAccount.setDepositType("262");
			defaultAccount.setFreezeAmount(new BigDecimal(0));
			defaultAccount.setMaturity(new Date());
			defaultAccount.setOpenAmount(new BigDecimal(0));
			defaultAccount.setOpenDate(new Date());
			defaultAccount.setRecordStatus("1");
			defaultAccount.setSeqence("001");
			defaultAccount.setUsableBalance(new BigDecimal(0));
			defaultAccount.setCurrency("01");
		}
		return defaultAccount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}