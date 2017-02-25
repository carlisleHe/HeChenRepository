/**
 * @Title: WechatMenuServiceImpl.java
 * @Package com.cib.weixin.wechat.service.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:33
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.dao.WechatMenuDao;
import com.newland.weixinService.wechat.model.WechatMenu;
import com.newland.weixinService.wechat.service.WechatMenuService;



 /**
 * @ClassName: WechatMenuServiceImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:21:33
 */
@Service("wechatMenuService") 
public class WechatMenuServiceImpl implements  WechatMenuService{

private static final  Logger logger = Logger.getLogger(WechatMenuServiceImpl.class);

	@Resource(name = "wechatMenuDao")
	private WechatMenuDao wechatMenuDao;
	
	@Override
	public WechatMenu findByIdWechatMenu(Integer id){
	     return wechatMenuDao.findById(id);
	}
	
	@Override
	public void saveWechatMenu(WechatMenu wechatMenu) {
		wechatMenuDao.save(wechatMenu);
	}
	
	@Override
	public void updateWechatMenu(WechatMenu wechatMenu){
		wechatMenuDao.update(wechatMenu);
	}

    @Override
	public void deleteWechatMenu(WechatMenu wechatMenu){
	    wechatMenuDao.delete(wechatMenu);
	}

	@Override
	public Page<WechatMenu> findWechatMenu(int startIndex,int pageSize){
		return	wechatMenuDao.findWechatMenu(startIndex, pageSize);
	}

	@Override
	public List<WechatMenu> findAll() {
		return wechatMenuDao.findAll();
	}

	@Override
	public List<WechatMenu> findMenuByAppId(String appId) {
		return wechatMenuDao.findMenuByAppId(appId);
	}
}

