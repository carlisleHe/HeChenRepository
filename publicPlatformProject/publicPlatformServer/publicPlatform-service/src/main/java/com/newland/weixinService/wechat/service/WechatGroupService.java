/**
 * @Title: WechatGroupService.java
 * @Package com.cib.weixin.wechat.service
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:20:10
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.service;

import com.intensoft.dao.Page;
import com.newland.weixinService.wechat.model.WechatGroup;



 /**
 * @ClassName: WechatGroupService
 * @Description: 微信分组表服务 
 * @author hongye
 * @date 2014-3-10 13:20:10
 */
public interface WechatGroupService {

	public WechatGroup findByIdWechatGroup(Integer id);
	
	public void saveWechatGroup(WechatGroup wechatGroup);
	
	public void updateWechatGroup(WechatGroup wechatGroup);

	public void deleteWechatGroup(WechatGroup wechatGroup);
	
	public Page<WechatGroup> findWechatGroup(int startIndex,int pageSize);

}

