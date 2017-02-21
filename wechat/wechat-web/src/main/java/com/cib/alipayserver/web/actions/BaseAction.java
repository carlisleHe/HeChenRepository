package com.cib.alipayserver.web.actions;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.post.AlipayPostUtils;
import com.cib.alipayserver.web.annotation.ExecuteResult;
import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.intensoft.formater.DateFormatter;
import com.intensoft.scopeplugin.ScopeType;
import com.intensoft.scopeplugin.annotations.In;
import com.intensoft.scopeplugin.annotations.Out;
import com.newland.wechatServer.common.AppExCode;
import com.newland.wechatServer.message.MsgService;
import com.newland.wechatServer.message.model.MessageTemplate;
import com.newland.wechatServer.util.SmsSecurity;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseAction.class);

	public final static String SUCCESS = "success";
	public final static String INPUT = "input";
	public final static String LIST = "list";
	public final static String INDEX = "index";
	public final static String ERROR = "error";
	public final static String PARAM = "param";
	public final static String INFO = "info";
	public static final String JSON = "json";
	public final static String GLOBAL_ERROR = "globalError";
	public final static String GLOBAL_JSON_ERROR = "jsonError";
	public final static String ALLINONE_PARAM = "param";
	public final static String ENCRIPTED_ALLINONE_PARAM = "p";
	private HttpServletRequest request;
	private HttpServletResponse response;

	@Resource(name = "msgService")
	protected MsgService msgService;

	/**
	 * 发送短信模板
	 */
	@In(scope = ScopeType.CONVERSATION)
	@Out(scope = ScopeType.CONVERSATION)
	protected MessageTemplate messageTemplate;

	protected Map<String, Object> sessionMap;

	@Value("@[alipay_snsapi_base_url]")
	private String snsapiBaseUrl;
	/**
	 * 短信口令
	 */
	private String hsendSms;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3731652188707289832L;

	public String getHsendSms() {
		return hsendSms;
	}

	public void setHsendSms(String hsendSms) {
		this.hsendSms = hsendSms;
	}

	protected HttpServletRequest getRequest() {

		return this.request;
	}

	/**
	 * Convenience method to get the response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * Convenience method to get the session. This will create a session if one
	 * doesn't exist.
	 * 
	 * @return the session from the request (request.getSession()).
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected Map<String, Object> getSessionMap() {
		return this.sessionMap;
	}

	public String getClientIp() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getRemoteAddr();
	}

	public String getAgent() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getHeader("User-Agent");
	}

	protected Object getBean(String beanName) {
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
		return wac.getBean(beanName);
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	protected void sendJSONObject(JSONObject object) throws IOException {
		getResponse().setHeader("Pragma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setDateHeader("Expires", 0);
		getResponse().setContentType("application/json;charset=UTF-8");
		getResponse().getWriter().write(object.toString());
	}

	public String dateFormat(Date date) {
		return DateFormatter.format(date, "yyyy-MM-dd");
	}

	/**
	 * 网页授权
	 * 
	 * @throws IOException
	 */
	@ExecuteResult(name = GLOBAL_ERROR)
	public void oauthWeb(String authAccesstokenUrl) throws IOException {
		String appId = AlipayApplication.getAppId();
		String get_code_url = snsapiBaseUrl.replace("APPID", appId).replace(
				"ENCODED_URL", authAccesstokenUrl + "?" + Math.random());
		logger.debug("get_code_url:" + get_code_url);
		ServletActionContext.getResponse().sendRedirect(get_code_url);
	}

	/**
	 * 获得OpenId返回
	 * 
	 * @throws AppBizException
	 * 
	 * @throws IOException
	 */
	public String accessToken(String code) throws AppBizException {
		logger.debug("请求传进来code:" + code);
		if (StringUtils.isBlank(code)) {
			throw new AppBizException(AppExCode.REQ_PARAM_ERROR, "请求参数不合法");
		}
		try {
			String openId = getOpenId(code);
			return openId;
		} catch (Exception e) {
			logger.error("获取assect_token失败" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取 openId
	 */
	public String getOpenId(String code) throws Exception {
		return AlipayPostUtils.getOauthOpenId(code);
	}

	/**
	 * 验证短信认证码。未登录
	 * 
	 * @return
	 */
	protected boolean verifySms() throws AppBizException {
		if (StringUtils.isBlank(hsendSms)) {
			throw new AppBizException("exception.no_found_val_sms", "请输入短信认证码！");
		}
		SmsSecurity authCode = (SmsSecurity) sessionMap
				.get(MsgService.SMS_SENT);
		if (authCode == null) {
			throw new AppRTException("VAL_SMS_NULL", "短信认证码已失效，请重新获取！");
		} else if (authCode.isInvalidStatus()) {
			throw new AppRTException("VAL_SMS_INVALID", "短信认证码已失效，请重新获取！");
		} else if (authCode.expire()) {
			throw new AppRTException("VAL_SMS_INVALIDATION", "短信认证码超时已失效");
		} else if (!authCode.isAvailability()) {
			throw new AppRTException("VAL_SMS_EXPIRE", "短信认证码6次错误已失效！");
		} else if (authCode.valid(hsendSms)) {
			return true;
		} else {
			if (!authCode.isAvailability()) {
				throw new AppRTException("VAL_SMS_EXPIRE", "短信认证码6次错误已失效！");
			}
			// 删除短信验证码输入错误次数提醒 20140807 zhangzhan
			throw new AppRTException("VAL_SMS_ERROR",getText("exception.error_sms"));
		}
	}

	protected void addSessionObj(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	protected Object getSessionObj(String key) {
		return getSession().getAttribute(key);
	}

	protected void setMessageTemplate() {
		this.addSessionObj("MessageTemplate", messageTemplate);
	}

	protected void setMessageTemplate(MessageTemplate msgTemplate) {
		this.addSessionObj("MessageTemplate", msgTemplate);
	}

	protected MessageTemplate getMessageTemplate() {
		return (MessageTemplate) getSessionObj("MessageTemplate");
	}

}
