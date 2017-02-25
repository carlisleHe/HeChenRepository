package com.newland.alipayserver.web.actions;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intensoft.coresyst.exception.CoreNativeException;
import com.intensoft.exception.AppBizException;
import com.intensoft.scopeplugin.ScopeType;
import com.intensoft.scopeplugin.annotations.Begin;
import com.intensoft.scopeplugin.annotations.In;
import com.intensoft.scopeplugin.annotations.Out;
import com.newland.alipay.env.AlipayApplication;
import com.newland.alipayService.card.model.BindCard;
import com.newland.alipayService.card.model.CreditCardPassword;
import com.newland.alipayService.card.service.BindCardService;
import com.newland.alipayService.card.service.CreditCardPasswordService;
import com.newland.alipayService.message.MessageTemplateManager;
import com.newland.alipayService.message.MsgService;
import com.newland.alipayService.message.model.MessageTemplate;
import com.newland.alipayserver.web.annotation.ExecuteResult;
import com.newland.base.common.AppExCode;
import com.newland.base.common.Const;
import com.newland.base.util.SmsSecurity;
import com.opensymphony.xwork2.ActionContext;

/**
 * 设置信用卡密码（查询密码和交易密码）
 * 
 * @author wot_wengxiaoying
 * 
 */
@ParentPackage("alipay-default")
@Namespace("/")
@Action("creditCardPasswordManager")
@InterceptorRefs({ @InterceptorRef(value = "clean_stack") })
@Results(value = {
		@Result(name = "json", type = "json"),
		@Result(name = "index", location = "/secure/setccpass/checkTranPass.jsp"),
		@Result(name = "setquerypass", location = "/secure/setccpass/setQueryPass.jsp"),
		// @Result(name = "prompt", location =
		// "/secure/setccpass/setPassPrompt.jsp"),
		@Result(name = "setQueryAndTranPass", location = "/secure/setccpass/setQueryAndTranPass.jsp"),
		@Result(name = "success", location = "/secure/setccpass/setSuccess.jsp"),
		@Result(name = "unactivate", location = "/secure/setccpass/unactivate.jsp") })
public class CreditCardPasswordManagerAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 信用卡
	// 取款密码未设置，请先设置取款密码
	public static final Integer CC_PWD_ERR_7193 = 7193;
	// 取款密码有误
	public static final Integer CC_PWD_ERR_3805 = 3805;

	private static final Logger logger = LoggerFactory
			.getLogger(CreditCardPasswordManagerAction.class);

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private CreditCardPassword confPass = new CreditCardPassword();

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String acctno;

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String content;

	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String identify;

	// 短信验证码发送失败提示
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private String message;

	// 取出openId、信用卡、证件号
	@SuppressWarnings("unchecked")
	private Map session = ActionContext.getContext().getSession();

	// 短信验证码发送是否成功
	private String isSuccess;

