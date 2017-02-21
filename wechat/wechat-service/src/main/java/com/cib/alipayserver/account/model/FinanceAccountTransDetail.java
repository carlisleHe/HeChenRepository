package com.cib.alipayserver.account.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName: FinanceAccountTransDetail  
 * @Description: 理财账户交易明细 
 * @author: xuzz 
 * @date:2014-3-20 上午11:02:52
 */
public class FinanceAccountTransDetail {
	/**
	 * 客户姓名
	 */
	private String custName;
	/**
	 * 客户账号
	 */
	private String acctNo;
	/**
	 * 交易明细
	 */
	private List<TransDetail> details = new ArrayList<TransDetail>();
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public List<TransDetail> getDetails() {
		return details;
	}
	public void setDetails(List<TransDetail> details) {
		this.details = details;
	} 
	

}
