/***********************************************************************
 * Module:  FinanceAccount.java
 * Author:  dvlp
 * Purpose: Defines the Class FinanceAccount
 ***********************************************************************/

package com.cib.alipayserver.account.model;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.cib.alipayserver.util.AccountUtil;

/**
 * 理财卡账户
 * @author ShiZhenning
 * @since 2010-3-15
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.NONE)
public class FinanceAccount extends AbstractAccount {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 开户行
	 */
	private String bankNo;
	/**
	 * 行内卡号
	 */
	private String cibAcctountNo;
	private int seq = 0;
	/**
	 * 内部账号
	 */
	protected java.util.List<InnerAccount> innerAccount;
	public BigDecimal getBalance() {
		InnerAccount ia = getInnerAccount("01", "262");//取人民币活期账户
		if (ia != null){
			return ia.getBalance();
		}
		return BigDecimal.ZERO;
	}
	public BigDecimal getUsableBalance() {
		InnerAccount ia = getInnerAccount("01", "262");//取人民币活期账户
		if (ia != null){
			return ia.getUsableBalance();
		}
		return BigDecimal.ZERO;
	}
	public String getCibAccountNo() {
		if(cibAcctountNo == null){
			return AccountUtil.calcCibAcctNo(getAccountNo());
		}
		return cibAcctountNo;
		
	}
	public String getBankNo() {
		if (StringUtils.isBlank(bankNo)) {
			if (StringUtils.isNotBlank(getCibAccountNo()) && StringUtils.length(getCibAccountNo()) > 2)
				this.bankNo = StringUtils.substring(getCibAccountNo(), 0, 2);
			else
				this.bankNo = "00";
		}
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public void setCibAcctountNo(String cibAcctountNo) {
		this.cibAcctountNo = cibAcctountNo;
	}
	/** @pdGenerated default getter */
	public java.util.List<InnerAccount> getInnerAccount() {
		if (innerAccount == null)
			innerAccount = new java.util.ArrayList<InnerAccount>();
		return innerAccount;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<InnerAccount> getIteratorInnerAccount() {
		if (innerAccount == null)
			innerAccount = new java.util.ArrayList<InnerAccount>();
		return innerAccount.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newInnerAccount
	 */
	public void setInnerAccount(java.util.List<InnerAccount> newInnerAccount) {
		removeAllInnerAccount();
		for (java.util.Iterator<InnerAccount> iter = newInnerAccount.iterator(); iter.hasNext();)
			addInnerAccount((InnerAccount) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newInnerAccount
	 */
	public void addInnerAccount(InnerAccount newInnerAccount) {
		if (newInnerAccount == null)
			return;
		if (this.innerAccount == null)
			this.innerAccount = new java.util.ArrayList<InnerAccount>();
		if (!this.innerAccount.contains(newInnerAccount))
			this.innerAccount.add(newInnerAccount);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldInnerAccount
	 */
	public void removeInnerAccount(InnerAccount oldInnerAccount) {
		if (oldInnerAccount == null)
			return;
		if (this.innerAccount != null)
			if (this.innerAccount.contains(oldInnerAccount))
				this.innerAccount.remove(oldInnerAccount);
	}

	/** @pdGenerated default removeAll */
	public void removeAllInnerAccount() {
		if (innerAccount != null)
			innerAccount.clear();
	}
	/**
	 * 根据币种和储种获取内部账户对象 需要先执行卡明细查询
	 * 
	 * @param currency
	 * @param depositType
	 * @return
	 */
	public final InnerAccount getInnerAccount(String currency, String depositType) {
		if (CollectionUtils.isEmpty(this.getInnerAccount())) {
			return InnerAccount.newNullInstance();
		}
		List<InnerAccount> list = this.getInnerAccount();
		for (InnerAccount ia : list) {
			if(ia.getCurrency().equals(currency)&&ia.getDepositType().equals(depositType)){
				return ia;
			}
		}
		return InnerAccount.newNullInstance();
	}


	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}