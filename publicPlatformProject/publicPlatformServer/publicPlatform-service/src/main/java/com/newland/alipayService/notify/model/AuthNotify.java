package com.newland.alipayService.notify.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.newland.base.common.Const;

/**
 * 
 * @ClassName: AuthNofify  
 * @Description: 授权通知信息 
 * @author: xuzz 
 * @date:2014-3-19 下午02:00:36
 */
@Entity
@Table(name = "T_ALIPAY_AUTH_NOTIFY")
public class AuthNotify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6586871392574756229L;

	public final static String VALID = "1";

	public final static String INVALID = "0";
	/**
	 * 授权通知ID
	 */
	@Id
	@Column(name = "notify_id")
	private String notifyId;
	/**
	 * 应用ID
	 */
	@Column(name = "app_id")
	private String appId;
	/**
	 * 客户应用ID
	 */
	@Column(name = "app_user_id")
	private String appUserId;

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
	 * 客户姓名
	 */
	@Column(name = "cust_name")
	private String custName;

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
	 * 核心客户号
	 */
	@Column(name = "ctsp_no")
	private String ctspNo;
	/**
	 * 账号类型 0-所有卡 1-借记卡 2-信用卡
	 */
	@Column(name = "acct_type")
	private String acctType;
	/**
	 * 通知类型 00-动户通知
	 */
	@Column(name = "notify_type")
	private String notifyType;
	/**
	 * 有效状态
	 */
	@Column(name = "valid")
	private String valid;
	/**
	 * 创建时间
	 */
	@Column(name = "cre_date")
	private Date creDate;
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;

	@Transient
	private String cacheKey;


	public String getCacheKey() {
		if (StringUtils.isNotEmpty(cacheKey)) {
			return cacheKey;
		}
		if (Const.ACCT_TYPE_DEBIT.equals(this.getAcctType())) {
			// 借记卡使用ctsp_no作为key的组成部分
			cacheKey = this.getAppId() + this.getNotifyType()
					+ this.getAcctType()
					+ this.getCtspNo();
		} else {
			// 信用卡使用acct_no作为key的组成部分
			cacheKey = this.getAppId() + this.getNotifyType()
					+ this.getAcctType() + this.getAcctNo();
		}
		return cacheKey;
	}


	public String getNotifyId() {
		return notifyId;
	}


	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getAppUserId() {
		return appUserId;
	}


	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}


	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}


	public String getAcctNo() {
		return acctNo;
	}


	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}


	public String getCtspNo() {
		return ctspNo;
	}


	public void setCtspNo(String ctspNo) {
		this.ctspNo = ctspNo;
	}


	public String getAcctType() {
		return acctType;
	}


	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public String getValid() {
		return valid;
	}


	public void setValid(String valid) {
		this.valid = valid;
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


	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
