package com.cib.alipayserver.service;

import java.util.List;

import com.cib.alipayserver.account.model.Total;


/**
 * 查询余额
 * @author dvlp
 *
 */
public interface QueryBalanceService {
	
	/**
	 * 查询余额
	 * @param acctNo
	 * @return
	 */
	List<Total> queryBalance(String acctNo);

}
