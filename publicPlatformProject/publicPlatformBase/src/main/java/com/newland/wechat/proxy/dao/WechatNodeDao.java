package com.newland.wechat.proxy.dao;


import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.wechat.proxy.WechatNode;

public interface WechatNodeDao extends GenericDao<WechatNode, Integer>{
	
	public List<WechatNode> findAll();

}
