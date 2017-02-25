/**
 * @Title: WechatUserService.java
 * @Package com.cib.weixin.wechat.service
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:27
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service;

import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatUser;



 /**
 * @ClassName: WechatUserService
 * @Description: 微信用户服务 
 * @author hongye
 * @date 2014-3-10 13:22:27
 */
public interface WechatUserService {

	public WechatUser findByIdWechatUser(String id);
	
	public void saveWechatUser(WechatUser wechatUser);
	
	public void updateWechatUser(WechatUser wechatUser);

	public void deleteWechatUser(WechatUser wechatUser);
	
	public Page<WechatUser> findWechatUser(int startIndex,int pageSize);
	
	//从微信平台取得关注用户
	public void updateWechatUser();

	public WechatUser findWechatUser(String openId, String appId);

}

