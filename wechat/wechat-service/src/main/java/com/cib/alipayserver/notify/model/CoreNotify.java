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

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cib.alipayserver.common.Const;
import com.cib.alipayserver.util.AccountUtil;
import com.intensoft.formater.BigDecimalFormatter;
import com.intensoft.util.DateUtils;

@Entity
@Table(name = "t_core_notify_pendding")
public class CoreNotify implements Serializable, OpenbankMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337607011215796130L;

	public static SimpleDateFormat inputFormat = new SimpleDateFormat("HHmmss");

	public static BigDecimalFormatter decimalFormatter = new BigDecimalFormatter();

	/** 贷方标志 */
	private static final String FLAG_CREDIT = "1";
	/** 借方标志 */
	private static final String FLAG_DEBIT = "0";

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
	@Column(name = "jyrq")
	private Date transDate;

	/**
	 * 交易时间(HHmmss)
	 */
	@Column(name = "jysj")
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

	@Transient
	private Currency currency;

	@Transient
	private String summary;

	@Transient
	private Date transDateTime;

	@Transient
	private String result;

	@Transient
	private String remark;



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

	public String getSummaryCode() {
		return summaryCode;
	}

	public void setSummaryCode(String summaryCode) {
		this.summaryCode = summaryCode;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}


	@Override
	public String getMessageType() {
		if (Const.ACCT_TYPE_CREDIT.equals(this.acctType)) {
			return Const.MESSAGE_TYPE_CREDIT;
		} else {
			return Const.MESSAGE_TYPE_BALANCE;
		}
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

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public Date getTransDateTime() {
		if(transDateTime!=null){
			return transDateTime;
		}else{
			try {
				return transDateTime = DateUtils.composeDateTime(transDate,
						inputFormat.parse(transTime));
			} catch (Exception e) {
				return transDateTime = transDate;
			}
		}
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
		map.put("messageType", this.getMessageType());
		map.put("transDate", this.getTransDateTime());
		map.put("businessName", this.getSummary());
		if (Const.ACCT_TYPE_DEBIT.equals(this.acctType)
				&& this.getTransAmount() != null
				&& this.getTransAmount().compareTo(BigDecimal.ZERO) > 0) {
			map.put("transType",
					this.getSummary()
							+ (FLAG_DEBIT.equals(this.getDebitFlag()) ? "支出"
									: "收入"));
		} else {
			map.put("transType", this.getSummary());
		}
		map.put("symbol", "");
		map.put("transAmount", this.getTransAmount());
		map.put("currencyName", this.getCurrencyName());
		map.put("currencyUnit", this.getCurrencyUnit());
		map.put("balance", this.getBalance());
		return map;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
