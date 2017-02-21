package com.cib.alipayserver.notify.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cib.alipayserver.util.AccountUtil;
import com.intensoft.formater.BigDecimalFormatter;


@Entity
@Table(name = "t_event_notify_pendding")
public class EventNotify implements Serializable, OpenbankMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861101724896490241L;

	public static SimpleDateFormat inputFormat = new SimpleDateFormat("HHmmss");

	public static BigDecimalFormatter decimalFormatter = new BigDecimalFormatter();

	/**
	 * 消息ID
	 */
	@Id
	@Column(name = "msg_id")
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

	@Transient
	private Currency currency;

	@Transient
	private String result;

	@Transient
	private String remark;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Currency getCurrency() {
		return currency;
	}

	public String getCurrencyName() {
		if (currency != null) {
			return currency.getCurrencyName();
		} else {
			return "";
		}
	}

	public String getCurrencyUnit() {
		if (currency != null) {
			return currency.getCurrencyUnit();
		} else {
			return "元";
		}
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	@Override
	public String getMessageType() {
		if (StringUtils.isNotEmpty(eventType)) {
			return "E" + this.eventType;
		} else {
			return null;
		}
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
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
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

	public String getResult() {
		return result;
	}

	@Override
	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getAccountNo() {
		return this.acctNo;
	}

	@Transient
	private Map<String, Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public Map<String, Object> toParameterMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ownerName", this.getCustName());
		// 需要进行尾号转换
		map.put("accountNo", AccountUtil.calcMaskAcctNo(this.getAcctNo()));
		map.put("clientId", this.getAppUserId());
		map.put("eventType", this.getEventType());
		map.put("product", this.getRelateName());
		map.put("time", this.getEventTime());

		map.put("money", this.getAmount());
		map.put("currencyName", this.getCurrencyName());
		map.put("currencyUnit", this.getCurrencyUnit());
		map.put("addition", this.getAddition());
		return map;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
