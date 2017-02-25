 /**
 * @Title: AuthCardView.java
 * @Package com.cib.weixinserver.web.view
 * @Description: 显示卡信息
 * @author hongye
 * @date 2014-3-21 10:09:39
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.alipayserver.web.view;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.newland.base.common.Const;
import com.newland.base.util.AccountUtil;

/**
* @ClassName: AuthCardView
* @Description:显示卡信息 只用于页面展示
* @author hongye
* @date 2014-3-26 16:09:39
*/ 
public class AuthCardView {
	
	/**
	 * 唯一ID
	 */
	private String authCardId;
	/**
	 * 用户授权ID
	 */
	private String authId;
	/**
	 * 应用标示ID
	 */
	private String appId;
	
	/**
	 * 应用用户ID
	 */
	private String clientId;
	/**
	 * 签约账号
	 */
	private String acctNo;
	
	/**
	 * 地区号
	 */
	private String brNo;

	/**
	 * 机构号
	 */
	private String subBrNo;

	/**
	 * 签约账号名
	 */
	private String acctName;
	
	/**
	 * 通知类型
	 */
	private String notifyType;
	
	//输入时格式化后的账号;
	private String acctNoInput;
	
	/**
	 * 账号显示
	 */
	private String acctNoView;
	
	/**
	 * 账号后四位
	 */
	private String acctNoSub;
	
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 账号类型
	 */
	private String acctType;
	/**
	 * 签约状态
	 */
	private String valid;
	/**
	 * 客户姓名
	 */
	private String custName;
	/**
	 * 证件类型
	 */
	private String certType;
	/**
	 * 用户输入的证件号码
	 */
	private String certNo;
	
	/**
	 * 核心返回的证件号码
	 */
	private String txnCertNo;
	/**
	 * 创建日期
	 */
	private Date creDate;
	/**
	 * 更新日期
	 */
	private Date updDate;
	
	/**
	 * 是否默认借记卡
	 */
	private boolean defaultCard = false;
	
	/**
	 * 理财卡账户余额
	 */
	private BigDecimal balance;
	
	/**
	 * 信用卡截止还款日期
	 */
	private Date endRefundDate;
	
	
	//isoCode.add("CNY");	cibCode.add("01");	//人民币
	//isoCode.add("EUR"); cibCode.add("11");	// 欧元
	///isoCode.add("GBP"); cibCode.add("12");	// 英镑
	//isoCode.add("HKD"); cibCode.add("13");	// 港币
	//isoCode.add("USD"); cibCode.add("14");	// 美元	
	/**
	 * 本期人民币账单金额
	 */
	private BigDecimal billAmountCNY;
	
	/**
	 * 本期美元账单金额
	 */
	private BigDecimal billAmountUSD;
	/**
	 * 推广人ID
	 */
	private String promptId;
	
	//格式化账号
	public static String formate(String bankAccount,String formateString) {
		//String formateString ="###-######-###";
		if(StringUtils.isBlank(bankAccount)){
			return "";
		}
		if(StringUtils.isBlank(formateString)){
			formateString ="######-######-######";
		}
        
		bankAccount = bankAccount.trim();
			// 格式字符串中的数字个数,
			int formateStringDigitNum = formateString.replace("-", "").length();

			// 原字符串过长，截取后面部分
			if (bankAccount.length() > formateStringDigitNum) {
				bankAccount = bankAccount
						.substring(bankAccount.length()
								- formateStringDigitNum);
			}

			// 原字符串过短，前面补*
			else if (bankAccount.length() < formateStringDigitNum) {
				String pre = "";
				for (int i = 0; i < formateStringDigitNum
						- bankAccount.length(); i++)
					pre += "*";
				bankAccount = pre + bankAccount;
			}

			// 格式化字符串中分隔符位置
			int delimiterPosition = 0;
			String temp = bankAccount;
			while (-1 != (delimiterPosition = formateString.indexOf('-',
					delimiterPosition))) {
				// 添加分割符号
				temp = temp.substring(0, delimiterPosition) + ' '
						+ temp.substring(delimiterPosition);
				delimiterPosition++;
			}
			
		return temp;
	}

	public String getAcctNoInput() {
		return acctNoInput;
	}





	public void setAcctNoInput(String acctNoInput) {
		this.acctNoInput = acctNoInput;
	}





	public String getAuthCardId() {
		return authCardId;
	}

	public void setAuthCardId(String authCardId) {
		this.authCardId = authCardId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctNoSub() {
		return AccountUtil.calcMaskAcctNo(this.getAcctNo());
	}

	public void setAcctNoSub(String acctNoSub) {
		this.acctNoSub = acctNoSub;
	}

	public String getAcctNoView() {
		//StringUtils.overlay(this.getAcctNo(), "****", 3, 7);//手机号屏蔽4到7位
		String	formateString ="######-######-######";
		
		if(this.getAcctNo().startsWith("90592")&&this.getAcctNo().length()==16){
			formateString ="#####-#####-######";
		}
		
		//信用卡
		if(this.getAcctType().equals(Const.ACCT_TYPE_CREDIT)){
			formateString ="####-####-####-####";
		}
		return  formate(AccountUtil.calcMaskAcctNo(this.getAcctNo()),formateString);
	}

	public void setAcctNoView(String acctNoView) {
		this.acctNoView = acctNoView;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
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

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public boolean isDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(boolean defaultCard) {
		this.defaultCard = defaultCard;
	}

	public String getTxnCertNo() {
		return txnCertNo;
	}

	public void setTxnCertNo(String txnCertNo) {
		this.txnCertNo = txnCertNo;
	}

	public Date getEndRefundDate() {
		return endRefundDate;
	}

	public void setEndRefundDate(Date endRefundDate) {
		this.endRefundDate = endRefundDate;
	}

	public BigDecimal getBillAmountCNY() {
		return billAmountCNY;
	}

	public void setBillAmountCNY(BigDecimal billAmountCNY) {
		this.billAmountCNY = billAmountCNY;
	}

	public BigDecimal getBillAmountUSD() {
		return billAmountUSD;
	}

	public void setBillAmountUSD(BigDecimal billAmountUSD) {
		this.billAmountUSD = billAmountUSD;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
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

	public String getPromptId() {
		return promptId;
	}

	public void setPromptId(String promptId) {
		this.promptId = promptId;
	}

}
