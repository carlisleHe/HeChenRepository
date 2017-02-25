/**
 * @Title: WechatGroupServiceImpl.java
 * @Package com.cib.weixin.wechat.service.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:20:12
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.dao.WechatGroupDao;
import com.newland.weixinService.wechat.model.WechatGroup;
import com.newland.weixinService.wechat.service.WechatGroupService;



 /**
 * @ClassName: WechatGroupServiceImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:20:12
 */
@Service("wechatGroupService") 
public class WechatGroupServiceImpl implements  WechatGroupService{

private static final  Logger logger = Logger.getLogger(WechatGroupServiceImpl.class);

	@Resource(name = "wechatGroupDao")
	private WechatGroupDao wechatGroupDao;
	
	@Override
	public WechatGroup findByIdWechatGroup(Integer id){
	     return wechatGroupDao.findById(id);
	}
	
	@Override
	public void saveWechatGroup(WechatGroup wechatGroup) {
		wechatGroupDao.save(wechatGroup);
	}
	
	@Override
	public void updateWechatGroup(WechatGroup wechatGroup){
		wechatGroupDao.update(wechatGroup);
	}

    @Override
	public void deleteWechatGroup(WechatGroup wechatGroup){
	    wechatGroupDao.delete(wechatGroup);
	}

	@Override
	public Page<WechatGroup> findWechatGroup(int startIndex,int pageSize){
		return	wechatGroupDao.findWechatGroup(startIndex, pageSize);
	}
}

