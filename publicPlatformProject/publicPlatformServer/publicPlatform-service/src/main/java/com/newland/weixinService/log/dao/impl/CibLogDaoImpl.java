package com.newland.weixinService.log.dao.impl;

import org.springframework.stereotype.Service;

import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.log.dao.CibLogDao;
import com.newland.weixinService.log.model.CibLog;

/**
 * 
 * @ClassName: AuthLogDaoImpl  
 * @Description: 授权日志dao实现类 
 * @author: xuzz 
 * @date:2014-3-22 下午05:52:12
 */
@Service("cibLogDao")
public class CibLogDaoImpl extends WeixinBaseDao<CibLog, String> implements
		CibLogDao {

}
