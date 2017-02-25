/**
 * @Title: WechatMenuDaoImpl.java
 * @Package com.cib.weixin.wechat.dao.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:30
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.wechat.dao.WechatMenuDao;
import com.newland.weixinService.wechat.model.WechatMenu;

  /**
 * @ClassName: WechatMenuDaoImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:21:30
 */
@Service("wechatMenuDao") 
public class WechatMenuDaoImpl extends WeixinBaseDao<WechatMenu, Integer> implements WechatMenuDao{

    
    @Override
    public Page<WechatMenu> findWechatMenu(int startIndex,int pageSize){
    	String hql = " from "+WechatMenu.class.getName()+ " wechatMenu";
		return this.find(hql,startIndex, pageSize);
    }
    @Override
    public List<WechatMenu> findAll(){
    	String hql = " from "+WechatMenu.class.getName()+ " wm  order by wm.seq asc";
    	return this.find(hql);
    }
	@Override
	public List<WechatMenu> findMenuByAppId(String appId) {
		String hql = " from "+WechatMenu.class.getName()+ " wm where wm.appId=?  order by wm.seq asc";
    	return this.find(hql,appId);
	}
    

}

