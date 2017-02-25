package com.newland.alipayService.log.dao.impl;

import org.springframework.stereotype.Service;

import com.newland.alipayService.base.AlipayBaseDao;
import com.newland.alipayService.log.dao.CibLogDao;
import com.newland.alipayService.log.model.CibLog;

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
