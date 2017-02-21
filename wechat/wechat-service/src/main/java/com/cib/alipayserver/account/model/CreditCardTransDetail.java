package com.cib.alipayserver.account.model;

import java.math.BigDecimal;
import java.util.Date;

import com.cib.alipayserver.parse.CompoundParsePattern;
import com.cib.alipayserver.parse.formatter.LongBigDecimalFormatter;
import com.intensoft.file.text.annotation.DataColumn;
import com.intensoft.file.text.annotation.FileEntity;
import com.intensoft.formater.DateFormatter;
/**
 * 信用卡交易明细
 * 
 * @ClassName: CreditCardTransDetail  
 * @Description: 
 * @author: xuzz 
 * @date:2014-5-9
 */
@FileEntity(charset="GBK",delimiter="",fields={"monthSNO","acctDate","consumeTime","transSNO","transType","transAmount","transAmountSign","others","reverseFlag","retain"})
@CompoundParsePattern(charset = "GBK", delimiterPosition = -1, isFixLength = true, mappedModelNames = {
		":com.cib.alipayserver.account.model.CreditCardTransDetail"
}, skipRegex = "BEGIN|END|begin|end|[\\s]*|", addHead = "BEGIN", addTail = "END")
public class CreditCardTransDetail {

	/**
	 * 月份序号
	 */
	@DataColumn(name = "月份序号",fixLength=3)
	private String monthSNO;
	/**
	 * 入账日期
	 */
	@DataColumn(name = "入账日期",fixLength=8,formatter=DateFormatter.class,pattern="yyyyMMdd")
	private Date acctDate;
	/**
	 * 消费日期
	 */
	@DataColumn(name = "消费日期",fixLength=16,formatter=DateFormatter.class,pattern="yyyyMMddHHmmssSS")
	private Date consumeTime;
	/**
	 * 交易流水号
	 */
	@DataColumn(name = "交易流水号",fixLength=6)
	private String transSNO;
	/**
	 * 交易类型
	 */
	@DataColumn(name = "交易类型",fixLength=4)
	private String transType;
	/**
	 * 交易金额
	 */
	@DataColumn(name = "交易类型",fixLength=12,formatter=LongBigDecimalFormatter.class)
	private BigDecimal transAmount;
	/**
	 * 金额符号
	 */
	@DataColumn(name = "金额符号",fixLength=1)
	private String transAmountSign;
	/**
	 * 其它字段集合
	 */
	@DataColumn(name = "其它字段集合",fixLength=85)
	private String others;
	/**
	 * 冲正标志，0未冲正；1-已冲正
	 */
	@DataColumn(name = "冲正标志",fixLength=1)
	private String reverseFlag;
	/**
	 * 保留
	 */
	@DataColumn(name = "保留",fixLength=5)
	private String retain;

	public String getMonthSNO() {
		return monthSNO;
	}
	public void setMonthSNO(String monthSNO) {
		this.monthSNO = monthSNO;
	}
	public Date getAcctDate() {
		return acctDate;
	}
	public void setAcctDate(Date acctDate) {
		this.acctDate = acctDate;
	}
	public Date getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getTransSNO() {
		return transSNO;
	}
	public void setTransSNO(String transSNO) {
		this.transSNO = transSNO;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransAmountSign() {
		return transAmountSign;
	}
	public void setTransAmountSign(String transAmountSign) {
		this.transAmountSign = transAmountSign;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getReverseFlag() {
		return reverseFlag;
	}
	public void setReverseFlag(String reverseFlag) {
		this.reverseFlag = reverseFlag;
	}
	public String getRetain() {
		return retain;
	}
	public void setRetain(String retain) {
		this.retain = retain;
	}

	
}
