package com.cib.alipayserver.account.model;


/**
 * 
 * @ClassName: AcctInfo
 * @Description: 账户信息
 * @author: xuzz
 * @date:2014-3-24 上午10:40:54
 */
public class AcctInfo {
	/**
	 * 证件类型
	 */
	private String certType;
	/**
	 * 证件号码
	 */
	private String certNo;

	/**
	 * 地区号
	 */
	private String brNo;

	/**
	 * 机构号
	 */
	private String subBrNo;

	/**
	 * 客户姓名
	 */
	private String custName;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getSubBrNo() {
		return subBrNo;
	}
	public void setSubBrNo(String subBrNo) {
		this.subBrNo = subBrNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

}
