package com.cib.alipayserver.card.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cib.alipayserver.common.Const;
/**
 * 
 * @ClassName: AutoCard  
 * @Description: 卡授权信息
 * @author: dvlp 
 * @date:2014-3-17 下午03:40:22
 */
@Entity
@Table(name = "T_ALIPAY_BIND_CARD")
public class BindCard implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4297037302224279268L;

	public BindCard() {
		this.prodCode = Const.PROD_CODE;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 唯一ID
	 */
	@Id
	@Column(name = "bind_id")
	private String bindId;
	/**
	 * 用户授权ID
	 */
	@Column(name="cust_id")
	private String custId;
	/**
	 * 微信公众号
	 */
	@Column(name = "app_id")
	private String appId;

	/**
	 * 应用用户ID
	 */
	@Column(name = "open_id")
	private String openId;

	/**
	 * 地区号
	 */
	@Column(name = "br_no")
	private String brNo;

	/**
	 * 机构号
	 */
	@Column(name = "sub_br")
	private String subBrNo;

	/**
	 * 签约账号
	 */
	@Column(name = "acct_no")
	private String acctNo;
	/**
	 * 行内卡号
	 */
	@Column(name = "cib_acct_no")
	private String cibAcctNo;
	/**
	 * 手机号
	 */
	@Column(name = "mobile")
	private String mobile;
	/**
	 * 账号类型
	 */
	@Column(name = "acct_type")
	private String acctType;
	/**
	 * 签约状态
	 */
	@Column(name = "valid")
	private String valid;
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
	 * 创建日期
	 */
	@Column(name = "cre_date")
	private Date creDate;
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;
	
	/**
	 * 产品代码
	 */
	@Column(name = "prod_code")
	private String prodCode;

	
	public String getBindId() {
		return bindId;
	}
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
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
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public String getCibAcctNo() {
		return cibAcctNo;
	}
	public void setCibAcctNo(String cibAcctNo) {
		this.cibAcctNo = cibAcctNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
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
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
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
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

}
