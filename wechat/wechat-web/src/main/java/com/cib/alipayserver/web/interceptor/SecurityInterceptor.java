/**
 * @Title: SecurityInterceptor.java
 * @Package com.cib.persmanager.security
 * @Description: TODO
 * @author hongye
 * @date 2012-12-19 下午3:50:27
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */

package com.cib.alipayserver.web.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.intensoft.util.StringUtils;
import com.newland.wechatServer.common.Const;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @ClassName: SecurityInterceptor
 * @Description: 系统安全过滤器 用于处理会话过期,URL访问控制
 * @author hongye
 * @date 2014-4-3 下午10:50:27
 */

public class SecurityInterceptor implements Interceptor {
	
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	private static final long serialVersionUID = 1L;
	private static final String EXECUTE="execute";
	private static final String ACCESSTOKEN="accessToken";
	 public static final String GLOABL_ERROR_MSG = "GLOABL_ERROR_MSG";


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}


	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.debug("SecurityInterceptor--------------");
		//取得Action和方法名
		String methodName = invocation.getProxy().getMethod();
		/**
		 * hechen
		 */
		if (StringUtils.isNotBlank(methodName)) {
			//因Action比较少；直接写methodName名
			if(EXECUTE.equals(methodName)||methodName.startsWith(ACCESSTOKEN)){
				return invocation.invoke();
			}
		}
		
		/***进行身份认证*/
		HttpSession session = ServletActionContext.getRequest().getSession(true);
		String uc = (String)session.getAttribute(Const.OPEN_ID);
		if(uc==null){
			logger.error("请求超时，请重新输入");
			ServletActionContext.getRequest().setAttribute(GLOABL_ERROR_MSG, "请求超时，请重新输入");
			return "globalError";
		}
		return invocation.invoke();
	}

}
