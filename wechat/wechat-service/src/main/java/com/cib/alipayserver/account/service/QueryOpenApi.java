package com.cib.alipayserver.account.service;

import com.cib.alipayserver.account.model.CreditCardBill;
import com.cib.alipayserver.account.model.CreditcardAccount;
import com.cib.alipayserver.account.model.FinanceAccount;
import com.cib.alipayserver.account.model.FinanceAccountTransDetail;
import com.cib.alipayserver.account.model.UnSettleBill;
import com.intensoft.exception.AppBizException;
/**
 * 
 * @ClassName: QueryOpenApi  
 * @Description: 账户查询类的openapi 
 * @author: xuzz 
 * @date:2014-3-20 上午11:42:19
 */
public interface QueryOpenApi {
	/**
	 * 理财卡账户余额查询
	 * @param acctNo 账号
	 * @return 账户余额信息
	 */
	public FinanceAccount queryBalance(String acctNo);
	/**
	 * 理财卡账户交易明细查询
	 * @param acctNo 账号
	 * @return 账户交易明细信息
	 * @throws AppBizException
	 */
	public FinanceAccountTransDetail queryDetail(String acctNo) throws AppBizException;
	/**
	 * 信用卡账户额度查询
	 * @param acctNo 账号
	 * @return 信用卡账户
	 */
	public CreditcardAccount queryLimit(String acctNo);
	/**
	 * 信用卡账户账单查询
	 * @param acctNo 账号
	 * @param billDate 账单年月
	 * @param currency 币种
	 * @return 信用卡账户账单信息
	 * @throws AppBizException
	 */
	public CreditCardBill queryBill(String acctNo,String billDate,String currency) throws AppBizException;
	/**
	 * 信用卡账户未出账单查询
	 * @param acctNo 账号
	 * @param currency 币种
	 * @return 信用卡账户未出账单信息
	 * @throws AppBizException
	 */
	public UnSettleBill queryUnsettleBill(String acctNo,String currency) throws AppBizException;

}
