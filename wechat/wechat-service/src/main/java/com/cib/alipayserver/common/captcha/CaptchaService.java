/*
 * CaptchaService.java
 * 功能：验证码服务接口
 * 类名：CaptchaService
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-3-14 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
*/
package com.cib.alipayserver.common.captcha;

import java.io.File;


/**
 * 验证码服务接口
 * @author   黄博飞
 * @version  Ver 1.0 2011-3-14
 * @since    CaptchaService.java Ver 1.0
 */
public interface CaptchaService {
	/**
	 * 生成验证码
	 */
	public CaptchaSecurity genCaptcha();
	/**
	 * 获取验证码图片
	 * @param captchaSecurity
	 * @return
	 */
	public File getCaptchaImage(CaptchaSecurity captchaSecurity) throws Exception;
}
