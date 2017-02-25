/**
 * @Title: WechatMenuDao.java
 * @Package com.cib.weixin.wechat.dao
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:28
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao;

import java.util.List;

import com.intensoft.dao.GenericDao;
import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatMenu;



 /**
 * @ClassName: WechatMenuDao
 * @Description: 微信菜单DAO 
 * @author hongye
 * @date 2014-3-10 13:21:28
 */
public interface WechatMenuDao extends GenericDao<WechatMenu, Integer> {
	public Page<WechatMenu> findWechatMenu(int startIndex,int pageSize);
	
	public List<WechatMenu> findAll();
	
	public List<WechatMenu> findMenuByAppId(String appId);
}

