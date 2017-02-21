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
import javax.persistence.Transient;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.intensoft.formater.BigDecimalFormatter;
import com.intensoft.util.BeanUtils;

@Entity
@Table(name = "t_core_notify_history")
public class CoreNotifyHistory implements Serializable {

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

	public CoreNotifyHistory() {

	}

	public CoreNotifyHistory(CoreNotify notify) {
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
	 * 用户姓名
	 */
	@Column(name = "cust_name")
	private String custName;

	/**
	 * 卡号
	 */
	@Column(name = "acct_no")
	private String acctNo;

	/**
	 * 卡类型
	 */
	@Column(name = "acct_type")
	private String acctType;

	/**
	 * 交易日期
	 */
	@Transient
	private Date transDate;

	/**
	 * 交易时间(HHmmss)
	 */
	@Transient
	private String transTime;

	/**
	 * 交易地区
	 */
	@Column(name = "dqdh")
	private String br;

	/**
	 * 交易地区
	 */
	@Column(name = "jgdh")
	private String subBr;

	/**
	 * 交易代码
	 */
	@Column(name = "jydm")
	private String coreTransNo;

	/**
	 * 记账交易代码
	 */
	@Column(name = "jzjydm")
	private String acctTransNo;

	/**
	 * 交易柜员
	 */
	@Column(name = "jygy")
	private String teller;

	/**
	 * 交易柜员流水号
	 */
	@Column(name = "gylsh")
	private String tellerSno;

	/**
	 * 渠道种类
	 */
	@Column(name = "qdzl")
	private String channel;

	/**
	 * 凭证代号
	 */
	@Column(name = "pzdh")
	private String invoiceNo;

	/**
	 * 凭证代号
	 */
	@Column(name = "pzqfrq")
	private Date invoiceDate;

	/**
	 * 内部账户代号
	 */
	@Column(name = "zhdh")
	private String innerAcctNo;

	/**
	 * 账户存储
	 */
	@Column(name = "zhcc")
	private String acctStorage;

	/**
	 * 开户地区
	 */
	@Column(name = "khdqdh")
	private String acctBr;

	/**
	 * 开户机构代号
	 */
	@Column(name = "khjgdh")
	private String acctSubBr;

	/**
	 * 账户余额
	 */
	@Column(name = "zhye")
	private BigDecimal balance;

	/**
	 * 交易金额
	 */
	@Column(name = "jyje")
	private BigDecimal transAmount;

	/**
	 * 交易金额
	 */
	@Column(name = "hbzl")
	private String currencyCode;

	/**
	 * 现转标志
	 */
	@Column(name = "xzbz")
	private String cashFlag;

	/**
	 * 借贷标记
	 */
	@Column(name = "jdbj")
	private String debitFlag;

	/**
	 * 冲补标志
	 */
	@Column(name = "cbbz")
	private String cbbz;

	/**
	 * 被冲账标志
	 */
	@Column(name = "bczbz")
	private String bczbz;

	/**
	 * 业务参考号
	 */
	@Column(name = "ywckh")
	private String busRefNo;

	/**
	 * 摘要代号
	 */
	@Column(name = "zydh")
	private String summaryCode;


	/**
	 * 交易时间
	 */
	@Column(name = "trans_time")
	private Date transDateTime;

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


	public Date getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(Date transDateTime) {
		this.transDateTime = transDateTime;
	}

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}


	public String getSummaryCode() {
		return summaryCode;
	}

	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
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

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getBr() {
		return br;
	}

	public void setBr(String br) {
		this.br = br;
	}

	public String getSubBr() {
		return subBr;
	}

	public void setSubBr(String subBr) {
		this.subBr = subBr;
	}

	public String getAcctTransNo() {
		return acctTransNo;
	}

	public void setAcctTransNo(String acctTransNo) {
		this.acctTransNo = acctTransNo;
	}

	public String getTeller() {
		return teller;
	}

	public void setTeller(String teller) {
		this.teller = teller;
	}

	public String getTellerSno() {
		return tellerSno;
	}

	public void setTellerSno(String tellerSno) {
		this.tellerSno = tellerSno;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInnerAcctNo() {
		return innerAcctNo;
	}

	public void setInnerAcctNo(String innerAcctNo) {
		this.innerAcctNo = innerAcctNo;
	}

	public String getAcctStorage() {
		return acctStorage;
	}

	public void setAcctStorage(String acctStorage) {
		this.acctStorage = acctStorage;
	}

	public String getAcctBr() {
		return acctBr;
	}

	public void setAcctBr(String acctBr) {
		this.acctBr = acctBr;
	}

	public String getAcctSubBr() {
		return acctSubBr;
	}

	public void setAcctSubBr(String acctSubBr) {
		this.acctSubBr = acctSubBr;
	}

	public String getCashFlag() {
		return cashFlag;
	}

	public void setCashFlag(String cashFlag) {
		this.cashFlag = cashFlag;
	}

	public String getDebitFlag() {
		return debitFlag;
	}

	public void setDebitFlag(String debitFlag) {
		this.debitFlag = debitFlag;
	}

	public String getCbbz() {
		return cbbz;
	}

	public void setCbbz(String cbbz) {
		this.cbbz = cbbz;
	}

	public String getBczbz() {
		return bczbz;
	}

	public void setBczbz(String bczbz) {
		this.bczbz = bczbz;
	}

	public String getBusRefNo() {
		return busRefNo;
	}

	public void setBusRefNo(String busRefNo) {
		this.busRefNo = busRefNo;
	}

	public String getCoreTransNo() {
		return coreTransNo;
	}

	public void setCoreTransNo(String coreTransNo) {
		this.coreTransNo = coreTransNo;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
