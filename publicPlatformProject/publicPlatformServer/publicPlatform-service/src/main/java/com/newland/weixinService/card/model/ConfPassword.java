package com.newland.weixinService.card.model;

/**
 *
 * @Description: 设置查询密码
 * @ClassName:SetPassword
 * @author:wengxy
 * @date:2015-11-3
 *
 */
public class ConfPassword {
	
	/**
	 * 信用卡
	 */
	private String acctNo;
	/**
	 * 信用卡,根式化，在界面显示
	 */
	private String acctNoInput;
	/**
	 * 证件号
	 */
	private String certNo;
	/**
	 * 交易密码
	 */
	private String tranPassword;
	/**
	 * 查询密码
	 */
	private String queryPassword;
	/**
	 * 持卡签名后三位 
	 */
	private String sign;
	
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getTranPassword() {
		return tranPassword;
	}
	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}
	public String getQueryPassword() {
		return queryPassword;
	}
	public void setQueryPassword(String queryPassword) {
		this.queryPassword = queryPassword;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAcctNoInput() {
		return acctNoInput;
	}
	public void setAcctNoInput(String acctNoInput) {
		this.acctNoInput = acctNoInput;
	}
	
	
}
