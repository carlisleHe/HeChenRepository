package com.newland.alipayService.account.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.newland.alipayService.account.model.AcctInfo;
import com.newland.alipayService.account.model.CreditCardBill;
import com.newland.alipayService.account.model.CreditCardLimit;
import com.newland.alipayService.account.model.CreditCardTransDetail;
import com.newland.alipayService.account.model.CreditcardAccount;
import com.newland.alipayService.account.model.FinanceAccount;
import com.newland.alipayService.account.model.FinanceAccountTransDetail;
import com.newland.alipayService.account.model.InnerAccount;
import com.newland.alipayService.account.model.TransDetail;
import com.newland.alipayService.account.model.UnSettleBill;
import com.newland.alipayService.account.service.AccountService;
import com.newland.alipayService.common.encrypt.HsmAdapter;
import com.newland.alipayService.parse.CompoundParsePattern;
import com.newland.alipayService.parse.ICompoundParse;
import com.newland.alipayService.txn.outgoing.Tr2569;
import com.newland.alipayService.txn.outgoing.Tr2560.Tr2560Request;
import com.newland.alipayService.txn.outgoing.Tr2560.Tr2560Response;
import com.newland.alipayService.txn.outgoing.Tr2569.Tr2569Request;
import com.newland.alipayService.txn.outgoing.Tr2569.Tr2569Response;
import com.newland.alipayService.txn.outgoing.Tr2569.Tr2569Response.TrJYMXResponse;
import com.newland.alipayService.txn.outgoing.Tr3422.Tr3422Request;
import com.newland.alipayService.txn.outgoing.Tr3422.Tr3422Response;
import com.newland.alipayService.txn.outgoing.Tr3425.Tr3425Request;
import com.newland.alipayService.txn.outgoing.Tr3425.Tr3425Response;
import com.newland.alipayService.txn.outgoing.Tr3431.Tr3431Request;
import com.newland.alipayService.txn.outgoing.Tr3446.Tr3446Request;
import com.newland.alipayService.txn.outgoing.Tr3446.Tr3446Response;
import com.newland.alipayService.txn.outgoing.Tr3502.Tr3502Request;
import com.newland.alipayService.txn.outgoing.Tr3502.Tr3502Response;
import com.newland.alipayService.txn.outgoing.Tr3873.Tr3873Request;
import com.newland.alipayService.txn.outgoing.Tr3873.Tr3873Response;
import com.newland.alipayService.txn.outgoing.Tr3900.Tr3900Request;
import com.newland.alipayService.txn.outgoing.Tr3902.Tr3902Request;
import com.newland.alipayService.txn.outgoing.Tr3902.Tr3902Response;
import com.newland.alipayService.txn.outgoing.Tr5070.Tr5070Request;
import com.newland.alipayService.txn.outgoing.Tr5070.Tr5070Response;
import com.newland.alipayService.txn.service.TxnService;
import com.newland.base.common.AppExCode;
import com.newland.base.common.Const;
import com.newland.base.util.AccountUtil;
import com.newland.base.util.ConvertUtil;

