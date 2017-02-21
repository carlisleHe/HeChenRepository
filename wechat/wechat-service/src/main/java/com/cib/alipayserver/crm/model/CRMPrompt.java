package com.cib.alipayserver.crm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @Description: 用户营销关系
 * @ClassName:CRMPrompt 
 * @author:xuzz
 * @date:2015-2-9
 */
@Entity
@Table(name = "T_CRM_PROMPT")
public class CRMPrompt {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	/**
	 * crm系统渠道ID
	 */
	@Column(name = "crm_channel")
	private String crmChannel;
	/**
	 * 操作类型
	 */
	@Column(name = "biz_type")
	private String bizType;
	/**
	 * 推荐人ID
	 */
	@Column(name = "prompt_id")
	private String promptId;
	/**
	 * 客户姓名
	 */
	@Column(name = "cust_name")
	private String custName;
	/**
	 * 账号
	 */
	@Column(name = "acct_no")
	private String acctNo;
	/**
	 * 地区代号
	 */
	@Column(name = "br_no")
	private String brNo;
	/**
	 * 机构代号
	 */
	@Column(name = "sub_br")
	private String subBr;
	/**
	 * 业务号
	 */
	@Column(name = "biz_no")
	private String bizNo;
	/**
	 * 账号类型
	 */
	@Column(name = "acct_type")
	private String acctType;
	/**
	 * 事件时间
	 */
	@Column(name = "event_time")
	private Date eventTime;
	/**
	 * 描述
	 */
	@Column(name = "desc")
	private String desc;
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCrmChannel() {
		return crmChannel;
	}
	public void setCrmChannel(String crmChannel) {
		this.crmChannel = crmChannel;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getPromptId() {
		return promptId;
	}
	public void setPromptId(String promptId) {
		this.promptId = promptId;
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
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getSubBr() {
		return subBr;
	}
	public void setSubBr(String subBr) {
		this.subBr = subBr;
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
}
