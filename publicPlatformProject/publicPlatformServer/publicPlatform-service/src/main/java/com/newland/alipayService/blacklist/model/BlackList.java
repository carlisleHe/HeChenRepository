package com.newland.alipayService.blacklist.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @ClassName: Blacklist
 * @Description: 黑名单(签约类)
 * @author Maple
 */
@Entity
@Table(name = "T_ALIPAY_BLACKLIST")
public class BlackList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2794768864004985407L;
	/**
	 * id
	 */
	@Id
	@Column(name = "black_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer blackId;
	/** 应用ID */
	@Column(name = "app_id")
	private String appId;

	/**
	 * 应用用户名
	 */
	@Column(name = "app_user_id")
	private String appUserId;
	/**
	 * 交易日期
	 */
	@Column(name = "trans_date")
	private Date transDate;
	/**
	 * 身份验证错误次数
	 */
	@Column(name = "apply_err")
	private int applyErr;
	/**
	 * 黑名单类型
	 */
	@Column(name = "black_type")
	private String blackType;
	public Integer getBlackId() {
		return blackId;
	}
	public void setBlackId(Integer blackId) {
		this.blackId = blackId;
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
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public int getApplyErr() {
		return applyErr;
	}
	public void setApplyErr(int applyErr) {
		this.applyErr = applyErr;
	}
	public String getBlackType() {
		return blackType;
	}
	public void setBlackType(String blackType) {
		this.blackType = blackType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
