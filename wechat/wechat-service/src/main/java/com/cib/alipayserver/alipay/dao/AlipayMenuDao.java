/**
 * @Title: AlipayMenuDao.java
 * @Package com.cib.weixin.wechat.dao
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:28
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.cib.alipayserver.alipay.dao;

import java.util.List;

import com.cib.alipayserver.alipay.model.AlipayMenu;
import com.intensoft.dao.GenericDao;
import com.intensoft.dao.Page;



 /**
 * @ClassName: AlipayMenuDao
 * @Description: 微信菜单DAO 
 * @author hongye
 * @date 2014-3-10 13:21:28
 */
public interface AlipayMenuDao extends GenericDao<AlipayMenu, Integer> { 
	public Page<AlipayMenu> findAlipayMenu(int startIndex,int pageSize);
	
	public List<AlipayMenu> findAll();
	
	public List<AlipayMenu> findMenuByAppId(String appId);
}

