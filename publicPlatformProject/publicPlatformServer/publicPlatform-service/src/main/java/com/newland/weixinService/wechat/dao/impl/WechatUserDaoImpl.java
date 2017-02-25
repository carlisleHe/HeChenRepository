/**
 * @Title: WechatUserDaoImpl.java
 * @Package com.cib.weixin.wechat.dao.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:25
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao.impl;

import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.wechat.dao.WechatUserDao;
import com.newland.weixinService.wechat.model.WechatUser;

  /**
 * @ClassName: WechatUserDaoImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:22:25
 */
@Service("wechatUserDao") 
public class WechatUserDaoImpl extends WeixinBaseDao<WechatUser, String> implements WechatUserDao{


    
    @Override
    public Page<WechatUser> findWechatUser(int startIndex,int pageSize){
    	String hql = " from "+WechatUser.class.getName()+ " wechatUser";
		return this.find(hql,startIndex, pageSize);
    }

	@Override
	public WechatUser findWechatUser(String openId, String appId) {
		String hql = " from "+WechatUser.class.getName()+ " wu where wu.openId=? and wu.appId=?";
		return (WechatUser) this.findUnique(hql, openId,appId);
	}

}

