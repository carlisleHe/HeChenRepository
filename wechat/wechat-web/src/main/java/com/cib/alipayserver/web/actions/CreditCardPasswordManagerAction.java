package com.cib.alipayserver.web.actions;

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

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipayserver.web.annotation.ExecuteResult;
import com.intensoft.coresyst.exception.CoreNativeException;
import com.intensoft.exception.AppBizException;
import com.intensoft.scopeplugin.ScopeType;
import com.intensoft.scopeplugin.annotations.Begin;
import com.intensoft.scopeplugin.annotations.In;
import com.intensoft.scopeplugin.annotations.Out;
import com.newland.wechatServer.card.model.BindCard;
import com.newland.wechatServer.card.model.CreditCardPassword;
import com.newland.wechatServer.card.service.BindCardService;
import com.newland.wechatServer.card.service.CreditCardPasswordService;
import com.newland.wechatServer.common.AppExCode;
import com.newland.wechatServer.common.Const;
import com.newland.wechatServer.message.MessageTemplateManager;
import com.newland.wechatServer.message.MsgService;
import com.newland.wechatServer.message.model.MessageTemplate;
import com.newland.wechatServer.util.SmsSecurity;
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
//		confPass = new CreditCardPassword();
//		confPass.setAcctNo("4512900005144104");
//		confPass.setCertNo("350702198506020015");
//		confPass.setCertType("0");
//		confPass.setCustName("小小");
		
		
		confPass = (CreditCardPassword) session.get(Const.SET_CC_PASSWORD);
//		System.out.println(confPass);
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
					// return "setQueryAndTranPass";
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
//			sendIdentifyCode();
//			countDown = -1;
			return "setQueryAndTranPass";
		} catch (AppBizException e) {
			logger.error(e.getMessage(), e);
			throw new AppBizException(AppExCode.CHECK_ERROR, "获取手机号码失败");
		}
		// countDown = -1;
		// return "setQueryAndTranPass";
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

		// return "setQueryAndTranPass";
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
//		Map session = ActionContext.getContext().getSession();
//		IdentifyCode identifyCode = (IdentifyCode) session.get(Const.IDENTIFY);
//		Date date = new Date();
//		if (identifyCode == null || !identify.equals(identifyCode.getCode())) {
//			this.addActionMessage("短信口令不正确，请重新输入");
//			return "setQueryAndTranPass";
//		}
//		if (date.getTime() > identifyCode.getExpireTime().getTime()) {
//			this.addActionMessage("短信口令失效，请重新获取");
//			return "setQueryAndTranPass";
//		}
		try {
			// 2)设置查询密码（3416）和交易密码（4614）
			creditCardPasswordService.setTranPassword(confPass);
			boolean flag = creditCardPasswordService.setQueryPassword(confPass);
			if (!flag) {
//				if (date.getTime() > identifyCode.getExpireTime().getTime()) {
//					this.addActionMessage("短信口令失效，请重新获取");
//					return "setQueryAndTranPass";
//				}
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
	 * 发送验证码
	 * 
	 * @return
	 * @throws AppBizException
	 */
	@SuppressWarnings("unchecked")
	public String sendIdentifyCode() throws AppBizException {
		MessageTemplate mt = getMessageTemplate();
		if (mt == null) {
			throw new AppBizException("error.no_found_template",
					getText("error.no_found_template"));
		}
		SmsSecurity sessionAuthCode = (SmsSecurity) sessionMap.get(MsgService.SMS_SENT);
		SmsSecurity ss = msgService.sendSms(mt, getSessionMap());
		// result = getText("success.sms_sent");
		return JSON;

		// //1)生成验证码
		// String s = "1234567890";
		// Random r = new Random();
		// String code = "";
		// for (int i = 0; i < 6; i++) {
		// int n = r.nextInt(9);
		// code += s.substring(n, n + 1);
		// }
		// //2)初始化 session
		// Map session = ActionContext.getContext().getSession();
		// IdentifyCode identifyCode =
		// (IdentifyCode)session.get(Const.IDENTIFY);
		// if(identifyCode == null){
		// identifyCode = new IdentifyCode();
		// }
		// identifyCode.setCode(code);
		// identifyCode.setExpireTime(DateUtils.addSeconds(new Date(), 120));
		// identifyCode.setTelephone(confPass.getMobile());
		// session.put(Const.IDENTIFY, identifyCode);
		// //3)发送短信验证码（3908）
		// identifyCode.setContent("您办理信用卡设置密码业务短信口令为"+identifyCode.getCode()+"。如非本人操作，可能是他人输错手机号，请忽略。");
		// isSuccess =
		// sendIdentifyCodeService.isSendSuccessTr3908(identifyCode);
		// if(isSuccess.equals("fail")){
		// message = "短信验证码发送失败，请重新发送";
		// }
		// logger.info("code:" + code);
		// logger.info("result:" + isSuccess);
//		return "json";
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
