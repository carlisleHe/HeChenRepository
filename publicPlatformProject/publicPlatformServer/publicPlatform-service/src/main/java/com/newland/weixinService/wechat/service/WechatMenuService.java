/**
 * @Title: WechatMenuService.java
 * @Package com.cib.weixin.wechat.service
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:32
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service;

import java.util.List;

import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatMenu;



 /**
 * @ClassName: WechatMenuService
 * @Description: 微信菜单服务 
 * @author hongye
 * @date 2014-3-10 13:21:32
 */
public interface WechatMenuService {

	public WechatMenu findByIdWechatMenu(Integer id);
	
	public List<WechatMenu> findAll();
	
	/**
	 * 根据微信服务号查询菜单
	 * @param appId
	 * @return
	 */
	public List<WechatMenu> findMenuByAppId(String appId);
	
	public void saveWechatMenu(WechatMenu wechatMenu);
	
	public void updateWechatMenu(WechatMenu wechatMenu);

	public void deleteWechatMenu(WechatMenu wechatMenu);
	
	public Page<WechatMenu> findWechatMenu(int startIndex,int pageSize);

}

