package com.newland.alipay.proxy.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.intensoft.dao.hibernate.HibernateGenericDao;
import com.newland.alipay.proxy.AlipayNode;
import com.newland.alipay.proxy.dao.AlipayNodeDao;

@Service("alipayNodeDao") 
public class AlipayNodeDaoImpl extends HibernateGenericDao<AlipayNode, Integer> implements AlipayNodeDao {

	@Override
	@Resource(name="alipaySessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		
	}
	
	@Override  
	public List<AlipayNode> findAll(){ 
		return super.findAll();
	}

}
