package com.cib.alipayserver.account.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
/**
 * 信用卡未出账账单
 * 
 * @ClassName: UnSettleBill  
 * @Description: 
 * @author: xuzz 
 * @date:2014-5-8
 */
public class UnSettleBill {
	/**
	 * 信用卡卡号
	 */
	private String acctNo;
	/**
	 * 货币种类
	 */
	private String currency;
	/**
	 * 未出账总金额
	 */
	private BigDecimal totalAmount = BigDecimal.ZERO;
	/**
	 * 信用卡交易明细
	 */
	private List<CreditCardTransDetail> list = new ArrayList<CreditCardTransDetail>();
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<CreditCardTransDetail> getList() {
		return list;
	}
	public void setList(List<CreditCardTransDetail> list) {
		this.list = list;
	}
	public BigDecimal getTotalAmount() {
		for(CreditCardTransDetail c : list){
			if("-".equals(c.getTransAmountSign())){
				totalAmount = totalAmount.subtract(c.getTransAmount());
			}
			else{
				totalAmount = totalAmount.add(c.getTransAmount());
			}
		}
		return totalAmount.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
	}
	

}
