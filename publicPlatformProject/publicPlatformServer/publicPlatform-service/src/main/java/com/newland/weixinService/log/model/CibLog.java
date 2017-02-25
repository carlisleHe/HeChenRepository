package com.newland.weixinService.log.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * @ClassName: AuthLog  
 * @Description: 授权日志表 
 * @author: dvlp 
 * @date:2014-3-18 上午10:47:09
 */
@Entity
@Table(name = "T_CIB_LOG")
public class CibLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8343162625404354663L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 微信公众号
	 */
	@Column(name = "app_id")
	private String appId;
	/**
	 * 应用客户ID
	 */
	@Column(name = "open_id")
	private String openId;
	/**
	 * 认证类型
	 */
	@Column(name = "auth_type")
	private String authType;
	/**
	 * 客户姓名
	 */
	@Column(name = "cust_name")
	private String custName;
	/**
	 * 证件类型
	 */
	@Column(name = "cert_type")
	private String certType;
	/**
	 * 证件号码
	 */
	@Column(name = "cert_no")
	private String certNo;
	/**
	 * 签约账号
	 */
	@Column(name = "acct_no")
	private String acctNo;
	/**
	 * 账号类型
	 */
	@Column(name = "acct_type")
	private String acctType;
	/**
	 * 手机号
	 */
	@Column(name = "mobile")
	private String mobile;

	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;
	
	/**
	 * 流水号
	 */
	@Id
	@Column(name = "sno")
	private String sno;
	/**
	 * 日志日期
	 */
	@Column(name = "log_date")
	private Date logDate;
	/**
	 * 日志时间
	 */
	@Column(name = "log_time")
	private Date logTime;
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
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
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