/**
 * 
 * @ClassName: AccountServiceImpl
 * @Description: 账户服务实现类
 * @author: xuzz
 * @date:2014-3-20 上午10:59:00
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory
			.getLogger(AccountServiceImpl.class);
	@Resource(name = "txnService")
	private TxnService txnService;
	@Resource(name="hsmAdapter")
	private HsmAdapter hsmAdapter;
	@Resource(name = "iCompoundParse")
	private ICompoundParse iCompoundParse;
	@Override
	public FinanceAccount queryBalance(FinanceAccount financeAccount)
			throws AppBizException {
		// 1、基础条件判断，此处只能是借记卡卡号
		if (!AccountUtil.isDebitCard(financeAccount.getAccountNo())) {
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format(
					"无效的借记卡账户！卡号：[%s]", financeAccount.getAccountNo()));
		}
		// 2、循环取，直到取完所有的信息
		List<InnerAccount> list = new ArrayList<InnerAccount>();
		Tr3502Request request = new Tr3502Request();
		Tr3502Response response = null;
		int i = 0;
		// 无需验密
		request.fwfs = "2";
		// 一次查询10条
		int recnu = 10;
		request.recnu = recnu;
		request.hbzl = "00";
		request.kh = financeAccount.getCibAccountNo();
		// 003-类务代码所有的行账号
		request.yw = "003";
		do {
			request.strec = i * recnu + 1;
			try {
				response = txnService.doTr3502(request);
				financeAccount.setOwnerName(response.khxm);
			} catch (AppBizException e) {
				logger.warn(e.getMessage());
				if (CollectionUtils.isEmpty(list)) {
					throw e;
				}
			}
			if (response == null
					|| CollectionUtils.isEmpty(response.ZZHXXResponses))
				break;

			for (Tr3502Response.TrZZHXXResponse zzh : response.ZZHXXResponses) {
				if (zzh.xxh == null || zzh.xxh.equals("XXX"))
					continue;
				if (StringUtils.isNotBlank(zzh.jlzt)
						&& ObjectUtils.equals(zzh.jlzt, "0") == false
						&& ObjectUtils.equals(zzh.jlzt, "2") == false) {
					InnerAccount innerAccount = new InnerAccount();
					innerAccount.setDepositType(zzh.ywdh);
					innerAccount.setAccountNo(zzh.zhdh);
					innerAccount.setSeqence(zzh.xxh);
					innerAccount.setOpenDate(zzh.khrq);
					innerAccount.setBalance(zzh.zhye);
					innerAccount.setRecordStatus(zzh.jlzt);
					innerAccount.setAccountNo(zzh.zhdh);
					innerAccount.setCashFlag(zzh.chbz);
					innerAccount.setOpenAmount(zzh.khje);
					innerAccount.setCurrency(zzh.hbzl);
					innerAccount.setUsableBalance(zzh.kyye);
					innerAccount.setRelativeBusinessNo(zzh.ywdh);
					if (zzh.ckqx != null)
						innerAccount.setTerm(zzh.ckqx);
					if (zzh.xccq != null)
						innerAccount.setContinueTerm(zzh.xccq);
					innerAccount.setMaturity(zzh.dqrq);
					if (StringUtils.isBlank(zzh.cpmc) == false) {
						innerAccount.setRalativeBusinessName(zzh.cpmc);
					} else if (StringUtils.isBlank(zzh.ckqx) == true) {
						innerAccount.setRalativeBusinessName(innerAccount
								.getDepositType()
								+ "账户");
					}
					list.add(innerAccount);
				}
			}
			i++;
		} while (response.ZZHXXResponses != null
				&& response.ZZHXXResponses.size() == recnu);
		financeAccount.setInnerAccount(list);
		return financeAccount;
	}

	@Override
	public CreditCardBill queryBill(CreditCardBill creditCardBill)
			throws AppBizException {
		String acctNo = creditCardBill.getAcctNo();
		String currency = creditCardBill.getCurrency();
		Date billDate = creditCardBill.getBillDate();
		if (!AccountUtil.isCreditCard(creditCardBill.getAcctNo())) {
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format(
					"无效的信用卡账户！卡号：[%s]", creditCardBill.getAcctNo()));
		}
		Tr3425Request req = new Tr3425Request();
		req.xykkh = acctNo;
		req.sfjymm = "0";
		// 币种
		req.hbzl = currency;
		// 账单年月 yyyyMM
		req.qsny = ConvertUtil.dateToString(billDate, "yyyyMM");
		Tr3425Response resp = txnService.doTr3425(req);
		// 账单金额
		creditCardBill.setBillAmount(resp.je2);
		// 账单日期
		creditCardBill.setBillAmount(resp.je2);
		// 账单日期
		creditCardBill.setBillDate(resp.rq);
		// 还款截止时间
		creditCardBill.setEndRefundDate(resp.zzrq);
		// 账单金额符号
		creditCardBill.setBillAmountSign(resp.jefh1);
		// 自动还款账号
		creditCardBill.setAutoRefundAcct(resp.zkzh);
		// 本期应还款额
		creditCardBill.setDueRepayAmount(resp.jyje);
		return creditCardBill;

	}

	@Override
	public CreditcardAccount queryLimit(String acctNo) throws AppBizException {
		if (!AccountUtil.isCreditCard(acctNo)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format(
					"无效的信用卡账户！卡号：[%s]", acctNo));
		}
		Tr3446Request req = new Tr3446Request();
		req.xykkh = acctNo;
		req.fwfs = "2";
		Tr3446Response resp = txnService.doTr3446(req);
		CreditcardAccount creditcardAccount = new CreditcardAccount();
		creditcardAccount.setAccountNo(acctNo);
		creditcardAccount.setAccountName(resp.khmc);

		// 人民币信用额度
		CreditCardLimit cnyCreditCardLimit = new CreditCardLimit();
		cnyCreditCardLimit.setCreditLimitAmount(resp.xyed);
		cnyCreditCardLimit.setCurrency("人民币");
		cnyCreditCardLimit.setUseableLimitAmount(resp.mqkyed);
		cnyCreditCardLimit.setAcctLimitAmount(resp.bbzmye);
		cnyCreditCardLimit.setAmountSymbol(resp.bbzmyefh);
		cnyCreditCardLimit.setUseableMoneyLimitAmount(resp.yjxjkyed);

		// 人民币账单
		CreditCardBill cnyCreditCardBill = new CreditCardBill();
		// 币种
		cnyCreditCardBill.setCurrency("01");
		// 卡号
		cnyCreditCardBill.setAcctNo(acctNo);
		// 自动还款账号
		cnyCreditCardBill.setAutoRefundAcct(resp.rmbhkzh);
		// 账单金额
		cnyCreditCardBill.setBillAmount(resp.rmbzdje);
		// 账单金额符号
		cnyCreditCardBill.setBillAmountSign(resp.rmbzdjefh == null?"":resp.rmbzdjefh);
		// 账单日期
		/* 账单日为2个字符,只取后面的日，因此前面的年和月可以随便写,mod by sunliulin 20150819 */
		//cnyCreditCardBill.setBillDate(resp.zhhkr);
		try {
		    cnyCreditCardBill.setBillDate(ConvertUtil.stringToDate("201508" + resp.zdr, "yyyyMMdd"));
		} catch (ParseException e) {
			logger.error("转换日期失败");
		}
		// 本期应还款额
		cnyCreditCardBill.setDueRepayAmount(resp.dqyhrmbje);
		//最低还款额
		cnyCreditCardBill.setLowPayment(resp.yhrmbje);
		//最后还款日
		cnyCreditCardBill.setEndRefundDate(resp.zhhkr);
		//未出账单金额符号
		cnyCreditCardBill.setUnsettleBillAmountSign(resp.bqxfjefh);
		//未出账单金额
		cnyCreditCardBill.setUnsettleBillAmount(resp.bbbqxfje);
		creditcardAccount.getLimits().add(cnyCreditCardLimit);
		creditcardAccount.getBills().add(cnyCreditCardBill);

		// 美元信用额度
		CreditCardLimit usdCreditCardLimit = new CreditCardLimit();
		usdCreditCardLimit.setCreditLimitAmount(resp.debzxyed);
		usdCreditCardLimit.setCurrency("美元");
		usdCreditCardLimit.setUseableLimitAmount(resp.debzkyed);
		usdCreditCardLimit.setAcctLimitAmount(resp.wbzmye);
		usdCreditCardLimit.setAmountSymbol(resp.wbzmyefh);
		usdCreditCardLimit.setUseableMoneyLimitAmount(resp.wbyjxjkyed);

		// 美元账单
		CreditCardBill usdCreditCardBill = new CreditCardBill();
		// 币种
		usdCreditCardBill.setCurrency("14");
		// 卡号
		usdCreditCardBill.setAcctNo(acctNo);
		// 自动还款账号
		usdCreditCardBill.setAutoRefundAcct(resp.mybhkzh);
		// 账单金额
		usdCreditCardBill.setBillAmount(resp.myzdje);
		// 账单金额符号
		usdCreditCardBill.setBillAmountSign(resp.mybzdjefh==null?"":resp.mybzdjefh);
		// 账单日期
		usdCreditCardBill.setBillDate(resp.zhhkr);
		// 本期应还款额
		usdCreditCardBill.setDueRepayAmount(resp.dqyhmyje);
		//最低还款额
		usdCreditCardBill.setLowPayment(resp.yhmyje);
		//最后还款日
		usdCreditCardBill.setEndRefundDate(resp.zhhkr);
		//未出账单金额符号
		usdCreditCardBill.setUnsettleBillAmountSign(resp.bqxfje);
		//未出账单金额
		usdCreditCardBill.setUnsettleBillAmount(resp.wbbqxfje);
		creditcardAccount.getLimits().add(usdCreditCardLimit);
		creditcardAccount.getBills().add(usdCreditCardBill);
		return creditcardAccount;
	}
	
	@Override
	public void validPasswd(String acctNo,String passwd) throws AppBizException{
		try{
			//判断账户类型
			//信用卡
			if(AccountUtil.isCreditCard(acctNo)){
				Tr3431Request req = new Tr3431Request();
				req.xyk_kh = acctNo;
				try{
					req.xykmm = hsmAdapter.transXYKPW(passwd);
				}
				catch(Exception e){
					throw new AppBizException(AppExCode.TXN_HSM_ERROR, String.format("发送加密机交易失败，原因:[%s]",e.getMessage()));
				}
				txnService.doTr3431(req);
			}

			//借记卡
			else{
				Tr3900Request req = new Tr3900Request();
				req.zhdh = acctNo;
				try{
					req.mm = hsmAdapter.transAcctPwd(passwd, acctNo);
				}
				catch(Exception e){
					throw new AppBizException(AppExCode.TXN_HSM_ERROR, String.format("发送加密机交易失败，原因:[%s]",e.getMessage()));
				}
				
				txnService.doTr3900(req);
			}
		}
		catch(AppBizException e){
			throw e;
		}
		catch(Exception e){
			throw new AppBizException(AppExCode.TXN_EXCEPTION, e.getMessage(),e);
		}
	}
	@Override
	public AcctInfo findAcctInfoByAcctNo(String acctNo) throws AppBizException{
		try{
			//信用卡
			if(AccountUtil.isCreditCard(acctNo)){
				Tr3422Request req = new Tr3422Request();
				//0表示不验密
				req.sfjymm = "0";
				req.xyk_kh = acctNo;
				Tr3422Response resp = txnService.doTr3422(req);
				//判断信用卡卡状态
				if(resp.jlzt !=null){
					if(Const.CC_INVLID_STATUS.contains(resp.jlzt)){
						throw new AppBizException(AppExCode.CHECK_ERROR,String.format("信用卡卡片状态无效！卡号：[%s]", acctNo));
					}
				}
				//回填证件信息
				AcctInfo acctInfo = new AcctInfo();
				acctInfo.setCertNo(resp.zjhm);
				acctInfo.setCertType(resp.zjzl);
				acctInfo.setCustName(resp.zwxm);
				// 信用卡默认06-101
				acctInfo.setBrNo(Const.CC_DEFAULT_BRANCH);
				acctInfo.setSubBrNo(Const.CC_DEFAULT_SUBBRANCH);
				return acctInfo;
			}
			//借记卡
			else if(AccountUtil.isDebitCard(acctNo)){
				Tr2560Request req = new Tr2560Request();
				req.kh = AccountUtil.calcCibAcctNo(acctNo);
				Tr2560Response resp = txnService.doTr2560(req);
				// 回填账号信息
				AcctInfo acctInfo = new AcctInfo();
				acctInfo.setCertNo(resp.zjhm);
				acctInfo.setCertType(resp.zjzl);
				acctInfo.setCustName(resp.khxm);
				acctInfo.setBrNo(AccountUtil.calcBranchNo(acctNo));
				acctInfo.setSubBrNo(resp.khjgdh);
				acctInfo.setMobile(resp.dhhm);
				return acctInfo;
			}
			else{
				throw new AppBizException(AppExCode.CHECK_ERROR, String.format("卡号有误，非有效兴业卡！卡号：[%s]", acctNo));
			}
		}
		catch(AppBizException e){
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new AppBizException(AppExCode.TXN_EXCEPTION, String.format("账户信息交易查询异常，原因:[%s]", e.getMessage(),e));
		}
	}

	@Override
	public AcctInfo findBrNoByAcctNo(String accountNo) throws AppBizException {
		AcctInfo a = null;
		if(AccountUtil.isCreditCard(accountNo)){
			a = new AcctInfo();
			a.setBrNo(Const.CC_DEFAULT_BRANCH);
			a.setSubBrNo(Const.CC_DEFAULT_SUBBRANCH);
			return a;
		}
		else{
			try{
				Tr3873Request request = new Tr3873Request();
				// 3873交易需要传递11位核心卡号，否则会当做存折处理
				request.zhdh = AccountUtil.calcCibAcctNo(accountNo);
				Tr3873Response req = txnService.doTr3873(request);
				a = new AcctInfo();
				a.setBrNo(req.khdqdh);
				a.setSubBrNo(req.khjgdh); 
				return a;
			}
			catch(AppBizException e){
				throw e;
			}
			catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new AppBizException(AppExCode.TXN_EXCEPTION, String.format("账户信息交易查询异常，原因:[%s]", e.getMessage(),e));
			}
		}
	}

	@Override
	public String ApplyDebitCard(String certNo, String certType) {
		Tr3902Request req = new Tr3902Request();
		req.zjzl = certType;
		req.zjhm = certNo;
		try{
			
			Tr3902Response resp = txnService.doTr3902(req);
			return resp.khdh;
		}
		catch(AppBizException e){
			logger.error(e.getMessage(),e);
			return "";
		}
	}
	@Override
	public FinanceAccountTransDetail queryDetail(FinanceAccount finAccount)
			throws AppBizException {
		// 1、基础条件判断，此处只能是借记卡卡号
		if (!AccountUtil.isDebitCard(finAccount.getAccountNo())) {
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format(
					"无效的借记卡账户！卡号：[%s]", finAccount.getAccountNo()));
		}
		Tr2569Request req = new Tr2569Request();
		req.kh = finAccount.getCibAccountNo();
		// 2014-08-08 与手机银行一致，改用9999交易柜员
		req.jygy = "9999";
		req.qsxh = "1";
		req.qqbs = "11";
		Tr2569Response resp = txnService.doTr2569(req);
		FinanceAccountTransDetail financeAccountTransDetail = new FinanceAccountTransDetail();
		financeAccountTransDetail.setAcctNo(finAccount.getAccountNo());
		financeAccountTransDetail.setCustName(resp.khxm);
		// 对交易明细结果进行降序排序
		Collections.sort(resp.JYMXResponses,
				new Comparator<Tr2569Response.TrJYMXResponse>() {

					@Override
					public int compare(TrJYMXResponse o1, TrJYMXResponse o2) {
						if (o1 == null) {
							return -1;
						}
						if (o2 == null) {
							return 1;
						}
						return o2.jyrq.compareTo(o1.jyrq);
					}

				});
		// 2、循环遍历明细记录
		for (Tr2569.Tr2569Response.TrJYMXResponse r : resp.JYMXResponses) {
			TransDetail t = new TransDetail();
			// 账户余额
			t.setBalanceAmount(r.zhye);
			// 交易金额
			t.setTransAmount(r.jyje);
			// 借贷标记
			t.setLoanFlag(r.jdbj);
			//TODO
//			// 摘要代码
//			t.setDigestCode(abstractTransService.getAbstractName(r.zydh, null));
			// 交易柜员
			t.setTransTeller(r.jygy);
			// 交易时间
			t.setTransTime(r.jyrq);
			financeAccountTransDetail.getDetails().add(t);
		}
		return financeAccountTransDetail;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UnSettleBill queryUnsettleBill(UnSettleBill unSettleBill)
			throws AppBizException {
		String acctNo = unSettleBill.getAcctNo();
		String currency = unSettleBill.getCurrency();
		Tr5070Request req = new Tr5070Request();
		req.xykkh = acctNo;
		req.ybmz = "0";
		req.czbz = "1";
		// 币种
		req.hbzl = currency;
		Tr5070Response resp = txnService.doTr5070(req);
		String fileMac = resp.wjid + resp.xcwjm;
		String fileName = txnService.getCTSPFile(fileMac, true);
		File file = new File(fileName);
		// 解析文件
		Collection<?> c = iCompoundParse.convertFileToModel(file,
				CreditCardTransDetail.class
						.getAnnotation(CompoundParsePattern.class));
		UnSettleBill unBill = new UnSettleBill();
		unBill.setAcctNo(acctNo);
		unBill.setCurrency(currency);
		unBill.setList((List<CreditCardTransDetail>) c);
		return unBill;
	}
}
