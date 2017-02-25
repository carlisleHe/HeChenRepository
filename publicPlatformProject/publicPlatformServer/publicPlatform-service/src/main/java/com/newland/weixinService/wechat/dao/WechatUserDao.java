/**
 * @Title: WechatUserDao.java
 * @Package com.cib.weixin.wechat.dao
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:23
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao;

import com.intensoft.dao.GenericDao;
import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatUser;



 /**
 * @ClassName: WechatUserDao
 * @Description: 微信用户DAO
 * @author hongye
 * @date 2014-3-10 13:22:23
 */
public interface WechatUserDao extends GenericDao<WechatUser, String> {
	public Page<WechatUser> findWechatUser(int startIndex,int pageSize);
	
	public WechatUser findWechatUser(String openId, String appId);
}

