package com.newland.alipayService.account.service.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.intensoft.coresyst.exception.CoreNativeException;
import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.newland.alipayService.account.model.CreditCardBill;
import com.newland.alipayService.account.model.CreditcardAccount;
import com.newland.alipayService.account.model.FinanceAccount;
import com.newland.alipayService.account.model.FinanceAccountTransDetail;
import com.newland.alipayService.account.model.UnSettleBill;
import com.newland.alipayService.account.service.AccountService;
import com.newland.alipayService.account.service.QueryOpenApi;
import com.newland.base.common.AppExCode;
import com.newland.base.util.ConvertUtil;
/**
 * 
 * @ClassName: QueryOpenApiImpl  
 * @Description: 账户查询类对外开放接口实现类 
 * @author: xuzz 
 * @date:2014-3-24 上午11:18:48
 */
@Service("queryOpenApi")
public class QueryOpenApiImpl implements QueryOpenApi{
	@Resource(name = "accountService")
	private AccountService accountService;

	@Override
	public FinanceAccount queryBalance(String acctNo) {
		//1、条件判断
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "必输要素不能为空！");
		}
		//2、查询理财卡的余额，包括E卡
		FinanceAccount financeAccount = new FinanceAccount();
		financeAccount.setAccountNo(acctNo);
		try{
			return accountService.queryBalance(financeAccount);
		}
		catch(AppBizException e){
			throw new AppRTException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
	}

	@Override
	public FinanceAccountTransDetail queryDetail(String acctNo) throws AppBizException{
		//1、条件判断
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "必输要素不能为空！");
		}
		//2、查询理财卡的交易明细，包括E卡
		FinanceAccount financeAccount = new FinanceAccount();
		financeAccount.setAccountNo(acctNo);
		try{
			return accountService.queryDetail(financeAccount);
		}
		catch(AppBizException e){
			if(e instanceof CoreNativeException){
				if(((CoreNativeException)e).getNativeCode()== 4606){
					throw new AppBizException(AppExCode.DC_DETAILS_NOT_FOUND, "借记卡交易明细不存在！");
				}
			}
			throw new AppRTException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
		
	}

	@Override
	public CreditcardAccount queryLimit(String acctNo) {
		//1、条件判断
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "必输要素不能为空！");
		}
		//2、查询信用卡额度
		try{
			return accountService.queryLimit(acctNo);
		}
		catch(AppBizException e){
			throw new AppRTException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
		
	}

	@Override
	public CreditCardBill queryBill(String acctNo,String billDate,String currency) throws AppBizException{
		//1、条件判断
		if(StringUtils.isBlank(billDate)||StringUtils.isBlank(currency)||StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "必输要素不能为空！");
		}
		//2、查询信用卡账单
		CreditCardBill creditCardBill = new CreditCardBill();
		creditCardBill.setAcctNo(acctNo);
		try{
			Date dbillDate = ConvertUtil.stringToDate(billDate, "yyyyMM");
			creditCardBill.setBillDate(ConvertUtil.stringToDate(billDate, "yyyyMM"));
			creditCardBill.setCurrency(currency);
			creditCardBill.setBillDate(dbillDate);
			return accountService.queryBill(creditCardBill);
		}
		catch(ParseException e){
			throw new AppRTException(AppExCode.CHECK_ERROR, "账单年月格式输入错误！");
		}
		catch(AppBizException e){
			if(e instanceof CoreNativeException){
				if(((CoreNativeException)e).getNativeCode()== 7105){
					throw new AppBizException(AppExCode.CC_BILL_NOT_FOUND, "信用卡账单不存在！");
				}
			}
			throw new AppRTException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
	}

	@Override
	public UnSettleBill queryUnsettleBill(String acctNo, String currency) throws AppBizException{
		//1、条件判断
		if(StringUtils.isBlank(currency)||StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "必输要素不能为空！");
		}
		//2、查询信用卡账单
		UnSettleBill unSettleBill = new UnSettleBill();
		unSettleBill.setAcctNo(acctNo);
		unSettleBill.setCurrency(currency);
		try{
			return accountService.queryUnsettleBill(unSettleBill);
		}
		catch(AppBizException e){
			if(e instanceof CoreNativeException){
				if(((CoreNativeException)e).getNativeCode()== 7223){
					throw new AppBizException(AppExCode.CC_UNSETTLE_BILL_NOT_FOUND, "信用卡未出账单不存在！");
				}
			}
			throw new AppRTException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
		
	}

}
