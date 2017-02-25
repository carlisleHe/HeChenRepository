/**
 * 
 */
package com.newland.alipayService.card.service;

import com.intensoft.exception.AppBizException;
import com.newland.alipayService.card.model.CreditCardPassword;

/**
 * 信用卡（查询密码和交易密码）
 * @author wot_hechen
 * 
 */
public interface CreditCardPasswordService {
	// 查询信用卡是否有激活（是否有设置查询密码）3416
	public boolean isSetPassByAcctNo(String acctNo) throws AppBizException;

	// 获取手机号码 3420
	public String findMobileByAcctNo(String acctNo) throws AppBizException;

	// 设置查询密码 3416
	public boolean setQueryPassword(CreditCardPassword creditCardPassword)
			throws AppBizException;

	// 设置交易密码 4614
	public void setTranPassword(CreditCardPassword creditCardPassword)
			throws AppBizException;

	// 校验交易密码 3420
	public void checkTranPassword(CreditCardPassword creditCardPassword)
			throws AppBizException;
}
