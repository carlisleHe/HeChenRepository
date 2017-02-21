package com.cib.alipayserver.account.service;

import com.cib.alipayserver.account.model.AcctInfo;
import com.cib.alipayserver.account.model.CreditCardBill;
import com.cib.alipayserver.account.model.CreditcardAccount;
import com.cib.alipayserver.account.model.FinanceAccount;
import com.cib.alipayserver.account.model.FinanceAccountTransDetail;
import com.cib.alipayserver.account.model.UnSettleBill;
import com.intensoft.exception.AppBizException;


/**
 * 账户服务类
 * @since 2011-1-11		liw
 * @since 2012-10-19	fangsq	增加信用卡状态查询
 * @since 2012-5-30	    chenxw	借记卡通过统一认证系统获取认证服务
 * @since 2014-03-20	xuzz 开放平台账户服务类
 */
public interface AccountService {
	/**
	 * 查询理财卡的账户余额信息
	 * @param financeAccount 理财卡账户信息
	 * @return 理财卡账户余额信息
	 * @throws AppBizException
	 */
	public FinanceAccount queryBalance(FinanceAccount financeAccount)  throws AppBizException;
	/**
	 * 查询信用卡额度
	 * @param acctNo 账号
	 * @return
	 * @throws AppBizException
	 */
	public CreditcardAccount queryLimit(String acctNo)  throws AppBizException;
	/**
	 * 查询信用卡账单
	 * @param creditCardBill 信用卡账单
	 * @return
	 * @throws AppBizException
	 */
	public CreditCardBill queryBill(CreditCardBill creditCardBill)  throws AppBizException;
	
	/**
	 * 验证卡号的密码
	 * @param acctNo 账户信息
	 * @param passwd 账号密码
	 * @throws AppBizException
	 */
	public void validPasswd(String acctNo,String passwd) throws AppBizException;
	
	/**
	 * 根据卡号查询证件信息并回填
	 * @param acctNo 账户信息
	 * @return 回填证件信息的账户信息
	 */
	public AcctInfo findAcctInfoByAcctNo(String acctNo) throws AppBizException;
	/**
	 * 根据卡号查询开户地区信息
	 * @param accountNo
	 * @return
	 * @throws AppBizException
	 */
	public AcctInfo findBrNoByAcctNo(String accountNo) throws AppBizException;
	/**
	 * 是否申请过借记卡
	 * @param certNo 证件号
	 * @param certType 证件类型
	 * @return "acctNo"-申请过，""-未申请
	 */
	public String ApplyDebitCard(String certNo,String certType);
	/**
	 * 查询理财卡的账户交易明细
	 * @param finAbstractAccount
	 * @return 账户交易明细信息（最近10笔）
	 * @throws AppBizException
	 */
	public FinanceAccountTransDetail queryDetail(FinanceAccount finAbstractAccount)  throws AppBizException;
	/**
	 * 查询未出账账单
	 * @param unSettleBill 未出账账单
	 * @return
	 * @throws AppBizException
	 */
	public UnSettleBill queryUnsettleBill(UnSettleBill unSettleBill)  throws AppBizException;
}
