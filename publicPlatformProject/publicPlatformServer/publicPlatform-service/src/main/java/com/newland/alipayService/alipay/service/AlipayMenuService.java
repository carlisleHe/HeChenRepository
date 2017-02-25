/**
 * @Title: AlipayMenuService.java
 * @Package com.cib.weixin.wechat.service
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:32
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.alipayService.alipay.service;

import java.util.List;

import com.intensoft.dao.Page;
import com.newland.alipayService.alipay.model.AlipayMenu;



 /**
 * @ClassName: AlipayMenuService
 * @Description: 微信菜单服务 
 * @author hongye
 * @date 2014-3-10 13:21:32
 */
public interface AlipayMenuService { 

	public AlipayMenu findByIdAlipayMenu(Integer id);
	
	public List<AlipayMenu> findAll();
	
	/**
	 * 根据微信服务号查询菜单
	 * @param appId
	 * @return
	 */
	public List<AlipayMenu> findMenuByAppId(String appId);
	
	public void saveAlipayMenu(AlipayMenu alipayMenu);
	
	public void updateAlipayMenu(AlipayMenu alipayMenu);

	public void deleteAlipayMenu(AlipayMenu alipayMenu);
	
	public Page<AlipayMenu> findAlipayMenu(int startIndex,int pageSize);

}

