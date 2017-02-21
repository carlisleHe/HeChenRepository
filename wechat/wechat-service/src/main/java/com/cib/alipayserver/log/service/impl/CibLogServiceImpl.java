package com.cib.alipayserver.log.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cib.alipayserver.card.model.BindCard;
import com.cib.alipayserver.common.Const;
import com.cib.alipayserver.customer.model.Customer;
import com.cib.alipayserver.idgen.service.IdentifierGenerator;
import com.cib.alipayserver.log.dao.CibLogDao;
import com.cib.alipayserver.log.model.CibLog;
import com.cib.alipayserver.log.service.CibLogService;
import com.cib.alipayserver.notify.model.AuthNotify;

/**
 * 
 * @ClassName: AuthLogServiceImpl  
 * @Description: 授权日志服务实现 
 * @author: xuzz 
 * @date:2014-3-20 下午07:15:53
 */
@Service("cibLogService")
public class CibLogServiceImpl implements CibLogService{
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	@Resource(name = "cibLogDao")
	private CibLogDao authLogDao;

	@Override
	@Transactional(value= "alipayTransactionManager",propagation=Propagation.REQUIRES_NEW)
	public CibLog applyAuthLogByAuthCard(BindCard authCard,int type) {
		CibLog authLog = new CibLog();
		authLog.setSno(snoGenerator.generate());
		authLog.setAcctNo(authCard.getAcctNo());
		authLog.setAcctType(authCard.getAcctType());
		authLog.setAppId(authCard.getAppId());
		authLog.setAuthType(Const.AUTH_TYPE_CARD);
		authLog.setCertNo(authCard.getCertNo());
		authLog.setCertType(authCard.getCertType());
		authLog.setOpenId(authCard.getOpenId());
		authLog.setCustName(authCard.getCustName());
		authLog.setLogDate(new Date());
		authLog.setLogTime(new Date());
		authLog.setMobile(authCard.getMobile());
		authLog.setStatus(Const.STATUS_WAIT);
		if(Const.AUTH_LOG_BIND==type){
			authLog.setRemark(String.format("绑定卡，应用ID：[%s],客户ID：[%s],卡号：[%s],%s！", authCard.getAppId(),authCard.getOpenId(),authCard.getAcctNo(),Const.AUTH_LOG_BIND==type?"绑定卡授权":"解绑卡授权"));
		}
		else{
			authLog.setRemark(String.format("解绑卡，应用ID：[%s],客户ID：[%s],卡号：[%s],%s！", authCard.getAppId(),authCard.getOpenId(),authCard.getAcctNo(),Const.AUTH_LOG_BIND==type?"绑定卡授权":"解绑卡授权"));
		}
		authLogDao.save(authLog);
		return authLog;
	}

	

	@Override
	@Transactional(value= "alipayTransactionManager",propagation=Propagation.REQUIRES_NEW)
	public CibLog saveAuthLog(CibLog authLog) {
		return authLogDao.save(authLog);
	}

	@Override
	@Transactional(value= "alipayTransactionManager")
	public CibLog updAuthLog(CibLog authLog) {
		return authLogDao.update(authLog);
	}
	
	@Override
	@Transactional(value= "alipayTransactionManager",propagation=Propagation.REQUIRES_NEW)
	public CibLog applyAuthLogByAuthCustomer(Customer authCustomer,int type) {
		Date tody = new Date();
		CibLog authLog = new CibLog();
		authLog.setSno(snoGenerator.generate());
		authLog.setAppId(authCustomer.getAppId());
		authLog.setOpenId(authCustomer.getOpenId());
		authLog.setAuthType(Const.AUTH_TYPE_CUSTOMER);
		authLog.setLogDate(tody);
		authLog.setLogTime(tody);
		authLog.setCustName(authCustomer.getCustName());
		authLog.setCertNo(authCustomer.getCertNo());
		authLog.setCertType(authCustomer.getCertType());
		authLog.setStatus(Const.STATUS_WAIT);
		if(Const.AUTH_LOG_BIND==type){
			authLog.setRemark(String.format("绑定用户授权，应用ID：[%s],客户ID：[%s],%s！",authCustomer.getAppId(),authCustomer.getOpenId(),Const.AUTH_LOG_BIND==type?"绑定用户授权":"解绑用户授权"));
		}
		else{
			authLog.setRemark(String.format("解绑用户授权，应用ID：[%s],客户ID：[%s],%s！",authCustomer.getAppId(),authCustomer.getOpenId(), Const.AUTH_LOG_BIND==type?"绑定用户授权":"解绑用户授权"));
		}
		authLogDao.save(authLog);
		return authLog;
	}



	@Override
	public CibLog applyAuthLogByAuthNotify(AuthNotify authNotify, int type) {
		CibLog authLog = new CibLog();
		authLog.setSno(snoGenerator.generate());
		authLog.setAcctNo(authNotify.getAcctNo());
		authLog.setAcctType(authNotify.getAcctType());
		authLog.setAppId(authNotify.getAppId());
		authLog.setAuthType(Const.AUTH_TYPE_NOTIFY);
		authLog.setLogDate(new Date());
		authLog.setLogTime(new Date());
		authLog.setCustName(authNotify.getCustName());
		authLog.setStatus(Const.STATUS_WAIT);
		/**
		 * 添加openId
		 */
		authLog.setOpenId(authNotify.getAppUserId());
		if(Const.AUTH_LOG_BIND==type){
			authLog.setRemark(String.format("绑定通知授权，应用ID：[%s],客户ID：[%s],卡号：[%s],%s！", authNotify.getAppId(),authNotify.getAppUserId(),authNotify.getAcctNo(),Const.AUTH_LOG_BIND==type?"绑定通知授权":"解绑通知授权"));
		}
		else{
			authLog.setRemark(String.format("解绑通知授权，应用ID：[%s],客户ID：[%s],卡号：[%s],%s！", authNotify.getAppId(),authNotify.getAppUserId(),authNotify.getAcctNo(),Const.AUTH_LOG_BIND==type?"绑定通知授权":"解绑通知授权"));
		}
		authLogDao.save(authLog);
		return authLog;
	}

}
