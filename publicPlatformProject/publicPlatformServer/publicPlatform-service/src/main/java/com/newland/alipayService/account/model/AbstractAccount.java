/***********************************************************************
 * Module:  AbstractAccount.java
 * Author:  dvlp
 * Purpose: Defines the Class AbstractAccount
 ***********************************************************************/

package com.newland.alipayService.account.model;

import org.apache.commons.lang.StringUtils;

/**
 * 账户抽象类
 * 
 * @author ShiZhenning
 * @since 2010-3-15
 */
public abstract class AbstractAccount implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 银联卡号
	 */
	private java.lang.String accountNo;
	/**
	 * 客户名称
	 */
	private java.lang.String ownerName;
	/**
	 * 账户别名
	 */
	private java.lang.String accountName;
	/**
	 * 账户类型
	 */
	private int accountType;
	
	/**
	 * 凭证代号
	 */
	private java.lang.String invoiceNo;
	
	/**
	 * 证件号码
	 */
	private java.lang.String idNo;
	/**
	 * 账户密码
	 */
	private String accountPwd;
	
	/**
	 * 基金代码
	 */
	private String fundCode;
	
	/**
	 * 前台系统编号
	 */
	private String qtxtbh;
	
	
	private String branch;

	private String subBranch;


	public AbstractAccount() {

	}

	

	public java.lang.String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(java.lang.String accountNo) {
		this.accountNo = accountNo;
	}

	public java.lang.String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(java.lang.String ownerName) {
		this.ownerName = ownerName;
	}

	public java.lang.String getAccountName() {
		return accountName;
	}

	public void setAccountName(java.lang.String accountName) {
		this.accountName = accountName;
	}

	
	public java.lang.String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(java.lang.String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public java.lang.String getIdNo() {
		return idNo;
	}

	public String getHideIdNo() {
		if (StringUtils.isBlank(idNo)) {
			return "";
		} else if (idNo.length() >= 5) {
			return idNo.substring(0, idNo.length() - 5) + "****" + idNo.charAt(idNo.length() - 1);
		} else {
			return idNo;
		}
	}


	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}



	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}



	public String getBranch() {
		return branch;
	}



	public void setBranch(String branch) {
		this.branch = branch;
	}



	public String getSubBranch() {
		return subBranch;
	}



	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}



	public String getFundCode() {
		return fundCode;
	}



	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}



	public String getQtxtbh() {
		return qtxtbh;
	}



	public void setQtxtbh(String qtxtbh) {
		this.qtxtbh = qtxtbh;
	}


	

}