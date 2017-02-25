/**
 * @Title: WechatGroupDaoImpl.java
 * @Package com.cib.weixin.wechat.dao.impl
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:20:08
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao.impl;

import org.springframework.stereotype.Service;

import com.intensoft.dao.Page;
import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.wechat.dao.WechatGroupDao;
import com.newland.weixinService.wechat.model.WechatGroup;

  /**
 * @ClassName: WechatGroupDaoImpl
 * @Description: TODO 
 * @author hongye
 * @date 2014-3-10 13:20:08
 */
@Service("wechatGroupDao") 
public class WechatGroupDaoImpl extends WeixinBaseDao<WechatGroup, Integer> implements WechatGroupDao{

	 public Page<WechatGroup> findWechatGroup(int startIndex,int pageSize){
	    	String hql = " from "+WechatGroup.class.getName()+ " wechatGroup";
			return this.find(hql,startIndex, pageSize);
	    }
   
}

