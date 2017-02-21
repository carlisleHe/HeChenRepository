/**
 * 
 */
package com.cib.alipayserver.txn;

/**
 * 交易报错码
 * @author wot_hechen
 * 
 */
public class TxnErrorCode {
	// 密码不符
	public static final Integer DC_PWD_ERR_3805 = 3805;
	// 凭证密码不符
	public static final Integer DC_PWD_ERR_1814 = 1814;
	// 密码错
	public static final Integer DC_PWD_ERR_7118 = 7118;
	//
	public static final Integer DC_PWD_ERR_1830 = 1830;

	// 信用卡
	// 超过每日密码连续重试最大错误次数
	public static final Integer CC_PWD_ERR_7290 = 7290;
	// 超过累计密码错误次数
	public static final Integer CC_PWD_ERR_7289 = 7289;
	// 未激活
	public static final Integer CC_PWD_ERR_7300 = 7300;

}
