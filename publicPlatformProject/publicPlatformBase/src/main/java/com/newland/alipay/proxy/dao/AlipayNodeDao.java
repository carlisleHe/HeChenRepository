package com.newland.alipay.proxy.dao;


import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.alipay.proxy.AlipayNode;

public interface AlipayNodeDao extends GenericDao<AlipayNode, Integer>{
	
	public List<AlipayNode> findAll();

}
