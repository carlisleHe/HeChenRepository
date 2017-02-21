/**
 * @Title: AlipayMenuDaoImpl.java
 * @Package com.cib.weixin.wechat.dao.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:30
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.cib.alipayserver.alipay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.alipay.dao.AlipayMenuDao;
import com.cib.alipayserver.alipay.model.AlipayMenu;
import com.cib.alipayserver.base.AlipayBaseDao;
import com.intensoft.dao.Page;

  /**
 * @ClassName: AlipayMenuDaoImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:21:30
 */
@Service("alipayMenuDao") 
public class AlipayMenuDaoImpl extends AlipayBaseDao<AlipayMenu, Integer> implements AlipayMenuDao{

    
    @Override
    public Page<AlipayMenu> findAlipayMenu(int startIndex,int pageSize){
    	
//    	String hql = " from "+AlipayMenu.class.getName()+ " AlipayMenu";
		return this.findAll(startIndex, pageSize);
    }
    @Override
    public List<AlipayMenu> findAll(){
    	String hql = " from "+AlipayMenu.class.getName()+ " wm  order by wm.seq asc";
    	return this.find(hql);
    }
	@Override
	public List<AlipayMenu> findMenuByAppId(String appId) {
		String hql = " from "+AlipayMenu.class.getName()+ " wm where wm.appId=?  order by wm.seq asc";
    	return this.find(hql,appId);
	}
    

}

