package com.cib.alipayserver.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Total implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal amount = new BigDecimal(0);
	
	private String title;
	
	private boolean total = false;
	
	private String currency;

	
	public void add(BigDecimal amount){
		this.amount = this.amount.add(amount);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTotal() {
		return total;
	}

	public void setTotal(boolean total) {
		this.total = total;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


}
