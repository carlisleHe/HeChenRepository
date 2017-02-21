package com.cib.alipay.proxy.dao;


import java.util.List;

import com.cib.alipay.proxy.AlipayNode;
import com.intensoft.dao.GenericDao;

public interface AlipayNodeDao extends GenericDao<AlipayNode, Integer>{
	
	public List<AlipayNode> findAll();

}
