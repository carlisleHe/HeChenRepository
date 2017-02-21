/*
 * CaptchaServiceImpl.java
 * 功能：验证码服务实现
 * 类名：CaptchaServiceImpl
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────
 * 	V1.0  	2011-3-14 	黄博飞         初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
*/
package com.cib.alipayserver.common.captcha.impl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cib.alipayserver.common.captcha.CaptchaSecurity;
import com.cib.alipayserver.common.captcha.CaptchaService;


/**
 * 验证码服务实现
 * @author   黄博飞
 * @version  Ver 1.0 2011-3-14
 * @since    CaptchaServiceImpl.java Ver 1.0
 */
public class CaptchaServiceImpl implements CaptchaService{
	private final static Log logger = LogFactory.getLog(CaptchaServiceImpl.class);
	/**
	 * 图片文件后缀
	 */
	private final static String IMAGE_SUFFIX = ".jpg";
	/**
	 * 图片文件路径
	 */
	private String resourcePath;
	/**
	 * 是否需要验证
	 */
	private boolean needVerify;

	/**
	 * 生成验证码
	 */
	@Override
	public CaptchaSecurity genCaptcha() {
		return new CaptchaSecurity(this.needVerify);		
	}
	@Override
	public File getCaptchaImage(CaptchaSecurity captchaSecurity) throws Exception{
		if(captchaSecurity == null){
			throw new Exception("验证码不存在！");
		}
		File file = new File(this.resourcePath + captchaSecurity.getCaptcha() +IMAGE_SUFFIX);
        int tryNum = 0;
        while(!file.exists() && tryNum++ <= 5){
            captchaSecurity.genNewCaptcha();
            file = new File(this.resourcePath + captchaSecurity.getCaptcha() +IMAGE_SUFFIX);
            logger.info("try general captcha " + tryNum);
        }
        logger.info(file.getPath());
        if (file.exists()){
            return file;
        }else{
        	throw new Exception("resource not found!");
        }
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public void setNeedVerify(boolean needVerify) {
		this.needVerify = needVerify;
	}

}
