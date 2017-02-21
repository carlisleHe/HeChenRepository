/**
 * 
 */
package com.cib.alipayserver.web.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipayserver.web.annotation.ExecuteResult;
import com.cib.alipayserver.web.view.AuthCardView;
import com.intensoft.coresyst.exception.CoreNativeException;
import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.intensoft.scopeplugin.ScopeType;
import com.intensoft.scopeplugin.annotations.In;
import com.intensoft.scopeplugin.annotations.Out;
import com.intensoft.util.StringUtils;
import com.newland.wechatServer.account.model.AcctInfo;
import com.newland.wechatServer.account.model.CreditCardBill;
import com.newland.wechatServer.account.model.CreditcardAccount;
import com.newland.wechatServer.account.model.FinanceAccount;
import com.newland.wechatServer.account.model.Total;
import com.newland.wechatServer.account.service.AccountService;
import com.newland.wechatServer.account.service.QueryOpenApi;
import com.newland.wechatServer.blacklist.model.BlackList;
import com.newland.wechatServer.blacklist.service.BlackListService;
import com.newland.wechatServer.card.model.BindCard;
import com.newland.wechatServer.card.model.CreditCardPassword;
import com.newland.wechatServer.card.service.BindCardService;
import com.newland.wechatServer.card.service.CreditCardPasswordService;
import com.newland.wechatServer.common.AppExCode;
import com.newland.wechatServer.common.Const;
import com.newland.wechatServer.common.captcha.CaptchaSecurity;
import com.newland.wechatServer.crm.model.CRMPrompt;
import com.newland.wechatServer.crm.service.CRMPromptService;
import com.newland.wechatServer.customer.model.Customer;
import com.newland.wechatServer.customer.service.CustomerService;
import com.newland.wechatServer.idgen.service.IdentifierGenerator;
import com.newland.wechatServer.notify.model.AuthNotify;
import com.newland.wechatServer.notify.service.AuthNotifyService;
import com.newland.wechatServer.service.QueryBalanceService;
import com.newland.wechatServer.txn.TxnErrorCode;
import com.newland.wechatServer.util.AccountUtil;
import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;

/**
 * 银行卡管理
 * 
 * @author wot_hechen
 * 
 */
@ParentPackage("alipay-default")
@Namespace("/")
@Action("bankCardManager")
@InterceptorRefs({
		@InterceptorRef(value = "base_stack"),
		@InterceptorRef(value = "token", params = { "includeMethods",
				"addAcct,auth,close,authNotify" }) })
@Results(value = {
		@Result(name = "toAddCard", location = "/secure/bankCardManager/toAddCard.jsp"),
		@Result(name = "bankCardList", location = "/secure/bankCardManager/bankCardList.jsp"),

		@Result(name = "passWord", location = "/secure/bankCardManager/passWord.jsp"),
		@Result(name = "credit", location = "/secure/credit.jsp"),
		@Result(name = "show", location = "/secure/bankCardManager/show.jsp"),
		@Result(name = "notifyPassWord", location = "/secure/bankCardManager/notifyPassWord.jsp"),
		@Result(name = "unactivate", type = "chain", location = "creditCardPasswordManager", params = {
				"namespace", "/", "method", "unactivate" }),
		@Result(name = "invalid.token", type = "chain", location = "bankCardManager", params = {
				"namespace", "/", "method", "doInvalidToken" }) })
