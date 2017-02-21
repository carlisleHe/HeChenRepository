package com.cib.alipayserver.card.model;

/**
 *
 * @Description: 设置查询密码
 * @ClassName:CreditCardPassWord
 * @author:wengxy
 * @modify:hc
 * @date:2016.01.26
 *
 */
public class CreditCardPassword {
	
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
	 * 证件类型
	 */
	private String certType;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 客户输入证件号
	 */
	private String inputCertNo;
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
	/**
	 * 输出格式的手机号：如168****1194
	 */
	private String formMobile;
	/**
	 * 手机号
	 */
	private String mobile;
	
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
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getInputCertNo() {
		return inputCertNo;
	}
	public void setInputCertNo(String inputCertNo) {
		this.inputCertNo = inputCertNo;
	}
	public String getFormMobile() {
		return formMobile;
	}
	public void setFormMobile(String formMobile) {
		this.formMobile = formMobile;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "CreditCardPassword [acctNo=" + acctNo + ", acctNoInput="
				+ acctNoInput + ", certNo=" + certNo + ", certType=" + certType
				+ ", custName=" + custName + ", inputCertNo=" + inputCertNo
				+ ", tranPassword=" + tranPassword + ", queryPassword="
				+ queryPassword + ", sign=" + sign + ", formMobile="
				+ formMobile + ", mobile=" + mobile + "]";
	}
	
	
}
