package com.newland.wechat.proxy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.intensoft.dao.hibernate.HibernateGenericDao;
import com.newland.wechat.proxy.WechatNode;
import com.newland.wechat.proxy.dao.WechatNodeDao;

@Service("wechatNodeDao") 
public class WechatNodeDaoImpl extends HibernateGenericDao<WechatNode, Integer> implements WechatNodeDao {

	@Override
	@Resource(name="weiXinSessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		
	}
	
	@Override  
	public List<WechatNode> findAll(){ 
		return super.findAll();
	}

}
