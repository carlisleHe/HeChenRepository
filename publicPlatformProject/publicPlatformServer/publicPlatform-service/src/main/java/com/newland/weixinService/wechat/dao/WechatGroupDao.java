/**
 * @Title: WechatGroupDao.java
 * @Package com.cib.weixin.wechat.dao
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:20:07
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.dao;

import com.intensoft.dao.GenericDao;
import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatGroup;



 /**
 * @ClassName: WechatGroupDao
 * @Description: 微信组服务 
 * @author hongye
 * @date 2014-3-10 13:20:07
 */
public interface WechatGroupDao extends GenericDao<WechatGroup, Integer> {
	public Page<WechatGroup> findWechatGroup(int startIndex,int pageSize);
}

