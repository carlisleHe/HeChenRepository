/**
 * @Title: WechatUserServiceImpl.java
 * @Package com.cib.weixin.wechat.service.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:29
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.wechat.dao.WechatUserDao;
import com.newland.weixinService.wechat.model.WechatUser;
import com.newland.weixinService.wechat.service.WechatUserService;

/**
 * @ClassName: WechatUserServiceImpl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:29
 */
@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {

	private static final Logger logger = Logger
			.getLogger(WechatUserServiceImpl.class);

	@Resource(name = "accessTokenService")
	private AccessTokenService accessTokenService;

	@Resource(name = "wechatUserDao")
	private WechatUserDao wechatUserDao;

	@Override
	public WechatUser findByIdWechatUser(String id) {
		return wechatUserDao.findById(id);
	}

	@Override
	public WechatUser findWechatUser(String openId, String appId) {
		return wechatUserDao.findWechatUser(openId, appId);
	}

	@Override
	public void saveWechatUser(WechatUser wechatUser) {
		wechatUserDao.save(wechatUser);
	}

	@Override
	public void updateWechatUser(WechatUser wechatUser) {
		wechatUserDao.update(wechatUser);
	}

	@Override
	public void deleteWechatUser(WechatUser wechatUser) {
		wechatUserDao.delete(wechatUser);
	}

	@Override
	public Page<WechatUser> findWechatUser(int startIndex, int pageSize) {
		return wechatUserDao.findWechatUser(startIndex, pageSize);
	}

	@Override
	public void updateWechatUser() {
		//TODO 因方案调整 未定 先删除 没用的
		logger.info("该方法没实现....正在开发中，敬请期待！");

	}
}
