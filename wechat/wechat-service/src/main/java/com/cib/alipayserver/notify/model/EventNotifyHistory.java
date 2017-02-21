package com.cib.alipayserver.notify.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.intensoft.formater.BigDecimalFormatter;
import com.intensoft.util.BeanUtils;

@Entity
@Table(name = "t_event_notify_history")
public class EventNotifyHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337607011215796130L;

	public static SimpleDateFormat inputFormat = new SimpleDateFormat(
			"yyyy-MM-dd HHmmss");

	public static BigDecimalFormatter decimalFormatter = new BigDecimalFormatter();

	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
	}

	public EventNotifyHistory() {

	}

	public EventNotifyHistory(EventNotify notify) {
		try {
			BeanUtils.copyProperties(this, notify);
			// 将msg_id重置为null
			this.setMsgId(null);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
	}

	/**
	 * 消息ID
	 */
	@Id
	@Column(name = "msg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer msgId;

	/**
	 * 开放互联应用ID
	 */
	@Column(name = "app_id")
	private String appId;

	/**
	 * 应用内用户ID
	 */
	@Column(name = "app_user_id")
	private String appUserId;

	/**
	 * 应用内用户ID
	 */
	@Column(name = "event_type")
	private String eventType;

	/**
	 * 用户姓名
	 */
	@Column(name = "cust_name")
	private String custName;

	/**
	 * 卡号
	 */
	@Column(name = "cert_no")
	private String certNo;

	/**
	 * 证件类型
	 */
	@Column(name = "cert_type")
	private String certType;

	/**
	 * 卡号
	 */
	@Column(name = "acct_no")
	private String acctNo;

	/**
	 * 核心客户号
	 */
	@Column(name = "ctsp_no")
	private String ctspNo;

	/**
	 * 相关（产品）名称
	 */
	@Column(name = "relate_name")
	private String relateName;

	/**
	 * 交易金额
	 */
	@Column(name = "amount")
	private BigDecimal amount;

	/**
	 * 交易金额
	 */
	@Column(name = "currency")
	private String currencyCode;

	/**
	 * 附加信息
	 */
	@Column(name = "addition")
	private String addition;

	/**
	 * 交易日期
	 */
	@Column(name = "event_time")
	private Date eventTime;

	/**
	 * 发送结果
	 */
	@Column(name = "result")
	private String result;

	/**
	 * 消息发送时间
	 */
	@Column(name = "upd_time")
	private Date updateTime = new Date();

	/**
	 * 备注（失败原因）
	 */
	@Column(name = "remark")
	private String remark;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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


	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCtspNo() {
		return ctspNo;
	}

	public void setCtspNo(String ctspNo) {
		this.ctspNo = ctspNo;
	}

	public String getRelateName() {
		return relateName;
	}

	public void setRelateName(String relateName) {
		this.relateName = relateName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