public class BankCardManagerAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory
			.getLogger(BankCardManagerAction.class);

	private static final String OPERTYPE_MAIN = "MAIN";
	private static final String OPERTYPE_ADD = "ADD";
	@Resource(name = "customerService")
	private CustomerService customerService;

	@Resource(name = "bindCardService")
	private BindCardService bindCardService;
	@Resource(name = "accountService")
	private AccountService accountService;
	@Resource(name = "blackListService")
	private BlackListService blackListService;
	@Resource(name = "authNotifyService")
	private AuthNotifyService authNotifyService;
	@Resource(name = "queryOpenApi")
	private QueryOpenApi queryOpenApi;
	@Resource(name = "queryBalanceService")
	private QueryBalanceService queryBalanceService;
	// @Resource(name = "authNotifyOpenApi")
	// private AuthNotifyOpenApi authNotifyOpenApi;

	// @Resource(name = "blackListOpenApi")
	// private BlackListOpenApi blackListOpenApi;

	// @Resource(name = "queryBalanceService")
	// private QueryBalanceService queryService;
	// @Resource(name = "accountOpenApi")
	// private AccountOpenApi accountOpenApi;
	@Resource(name = "crmPromptService")
	private CRMPromptService crmPromptService;
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	@Resource(name = "creditCardPasswordService")
	private CreditCardPasswordService creditCardPasswordService;
	/**
	 * 
	 */
	@Value("@[alipay_auth_bind_bank_card_add_url]")
	public String callBackBindBankCardToAddUrl;
	@Value("@[alipay_auth_bind_bank_card_list_url]")
	public String callBackBindBankCardToListUrl;
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private List<AuthCardView> authCardViewList;
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String openId;

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private AuthCardView authCardView;

	/**
	 * 附加码
	 */
	private String captchafield;
	private String needCaptcha;
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private Integer selectIndex;

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private AuthCardView selAuthCardView;

	/**
	 * 操作类型
	 */
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String operationType;

	/**
	 * 第一步页面授权，获取code
	 */
	public String execute() {
		try {
			if (StringUtils.isBlank(operationType)) {
				throw new AppRTException(AppExCode.CHECK_ERROR,
						"operationType不能为空，请重试！");
			}
			// 1)页面授权
			if (OPERTYPE_ADD.equals(operationType)) {
				super.oauthWeb(callBackBindBankCardToAddUrl);
			} else if (OPERTYPE_MAIN.equals(operationType)) {
				super.oauthWeb(callBackBindBankCardToListUrl);
			}
		} catch (IOException e) {
			logger.error("获取code出错:" + e.getMessage(), e);
			throw new AppRTException(AppExCode.CHECK_ERROR, "获取alipayId失败，请重试！");
		}
		return NONE;
	}

	public String accessTokenToAdd() throws Exception {
		// 1)获取code
		String code = ServletActionContext.getRequest().getParameter(
				"auth_code");
		// 2)获取openId
		openId = super.accessToken(code);
		if (StringUtils.isBlank(openId)) {
			throw new AppRTException(AppExCode.CHECK_ERROR,
					"获取alipay ID失败，请重试！");
		}
		getSessionMap().put(Const.OPEN_ID, openId);
		return toAddCard();
	}

	public String accessTokenToBankList() throws Exception {
		// 1)获取code
		String code = ServletActionContext.getRequest().getParameter(
				"auth_code");
		// 2)获取openId
		openId = super.accessToken(code);
		if (StringUtils.isBlank(openId)) {
			throw new AppRTException(AppExCode.CHECK_ERROR,
					"获取alipay ID失败，请重试！");
		}
		getSessionMap().put(Const.OPEN_ID, openId);
		return bankCardList();
	}

	/**
	 * 展示银行卡列表
	 * 
	 * @return
	 */
	public String bankCardList() {
		// 2 查询该openID 是否已绑定账号
		authCardViewList = new ArrayList<AuthCardView>();
		Customer authCustomer = customerService.findAuthCustomer(
				AlipayApplication.getAppId(), openId);

		/**
		 * 银行卡不为空
		 */
		if (!(authCustomer == null || authCustomer.getAuthCards().size() < 1)) {

			// 3 已有绑定
			List<BindCard> bindCards = authCustomer.getAuthCards();
			authCardListConvertAuthCardView(authCustomer, bindCards);
		}
		sortAuthCardView(authCardViewList);
		return "bankCardList";
	}

	public String toAddCard() {
		authCardView = null;
		return "toAddCard";
	}

	@ExecuteResult(name = "toAddCard")
	public String addAcct() throws AppBizException, InterruptedException {
		if (authCardView == null) {
			this.addActionMessage("输入不能为空");
			return "toAddCard";
		}
		logger.debug("账号:[" + authCardView.getAcctNo() + "]证件号：["
				+ authCardView.getCertNo() + "]");
		if (StringUtils.isBlank(authCardView.getAcctNo())) {
			this.addActionMessage("账号不能为空");
			return "toAddCard";
		}
		if (!AccountUtil.isCreditCard(authCardView.getAcctNo())
				&& !AccountUtil.isDebitCard(authCardView.getAcctNo())) {
			this.addActionMessage("卡号输入有误，不是有效兴业卡！");
			return "toAddCard";
		}
		if (StringUtils.isBlank(authCardView.getCertNo())) {
			this.addActionMessage("证件号不能为空");
			return "toAddCard";
		}

		try {
			// 验证附加码
			needCaptcha = (String) getSessionMap().get(
					Const.GLOBAL_SESSION_NEEDCAPTCHA);
			if (needCaptcha != null && needCaptcha.equals("1")) {
				CaptchaSecurity captcha = (CaptchaSecurity) getSessionMap()
						.get(CaptchaSecurity.IDENTIFYING_CODE);
				this.validateCaptcha(captcha);
			}
		} catch (Exception e) {
			this.addActionMessage(e.getMessage());
			needCaptcha = "1";
			captchafield = null;
			getSessionMap().put(Const.GLOBAL_SESSION_NEEDCAPTCHA, needCaptcha);
			return "toAddCard";
		}

		needCaptcha = "1";
		getSessionMap().put(Const.GLOBAL_SESSION_NEEDCAPTCHA, needCaptcha);

		// 1 查询是否已绑定 该微信号
		BindCard bindCard = bindCardService.findAuthCardByAcctNo(
				AlipayApplication.getAppId(), openId, authCardView.getAcctNo());
		if (bindCard != null) {
			this.addActionMessage("亲，这张卡已和您当前的微信号绑定过了哦");
			return "toAddCard";
		}
		// 2 验证账号 和类型
		if (AccountUtil.isCreditCard(authCardView.getAcctNo())) {
			authCardView.setAcctType(Const.ACCT_TYPE_CREDIT);// 信用卡 2
		} else if (AccountUtil.isFullDebitCard(authCardView.getAcctNo())) {
			authCardView.setAcctType(Const.ACCT_TYPE_DEBIT);// 借记卡 1
			// 借记卡还要验证核心卡号
			// 1 借记卡查询是否已绑定 该微信号
			bindCard = bindCardService.findAuthCardBycibAcctNo(
					AlipayApplication.getAppId(), openId,
					AccountUtil.calcCibAcctNo(authCardView.getAcctNo()));
			if (bindCard != null) {
				this.addActionMessage("亲，这张卡已和您当前的微信号绑定过了哦");
				return "toAddCard";
			}

		} else {
			this.addActionMessage("亲，您输入的卡号不正确，一定要兴业银行的卡哦");
			return "toAddCard";
		}

		// 3 验证证件号

		AcctInfo acctInfo = accountService.findAcctInfoByAcctNo(authCardView
				.getAcctNo());
		if (acctInfo != null && acctInfo.getCertNo() != null) {
			if (authCardView.getCertNo().length() > 6) {
				if (authCardView.getCertNo().toLowerCase()
						.equals(acctInfo.getCertNo().toLowerCase())) {
					authCardView.setTxnCertNo(acctInfo.getCertNo());
					authCardView.setCertType(acctInfo.getCertType());
					authCardView.setCustName(acctInfo.getCustName());
					authCardView.setAcctName(acctInfo.getCustName());
					authCardView.setBrNo(acctInfo.getBrNo());
					authCardView.setSubBrNo(acctInfo.getSubBrNo());
				} else {
					this.addActionMessage("证件号不正确");
					// 重置输入
					authCardView.setCertNo("");
					captchafield = null;
					return "toAddCard";
				}
			} else {

				// 核心返回的证件号 长度是否小于6位
				String txnCertNo;
				if (acctInfo.getCertNo().length() >= 6) {
					logger.debug("核心返回的证件号后六位：["
							+ acctInfo.getCertNo().substring(
									acctInfo.getCertNo().length() - 6,
									acctInfo.getCertNo().length()) + "]");
					txnCertNo = acctInfo.getCertNo().substring(
							acctInfo.getCertNo().length() - 6,
							acctInfo.getCertNo().length());
				} else {
					txnCertNo = acctInfo.getCertNo();
				}
				if (authCardView.getCertNo().toLowerCase()
						.equals(txnCertNo.toLowerCase())) {
					authCardView.setTxnCertNo(acctInfo.getCertNo());
					authCardView.setCertType(acctInfo.getCertType());
					authCardView.setCustName(acctInfo.getCustName());
					authCardView.setAcctName(acctInfo.getCustName());
					authCardView.setBrNo(acctInfo.getBrNo());
					authCardView.setSubBrNo(acctInfo.getSubBrNo());
				} else {
					this.addActionMessage("证件号不正确");
					// 重置输入
					authCardView.setCertNo("");
					captchafield = null;
					return "toAddCard";
				}
			}

		}
		needCaptcha = "0";
		getSessionMap().put(Const.GLOBAL_SESSION_NEEDCAPTCHA, needCaptcha);
		if (AccountUtil.isCreditCard(authCardView.getAcctNo())) {
			// 3416是否已设置查询密码
			boolean type = creditCardPasswordService.isSetPassByAcctNo(authCardView
					.getAcctNo());
			// 未激活,跳转到错误界面
			if (!type) {
				CreditCardPassword confPassword = new CreditCardPassword();
				confPassword.setAcctNo(authCardView.getAcctNo());
				confPassword.setCertNo(acctInfo.getCertNo());
				confPassword.setInputCertNo(authCardView.getCertNo());
				confPassword.setCertType(acctInfo.getCertType());
				confPassword.setCustName(acctInfo.getCustName());
				getSessionMap().put(Const.SET_CC_PASSWORD, confPassword);
				return "unactivate";
			}
		}
		return "passWord";
	}

	public String auth() throws Exception {
		if (authCardView == null) {
			this.addActionMessage("输入不能为空");
			return "passWord";
		}
		logger.debug("账号:[" + authCardView.getAcctNo() + "]密码：["
				+ authCardView.getPassWord() + "]");
		if (StringUtils.isBlank(authCardView.getPassWord())) {
			this.addActionMessage("密码不能为空");
			return "passWord";
		}
		// 查询黑名单
		BlackList blackList = blackListService.findBlackListByTransDate(
				AlipayApplication.getAppId(), openId, new Date(),
				BlackListService.BLACK_LIST_TYPE_AUTH_CARD);
		if (blackList != null) {
			if (blackList.getApplyErr() >= BlackListService.BLACK_LIST_AUTH_CARD_MAI) {
				this.addActionMessage("您的微信号绑定服务今天已被锁定，请您明天再尝试");
				return "toAddCard";
			}
		}

		try {
			accountService.validPasswd(authCardView.getAcctNo(),
					authCardView.getPassWord());
		} catch (Exception e) {
			logger.error("绑定微信银行验证密码异常" + e.getMessage(), e);

			if (e instanceof CoreNativeException) {
				// 卡授权认证黑名单 失败时，错误次数 增加一次 只对核心的错误
				blackListService.acctErrCount(AlipayApplication.getAppId(),
						openId, new Date(),
						BlackListService.BLACK_LIST_TYPE_AUTH_CARD);
				Integer errcode = ((CoreNativeException) e).getNativeCode();
				logger.debug("绑定微信银行验证密码，错误码：" + errcode);
				// 信用卡
				if (Const.ACCT_TYPE_CREDIT.equals(authCardView.getAcctType())) {
					if (errcode.equals(TxnErrorCode.CC_PWD_ERR_7290)) {
						this.addActionMessage("亲，您的信用卡查询密码已被锁定，可登录我行网上银行或手机银行进行重置。");
						return "passWord";
					}
					if (errcode.equals(TxnErrorCode.CC_PWD_ERR_7289)) {
						this.addActionMessage("亲，您的信用卡查询密码已被锁定，可登录我行网上银行或手机银行进行重置。");
						return "passWord";
					}
					throw e;
				} else {// 借记卡
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_1814)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "passWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_3805)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "passWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_7118)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "passWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_1830)) {
						this.addActionMessage("亲，您的理财卡密码连续错误6次已被锁定，请至开户行网点处理。");
						return "passWord";
					}
					throw e;
				}
			}
			throw e;
		}
		// 卡授权认证黑名单 成功时，错误次数 清零
		if (blackList != null) {
			blackListService.clearErrCount(AlipayApplication.getAppId(),
					openId, new Date(),
					BlackListService.BLACK_LIST_TYPE_AUTH_CARD);
		}
		BindCard bindCard = new BindCard();
		bindCard.setAcctNo(authCardView.getAcctNo());
		bindCard.setAcctType(authCardView.getAcctType());
		bindCard.setCertType(authCardView.getCertType());
		bindCard.setCertNo(authCardView.getTxnCertNo());
		bindCard.setCustName(authCardView.getAcctName());
		bindCard.setBrNo(authCardView.getBrNo());
		bindCard.setSubBrNo(authCardView.getSubBrNo());
		bindCard.setAppId(AlipayApplication.getAppId());
		bindCard.setOpenId(openId);
		bindCard.setUpdTime(new Date());
		bindCard.setCreDate(new Date());
		bindCard.setValid(Const.STATUS_VALID);
		bindCard.setProdCode(Const.PROD_CODE);
		// 绑定
		bindCard = bindCardService.bindAuthCard(bindCard);
		try {
			// 2、保存推广人信息
			String promptId = authCardView.getPromptId();
			// 只有推广人信息不为空的时候才进行登记，否则不登记
			// 推荐广信息保存失败，不影响绑定结果
			if (StringUtils.isNotBlank(promptId)) {
				if (promptId.length() != 6) {
					throw new AppBizException(AppExCode.CHECK_ERROR,
							"推广人ID只能为6位的数字或字母！");
				}
				CRMPrompt crmPrompt = new CRMPrompt();
				crmPrompt.setAcctNo(bindCard.getAcctNo());
				if (StringUtils.isNotBlank(bindCard.getAcctNo())) {
					if (AccountUtil.isDebitCard(bindCard.getAcctNo())) {
						crmPrompt.setAcctType("0");
					} else {
						crmPrompt.setAcctType("1");
					}
				}
				// 微信银行渠道号 090
				crmPrompt.setCrmChannel("090");
				// 微信银行签约
				crmPrompt.setBizType("020304");
				crmPrompt.setPromptId(authCardView.getPromptId());
				crmPrompt.setCustName(authCardView.getAcctName());
				crmPrompt.setBrNo(authCardView.getBrNo());
				crmPrompt.setSubBr(authCardView.getSubBrNo());
				crmPrompt.setBizNo(snoGenerator.generate());
				crmPrompt.setEventTime(new Date());
				crmPrompt.setUpdTime(new Date());
				crmPromptService.saveCRMPrompt(crmPrompt);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		this.addActionMessage("绑定成功");
		return "passWord";
	}

	@ExecuteResult(name = "show")
	public String authNotify() throws Exception {
		String notifyType = selAuthCardView.getNotifyType();
		if (StringUtils.isBlank(notifyType)) {
			return "notifyPassWord";
		} else {// 关闭动户通知
			AuthNotify authNotify = new AuthNotify();
			authNotify.setAppId(AlipayApplication.getAppId());
			authNotify.setAppUserId(selAuthCardView.getClientId());
			authNotify.setCustName(selAuthCardView.getAcctName());
			authNotify.setAcctNo(selAuthCardView.getAcctNo());
			authNotify.setAcctType(selAuthCardView.getAcctType());
			authNotify.setBrNo(selAuthCardView.getBrNo());
			authNotify.setCertType(selAuthCardView.getCertType());
			authNotify.setCertNo(selAuthCardView.getCertNo());
			authNotify.setSubBrNo(selAuthCardView.getSubBrNo());
			if (Const.ACCT_TYPE_DEBIT.equals(selAuthCardView.getAcctType())) {
				authNotify.setNotifyType(Const.MESSAGE_TYPE_BALANCE);// 动户通知
			} else {
				authNotify.setNotifyType(Const.MESSAGE_TYPE_CREDIT);// 动户通知
			}
			// 关闭动户通知
			authNotifyService.unbindAuthNotify(authNotify);
			return this.showMod();
		}
	}

	@ExecuteResult(name = "list")
	public String show() throws Exception {
		logger.debug("选择的账号序号为" + selectIndex);
		selAuthCardView = null;

		if (StringUtils.isBlank(Integer.toString(selectIndex))) {
			this.addActionMessage("请选择一张账号");
			return "list";
		}
		selAuthCardView = authCardViewList.get(selectIndex);
		// 1 查询 通知类型：00-动户通知;02-信用卡动户通知
		AuthNotify authNotify = authNotifyService.findAuthNotify(
				AlipayApplication.getAppId(), selAuthCardView.getClientId(),
				selAuthCardView.getAcctNo(), Const.MESSAGE_TYPE_BALANCE,
				Const.MESSAGE_TYPE_CREDIT);
		if (authNotify != null) {
			selAuthCardView.setNotifyType(authNotify.getNotifyType());
			logger.debug("选择的账号通知类型为" + selAuthCardView.getNotifyType());
		}

		// 2 查卡信息
		if (Const.ACCT_TYPE_CREDIT.equals(selAuthCardView.getAcctType())) {
			CreditcardAccount acct = queryOpenApi.queryLimit(selAuthCardView
					.getAcctNo());
			if (acct != null) {
				for (int i = 0; i < acct.getBills().size(); i++) {
					CreditCardBill ccb = acct.getBills().get(i);
					if (ccb.getCurrency().equals("01")) { // 人民币
						selAuthCardView.setBillAmountCNY(ccb
								.getDueRepayAmount());
						selAuthCardView
								.setEndRefundDate(ccb.getEndRefundDate());
					}
					if (ccb.getCurrency().equals("14")) {// 美元
						selAuthCardView.setBillAmountUSD(ccb
								.getDueRepayAmount());
						selAuthCardView
								.setEndRefundDate(ccb.getEndRefundDate());
					}
				}
			} else {
				selAuthCardView.setBillAmountCNY(new BigDecimal(0.0));
				selAuthCardView.setBillAmountUSD(new BigDecimal(0.0));
				// 经业务确定 当交易报错时，还款日为空
			}
		} else {

			FinanceAccount financeAccount = queryOpenApi
					.queryBalance(selAuthCardView.getAcctNo());
			if (financeAccount != null) {
				List<Total> totals = queryBalanceService
						.queryBalance(selAuthCardView.getAcctNo());
				for (Total total : totals) {
					if (total.isTotal() && total.getCurrency().equals("01")) {
						selAuthCardView.setBalance(total.getAmount());
					}
				}
				selAuthCardView.setAcctName(financeAccount.getOwnerName());
			}
		}
		return "show";
	}

	// 开通动户通知
	@ExecuteResult(name = "notifyPassWord")
	public String openNotify() throws Exception {
		// 验证密码
		if (selAuthCardView == null) {
			this.addActionMessage("输入不能为空");
			return "notifyPassWord";
		}
		logger.debug("账号:[" + selAuthCardView.getAcctNo() + "]密码：["
				+ selAuthCardView.getPassWord() + "]");
		if (StringUtils.isBlank(selAuthCardView.getPassWord())) {
			this.addActionMessage("密码不能为空");
			return "notifyPassWord";
		}
		// TOTO 是否要验证黑名单
		// 查询黑名单
		BlackList blackList = blackListService.findBlackList(
				AlipayApplication.getAppId(), openId, new Date(),
				BlackListService.BLACK_LIST_TYPE_NOTIFY);
		if (blackList != null) {
			if (blackList.getApplyErr() >= BlackListService.BLACK_LIST_NOTIFY_MAI) {
				this.addActionMessage("您的微信号动户通知开通服务今天已被锁定，请您明天再尝试");
				return "show";
			}
		}

		try {
			accountService.validPasswd(selAuthCardView.getAcctNo(),
					selAuthCardView.getPassWord());
		} catch (Exception e) {
			logger.error("开通动户通知验证密码异常" + e.getMessage(), e);

			if (e instanceof CoreNativeException) {
				// 卡授权认证黑名单 失败时，错误次数 增加一次 只对核心的错误
				blackListService.acctErrCount(AlipayApplication.getAppId(),
						openId, new Date(),
						BlackListService.BLACK_LIST_TYPE_NOTIFY);
				Integer errcode = ((CoreNativeException) e).getNativeCode();
				logger.debug("开通动户通知验证密码，错误码：" + errcode);
				// 信用卡
				if (Const.ACCT_TYPE_CREDIT
						.equals(selAuthCardView.getAcctType())) {
					if (errcode.equals(TxnErrorCode.CC_PWD_ERR_7290)) {
						this.addActionMessage("亲，您的信用卡查询密码已被锁定，可登录我行网上银行或手机银行进行重置。");
						return "notifyPassWord";
					}
					if (errcode.equals(TxnErrorCode.CC_PWD_ERR_7289)) {
						this.addActionMessage("亲，您的信用卡查询密码已被锁定，可登录我行网上银行或手机银行进行重置。");
						return "notifyPassWord";
					}
					throw e;
				} else {// 借记卡
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_1814)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "notifyPassWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_3805)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "notifyPassWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_7118)) {
						this.addActionMessage("亲，您的密码输入有误，再想想，看好您哦，连续错误6次将被锁定。");
						return "notifyPassWord";
					}
					if (errcode.equals(TxnErrorCode.DC_PWD_ERR_1830)) {
						this.addActionMessage("亲，您的理财卡密码连续错误6次已被锁定，请至开户行网点处理。");
						return "notifyPassWord";
					}
					throw e;
				}
			}
			throw e;
		}

		// 卡授权认证黑名单 成功时，错误次数 清零
		if (blackList != null) {
			blackListService
					.clearErrCount(AlipayApplication.getAppId(), openId,
							new Date(), BlackListService.BLACK_LIST_TYPE_NOTIFY);
		}

		AuthNotify authNotify = new AuthNotify();
		authNotify.setAppId(AlipayApplication.getAppId());
		authNotify.setAppUserId(selAuthCardView.getClientId());
		authNotify.setCustName(selAuthCardView.getAcctName());
		authNotify.setCertType(selAuthCardView.getCertType());
		authNotify.setCertNo(selAuthCardView.getCertNo());
		authNotify.setAcctNo(selAuthCardView.getAcctNo());
		authNotify.setAcctType(selAuthCardView.getAcctType());
		authNotify.setBrNo(selAuthCardView.getBrNo());
		authNotify.setSubBrNo(selAuthCardView.getSubBrNo());
		if (Const.ACCT_TYPE_DEBIT.equals(selAuthCardView.getAcctType())) {
			authNotify.setNotifyType(Const.MESSAGE_TYPE_BALANCE);// 动户通知
		} else {
			authNotify.setNotifyType(Const.MESSAGE_TYPE_CREDIT);// 动户通知
		}
		// 开通动户通知
		authNotifyService.bindAuthNotify(authNotify);

		return this.showMod();
	}

	// 解除和微信银行绑定
	@ExecuteResult(name = "show")
	public String close() {
		BindCard bindCard = new BindCard();
		bindCard.setAcctNo(selAuthCardView.getAcctNo());
		bindCard.setAcctType(selAuthCardView.getAcctType());
		bindCard.setCertType(selAuthCardView.getCertType());
		bindCard.setCertNo(selAuthCardView.getTxnCertNo());
		bindCard.setCustName(selAuthCardView.getAcctName());
		bindCard.setBrNo(selAuthCardView.getBrNo());
		bindCard.setSubBrNo(selAuthCardView.getSubBrNo());
		bindCard.setAppId(AlipayApplication.getAppId());
		bindCard.setOpenId(openId);
		bindCardService.unbindAuthCard(bindCard);
		return bankCardList();
	}

	// 设置为默认卡
	@ExecuteResult(name = "show")
	public String defaultCard() throws Exception {
		customerService.defaultCardUpd(selAuthCardView.getAppId(),
				selAuthCardView.getClientId(), selAuthCardView.getAcctNo(),
				Const.CARD_BIND);
		return this.showMod();
	}

	@ExecuteResult(name = "list")
	public String showMod() throws Exception {
		if (selAuthCardView == null) {
			return bankCardList();
		} else {
			selAuthCardView.setNotifyType(null);
			selAuthCardView.setDefaultCard(false);
		}

		Customer authCustomer = customerService.findAuthCustomer(
				AlipayApplication.getAppId(), openId);
		if (authCustomer == null || authCustomer.getAuthCards().size() < 1) {
			return "toAddCard";
		} else {
			if (!StringUtils.isBlank(authCustomer.getDefaultCCCard())) {
				if (authCustomer.getDefaultCCCard().trim()
						.equals(selAuthCardView.getAcctNo().trim())) {
					selAuthCardView.setDefaultCard(true);
				}
			}
			if (!StringUtils.isBlank(authCustomer.getDefaultDCCard())) {
				if (authCustomer.getDefaultDCCard().trim()
						.equals(selAuthCardView.getAcctNo().trim())) {
					selAuthCardView.setDefaultCard(true);
				}
			}
		}

		// 1 查询 通知类型：00-动户通知;02-信用卡动户通知
		AuthNotify authNotify = authNotifyService.findAuthNotify(
				AlipayApplication.getAppId(), selAuthCardView.getClientId(),
				selAuthCardView.getAcctNo(), Const.MESSAGE_TYPE_BALANCE,
				Const.MESSAGE_TYPE_CREDIT);
		if (authNotify != null) {
			selAuthCardView.setNotifyType(authNotify.getNotifyType());
			logger.debug("选择的账号通知类型为" + selAuthCardView.getNotifyType());
		} else {

		}

		// 2 查卡信息
		if (Const.ACCT_TYPE_CREDIT.equals(selAuthCardView.getAcctType())) {

			CreditcardAccount acct = queryOpenApi.queryLimit(selAuthCardView
					.getAcctNo());
			if (acct != null) {
				for (int i = 0; i < acct.getBills().size(); i++) {
					CreditCardBill ccb = acct.getBills().get(i);
					if (ccb.getCurrency().equals("01")) { // 人民币
						selAuthCardView.setBillAmountCNY(ccb
								.getDueRepayAmount());
						selAuthCardView
								.setEndRefundDate(ccb.getEndRefundDate());
					}
					if (ccb.getCurrency().equals("14")) {// 美元
						selAuthCardView.setBillAmountUSD(ccb
								.getDueRepayAmount());
						selAuthCardView
								.setEndRefundDate(ccb.getEndRefundDate());
					}
				}
			} else {
				selAuthCardView.setBillAmountCNY(new BigDecimal(0.0));
				selAuthCardView.setBillAmountUSD(new BigDecimal(0.0));
				// 经业务确定 当交易报错时，还款日为空
			}

		} else {
			FinanceAccount financeAccount = queryOpenApi
					.queryBalance(selAuthCardView.getAcctNo());
			if (financeAccount != null) {
				List<Total> totals = queryBalanceService
						.queryBalance(selAuthCardView.getAcctNo());
				for (Total total : totals) {
					if (total.isTotal() && total.getCurrency().equals("01")) {
						selAuthCardView.setBalance(total.getAmount());
					}
				}
				selAuthCardView.setAcctName(financeAccount.getOwnerName());
			}
		}
		return "show";
	}

	/**
	 * 处理验密后的重复提交，利用struts2的token 进行重复提交的拦截 提示信息在原页面上展示
	 */
	public String doInvalidToken() {
		addActionMessage("请求已提交，请勿重复提交！");
		String result = null;
		LinkedList<String> chainHistory = ActionChainResult.getChainHistory();
		String key = chainHistory.getFirst();
		if (key.equals("/bankCardManager!addAcct")) {
			return "bankCardList";
		} else if (key.equals("/bankCardManager!auth")) {
			result = "bankCardList";
		} else {
			result = "bankCardList";
		}
		return result;
	}

	// 对账号进行排序 类型、默认卡
	private static List<AuthCardView> sortAuthCardView(List<AuthCardView> list) {
		Collections.sort(list, new Comparator<AuthCardView>() {
			@Override
			public int compare(AuthCardView o1, AuthCardView o2) {
				int flag = o1.getAcctType().compareTo(o2.getAcctType());
				if (flag == 0) {
					if (o1.isDefaultCard() == o2.isDefaultCard()) {
						return 0;
					} else {
						if (o1.isDefaultCard()) {
							return 0;
						} else {
							return 1;
						}
					}
				} else {
					return flag;
				}
			}
		});
		return list;
	}

	private void authCardListConvertAuthCardView(Customer authCustomer,
			List<BindCard> bindCards) {
		for (BindCard bindCard : bindCards) {
			AuthCardView acv = new AuthCardView();
			acv.setAcctNo(bindCard.getAcctNo());
			acv.setAcctType(bindCard.getAcctType());
			acv.setCertNo(bindCard.getCertNo());
			acv.setCertType(bindCard.getCertType());
			acv.setCustName(authCustomer.getCustName());
			acv.setAcctNoView(bindCard.getAcctNo());
			acv.setAppId(bindCard.getAppId());
			acv.setClientId(bindCard.getOpenId());
			acv.setAcctName(bindCard.getCustName());
			acv.setBrNo(bindCard.getBrNo());
			acv.setSubBrNo(bindCard.getSubBrNo());
			if (!StringUtils.isBlank(authCustomer.getDefaultCCCard())) {
				if (authCustomer.getDefaultCCCard().trim()
						.equals(bindCard.getAcctNo().trim())) {
					acv.setDefaultCard(true);
				}
			}
			if (!StringUtils.isBlank(authCustomer.getDefaultDCCard())) {
				if (authCustomer.getDefaultDCCard().trim()
						.equals(bindCard.getAcctNo().trim())) {
					acv.setDefaultCard(true);
				}
			}
			authCardViewList.add(acv);
		}
	}

	/**
	 * 验证验证码
	 */
	protected void validateCaptcha(CaptchaSecurity captcha) {
		if (captcha == null) {
			throw new AppRTException(AppExCode.CHECK_ERROR,
					"session capcha is null");
		}
		if (StringUtils.isEmpty(captchafield)) {
			throw new AppRTException(AppExCode.NOT_NULL, "为保证您的账户安全，请输入验证码！");
		}
		if (!captcha.valid(captchafield)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "验证码错误，请重新输入！");
		}
	}

	public List<AuthCardView> getAuthCardViewList() {
		return authCardViewList;
	}

	public void setAuthCardViewList(List<AuthCardView> authCardViewList) {
		this.authCardViewList = authCardViewList;
	}

	public AuthCardView getAuthCardView() {
		return authCardView;
	}

	public void setAuthCardView(AuthCardView authCardView) {
		this.authCardView = authCardView;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCaptchafield() {
		return captchafield;
	}

	public void setCaptchafield(String captchafield) {
		this.captchafield = captchafield;
	}

	public Integer getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(Integer selectIndex) {
		this.selectIndex = selectIndex;
	}

	public AuthCardView getSelAuthCardView() {
		return selAuthCardView;
	}

	public void setSelAuthCardView(AuthCardView selAuthCardView) {
		this.selAuthCardView = selAuthCardView;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
