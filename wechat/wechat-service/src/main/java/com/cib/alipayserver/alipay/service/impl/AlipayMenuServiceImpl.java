/**
 * @Title: AlipayMenuServiceImpl.java
 * @Package com.cib.weixin.wechat.service.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:33
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.cib.alipayserver.alipay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.alipay.dao.AlipayMenuDao;
import com.cib.alipayserver.alipay.model.AlipayMenu;
import com.cib.alipayserver.alipay.service.AlipayMenuService;
import com.intensoft.dao.Page;



 /**
 * @ClassName: AlipayMenuServiceImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:21:33
 */
@Service("alipayMenuService") 
public class AlipayMenuServiceImpl implements AlipayMenuService{

	@Resource(name = "alipayMenuDao")
	private AlipayMenuDao alipayMenuDao;
	 
	@Override
	public AlipayMenu findByIdAlipayMenu(Integer id){
	     return alipayMenuDao.findById(id);
	}
	
	@Override
	public void saveAlipayMenu(AlipayMenu alipayMenu) {
		alipayMenuDao.save(alipayMenu);
	}
	
	@Override
	public void updateAlipayMenu(AlipayMenu alipayMenu){
		alipayMenuDao.update(alipayMenu);
	}

    @Override
	public void deleteAlipayMenu(AlipayMenu alipayMenu){
    	alipayMenuDao.delete(alipayMenu);
	}

	@Override
	public Page<AlipayMenu> findAlipayMenu(int startIndex,int pageSize){
		return	alipayMenuDao.findAlipayMenu(startIndex, pageSize);
	}

	@Override
	public List<AlipayMenu> findAll() {
		return alipayMenuDao.findAll();
	}

	@Override
	public List<AlipayMenu> findMenuByAppId(String appId) {
		return alipayMenuDao.findMenuByAppId(appId);
	}
}

