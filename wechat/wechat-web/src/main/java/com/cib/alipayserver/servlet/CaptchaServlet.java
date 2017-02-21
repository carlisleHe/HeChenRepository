/*
 * CaptchaServlet.java
 * 功能：验证码Servlet
 * 类名：CaptchaServlet
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-3-14 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.cib.alipayserver.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newland.wechatServer.common.captcha.CaptchaSecurity;
import com.newland.wechatServer.common.captcha.CaptchaService;

/**
 * 验证码Servlet
 * 
 * @author 黄博飞
 * @version Ver 1.0 2011-3-14
 * @since CaptchaServlet.java Ver 1.0
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 5091552153577874506L;
	private final static Log logger = LogFactory.getLog(CaptchaServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(this.getServletContext());
		CaptchaService captchaService = (CaptchaService) wac
				.getBean("captchaService");

		CaptchaSecurity captchaSecurity = captchaService.genCaptcha();
		request.getSession().setAttribute(CaptchaSecurity.IDENTIFYING_CODE,
				captchaSecurity);
		try {
			File file = captchaService.getCaptchaImage(captchaSecurity);
			if (file.exists()) {
				logger.info("resource path:" + file.getPath());
				FileInputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] bytes = new byte[1024];
				int length = 0;
				while ((length = in.read(bytes)) > 0) {
					out.write(bytes, 0, length);
				}
				response.getOutputStream().write(out.toByteArray());
				response.flushBuffer();
			} else {
				response.sendError(404);
				logger.info("resource not found!");
			}
		} catch (Exception e) {
			response.sendError(404);
			logger.info("resource not found!");
		}

	}

	public void doPose(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		doGet(request, response);
	}
}
