package com.newland.alipayService.customer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newland.alipayService.card.model.BindCard;




/***
 * 
 * @ClassName: Customer  
 * @Description: 用户授权信息 
 * @author: xuzz hongye
 * @date:2014-3-17 下午04:47:29
 */
@Entity
@Table(name = "T_ALIPAY_CUSTOMER")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6122358772865573384L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 授权标示
	 */
	@Id
	@Column(name = "cust_id")
	private String custId;
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
	 * 默认借记卡
	 */
	@Column(name = "dc_card")
	private String defaultDCCard;
	/**
	 * 默认信用卡
	 */
	@Column(name = "cc_card")
	private String defaultCCCard;
	
	/**
	 * 有效状态
	 */
	@Column(name = "valid")
	private String valid;


	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;
	
	/**
	 * 创建时间
	 */
	@Column(name="cre_date")
	private Date creDate;
	
	/**
	 * 授权卡信息
	 */
	@Transient
	private List<BindCard> authCards = new ArrayList<BindCard>();
	

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
	public String getDefaultDCCard() {
		return defaultDCCard;
	}
	public void setDefaultDCCard(String defaultDCCard) {
		this.defaultDCCard = defaultDCCard;
	}
	public String getDefaultCCCard() {
		return defaultCCCard;
	}
	public void setDefaultCCCard(String defaultCCCard) {
		this.defaultCCCard = defaultCCCard;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	public List<BindCard> getAuthCards() {
		return authCards;
	}
	public void setAuthCards(List<BindCard> authCards) {
		this.authCards = authCards;
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
	public Date getCreDate() {
		return creDate;
	}
	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}
	

}