//	@Resource(name = "sendIdentifyCodeService")
//	private SendIdentifyCodeService sendIdentifyCodeService;

	@Resource(name = "bindCardService")
	private BindCardService bindCardService;

	@Resource(name = "creditCardPasswordService")
	private CreditCardPasswordService creditCardPasswordService;

	// 倒计时
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	private int countDown;

	public String unactivate() {
		return "unactivate";
	}

	@Override
	@ExecuteResult(name = GLOBAL_ERROR)
	@Begin
	public String execute() throws AppBizException, IOException {
		// 1)传进进来卡号和证件号
		
		
		confPass = (CreditCardPassword) session.get(Const.SET_CC_PASSWORD);
		logger.info("传过来_confPass:" + confPass);
		// 2）格式化卡号
		StringBuilder temp = new StringBuilder();
		String acctno = confPass.getAcctNo();
		for (int i = 0; i < acctno.length(); i = i + 4) {
			temp.append(acctno.substring(i, i + 4)).append(" ");
		}
		logger.info("str:" + temp);
		confPass.setAcctNoInput(temp.toString());
		return "index";
	}

	public String lastTranPass() {
		return "index";
	}

	public String checkTranPass() throws Exception {
		// 1)校验交易密码非空
		if (StringUtils.isBlank(confPass.getTranPassword())) {
			this.addActionMessage("交易密码不能为空");
			return "index";
		}
		logger.info("交易密码：" + confPass.getTranPassword());
		try {
			// 2)校验交易密码正确性 （3420）
			creditCardPasswordService.checkTranPassword(confPass);
			// 2.1)交易密码正确
			return "setquerypass";
		} catch (Exception e) {
			if (e instanceof CoreNativeException) {
				Integer errcode = ((CoreNativeException) e).getNativeCode();
				logger.error(e.getMessage(), e);
				if (errcode.equals(CC_PWD_ERR_7193)) { // 2.2)交易密码未设置
					return tranPass();
				}
				if (errcode.equals(CC_PWD_ERR_3805)) { // 2.3)交易密码错误
					this.addActionMessage("亲，输入的交易密码有误，请重输。");
					return "index";
				}
				throw e;
			}
		}
		return NONE;
	}

	/**
	 * 跳转到设置交易密码界面
	 * 
	 * @return
	 * @throws AppBizException
	 */
	public String tranPass() throws AppBizException {
		// 1)发交易获取手机号码（3420）
		String mobile;
		try {
			mobile = creditCardPasswordService.findMobileByAcctNo(confPass
					.getAcctNo());
			if (StringUtils.isBlank(mobile)) {
				this.addActionMessage("您没有在我行预留手机号，密码暂无法设置，请联系本行客服95561处理");
				return "index";
			}
			String prefix = mobile.substring(0, 3);
			String suffix = mobile.substring(7, 11);
			confPass.setFormMobile(prefix + "****" + suffix);
			confPass.setMobile(mobile);
			genCreditCardPwdMessageTemplate();
			
			// 2)发送短信口令
			return "setQueryAndTranPass";
		} catch (AppBizException e) {
			logger.error(e.getMessage(), e);
			throw new AppBizException(AppExCode.CHECK_ERROR, "获取手机号码失败");
		}
	}
	
	/**
	 * 设置查询密码
	 * 
	 * @return
	 * @throws AppBizException
	 */
	public String confQueryPass() throws AppBizException {
		// 1)校验查询密码非空
		if (StringUtils.isBlank(confPass.getQueryPassword())) {
			this.addActionMessage("查询密码不能为空");
			return "setquerypass";
		}
		// 2)设置查询密码 （3416）
		boolean flag = creditCardPasswordService.setQueryPassword(confPass);
		if (!flag) {
			logger.info("查询密码设置失败");
			this.addActionMessage("查询密码设置失败，请重试");
			return "setquerypass";
		}
		String no = confPass.getAcctNo();
		acctno = no.substring(no.length() - 4);
		content = "查询密码";
		// 3)绑定微信
		bindWeixin();
		return "success";
	}

	/**
	 * 设置查询密码和交易密码
	 * 
	 * @return
	 * @throws AppBizException
	 */
	@SuppressWarnings("unchecked")
	@ExecuteResult(name="setQueryAndTranPass")
	public String confQueryAndTranPass() throws AppBizException {

		// 1)校验查询密码
		if (StringUtils.isBlank(confPass.getQueryPassword())) {
			this.addActionMessage("查询密码不能为空");
			return "setQueryAndTranPass";
		}
		if (StringUtils.isBlank(confPass.getTranPassword())) {
			this.addActionMessage("交易密码不能为空");
			return "setQueryAndTranPass";
		}
		// 2)校验短信口令
		verifySms();
		try {
			// 2)设置查询密码（3416）和交易密码（4614）
			creditCardPasswordService.setTranPassword(confPass);
			boolean flag = creditCardPasswordService.setQueryPassword(confPass);
			if (!flag) {
				this.addActionMessage("设置查询密码失败");
				return "setQueryAndTranPass";
			}
			String no = confPass.getAcctNo();
			acctno = no.substring(no.length() - 4);
			content = "查询密码和交易密码";
			// 3)绑定微信
			bindWeixin();
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.addActionMessage("设置密码失败（原因背面签名栏末尾3位输入有误）");
			return "setQueryAndTranPass";
		}

	}

	/**
	 * 绑定微信
	 * 
	 * @throws AppBizException
	 */
	public void bindWeixin() throws AppBizException {
		String appId = AlipayApplication.getAppId();
		BindCard bindCard = new BindCard();
		try {
			bindCard.setAcctNo(confPass.getAcctNo());
			bindCard.setAcctType(Const.ACCT_TYPE_CREDIT);
			bindCard.setCertType(confPass.getCertType());
			bindCard.setCertNo(confPass.getCertNo());
			bindCard.setCustName(confPass.getCustName());
			bindCard.setBrNo(Const.CC_DEFAULT_BRANCH);
			bindCard.setSubBrNo(Const.CC_DEFAULT_SUBBRANCH);
			bindCard.setAppId(appId);
			bindCard.setOpenId((String) session.get(Const.OPEN_ID));
			bindCard.setUpdTime(new Date());
			bindCard.setCreDate(new Date());
			bindCard.setValid(Const.STATUS_VALID);
			bindCard.setProdCode(Const.PROD_CODE);
			// 绑定
			bindCard = bindCardService.bindAuthCard(bindCard);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AppBizException(AppExCode.CHECK_ERROR, "绑定微信失败");
		}
	}
	/**
	 * @throws AppBizException 
	 */
	private void genCreditCardPwdMessageTemplate() throws AppBizException {
		messageTemplate = new MessageTemplate();
		messageTemplate.setmPhone(confPass.getMobile());
		messageTemplate
				.setTemplateName(MessageTemplateManager.CREDITCARD_SET_PASSWORD_TEMPLATE);
		setMessageTemplate();
		msgService.sendSms(getMessageTemplate(), getSessionMap());
	}

	public String getAcctno() {
		return acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public int getCountDown() {
		return countDown;
	}

	public void setCountDown(int countDown) {
		this.countDown = countDown;
	}

	public CreditCardPassword getConfPass() {
		return confPass;
	}

	public void setConfPass(CreditCardPassword confPass) {
		this.confPass = confPass;
	}

}
