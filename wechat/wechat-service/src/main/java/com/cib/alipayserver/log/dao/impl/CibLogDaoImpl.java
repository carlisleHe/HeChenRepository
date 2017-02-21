package com.cib.alipayserver.log.dao.impl;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.log.dao.CibLogDao;
import com.cib.alipayserver.log.model.CibLog;

/**
 * 
 * @ClassName: AuthLogDaoImpl  
 * @Description: 授权日志dao实现类 
 * @author: xuzz 
 * @date:2014-3-22 下午05:52:12
 */
@Service("cibLogDao")
public class CibLogDaoImpl extends AlipayBaseDao<CibLog, String> implements
		CibLogDao {

}
