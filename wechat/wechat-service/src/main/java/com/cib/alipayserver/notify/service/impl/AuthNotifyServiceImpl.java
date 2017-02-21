package com.cib.alipayserver.notify.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cib.alipayserver.common.AppExCode;
import com.cib.alipayserver.common.Const;
import com.cib.alipayserver.idgen.service.IdentifierGenerator;
import com.cib.alipayserver.log.model.CibLog;
import com.cib.alipayserver.log.service.CibLogService;
import com.cib.alipayserver.notify.dao.AuthNotifyDao;
import com.cib.alipayserver.notify.model.AuthNotify;
import com.cib.alipayserver.notify.service.AuthNotifyService;
import com.cib.alipayserver.util.AccountUtil;
import com.intensoft.exception.AppRTException;

/**
 * 
 * @ClassName: AuthNotifyServiceImpl
 * @Description: 通知授权服务实现类
 * @author: xuzz
 * @date:2014-3-20 下午07:24:52
 */
@Service("authNotifyService")
public class AuthNotifyServiceImpl implements AuthNotifyService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthNotifyService.class);
	@Resource(name = "authNotifyDao")
	private AuthNotifyDao authNotifyDao;
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	@Resource(name = "cibLogService")
	private CibLogService cibLogService;
	@Override
	@Transactional(value = "alipayTransactionManager")
	public AuthNotify bindAuthNotify(AuthNotify authNotify) {
		Date today =  new Date();
		// 1、申请通知授权流水
		CibLog authLog = cibLogService.applyAuthLogByAuthNotify(authNotify,Const.AUTH_LOG_BIND);
		try {
			// 2、绑定通知授权信息
			// 查询是否已开通动户通知
			AuthNotify queryAuthNotify = authNotifyDao.findByClientNotifyTypeWithoutStatus(
					authNotify.getAppId(), authNotify.getAppUserId(),
					authNotify.getAcctNo(), authNotify.getNotifyType());
			AuthNotify persist = null;
			if (queryAuthNotify == null) {
				//只有借记卡才有核心客户号
				if(AccountUtil.isDebitCard(authNotify.getAcctNo())){
					authNotify.setCtspNo(AccountUtil.calcCibAcctNo(authNotify.getAcctNo()).substring(0,10));
				}
				else{
					authNotify.setCtspNo(authNotify.getAcctNo());
				}
				authNotify.setNotifyId(snoGenerator.generate());

				authNotify.setCreDate(today);
				authNotify.setUpdTime(today);
				authNotify.setValid(Const.STATUS_VALID);
				authNotifyDao.save(authNotify);
				persist = authNotify;
			}
			// 更新状态为有效
			else if (Const.STATUS_INVALID.equals(queryAuthNotify.getValid())) {
				queryAuthNotify.setUpdTime(today);
				queryAuthNotify.setValid(Const.STATUS_VALID);
				queryAuthNotify.setCustName(authNotify.getCustName());
				authNotifyDao.update(queryAuthNotify);
				persist = queryAuthNotify;
			} else {
				//考虑到目前现实生产环境中，绑定卡时会默认开通动户通知，而绑卡与开通动户通知又是分属于weibank与openbank数据库的，无法在一个事务里完成操作
				//考虑到业务的实现顺序为1、进行卡绑定2、开通动户通知，所以这里的事务配置决定了动户通知信息会被先执行，如果此刻绑定卡失败，会造成动户通知成功的情况
				//基于上述理由允许重复开通动户通知，依赖于前端进行控制
				logger.warn(String.format("重复开通通知！通知类型:[%s]，卡号:[%s]", authNotify.getNotifyType(),authNotify.getAcctNo()));
				authLog.setRemark(String.format("重复开通通知！通知类型:[%s]，卡号:[%s]", authNotify.getNotifyType(),authNotify.getAcctNo()));
				persist = queryAuthNotify;
			}
			// 3、更新流水状态为成功
			authLog.setStatus(Const.STATUS_SUCCESS);
			cibLogService.updAuthLog(authLog);
			return persist;
		} catch (AppRTException e) {
			authLog.setStatus(Const.STATUS_FAIL);
			authLog.setRemark(String.format("通知授权绑定失败！卡号：[%s]，原因:[%s]",
					authNotify.getAcctNo(), e.getMessage()));
			cibLogService.updAuthLog(authLog);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			authLog.setStatus(Const.STATUS_FAIL);
			authLog.setRemark(String.format("通知授权绑定失败！卡号：[%s]，原因:[%s]",
					authNotify.getAcctNo(), e.getMessage()));
			cibLogService.updAuthLog(authLog);
			throw new AppRTException(AppExCode.UNKNOWN, e.getMessage());
		}

	}

	@Override
	public AuthNotify findAuthNotify(String appId, String appUserId,
			String acctNo, String... notifyTypes) {
		return authNotifyDao.findByClientNotifyType(appId, appUserId, acctNo,
				notifyTypes);
	}

	@Override
	@Transactional(value = "alipayTransactionManager")
	public void unbindAuthNotify(AuthNotify authNotify) {
		Date today =  new Date();
		// 查询通知授权信息
		AuthNotify queryAuthNotify = authNotifyDao.findByClientNotifyType(
				authNotify.getAppId(), authNotify.getAppUserId(),
				authNotify.getAcctNo(), authNotify.getNotifyType());
		CibLog authLog = null;
		try {
			if (queryAuthNotify == null) {
				logger.warn( String.format("不存在的通知授权信息！通知类型:[%s],账号:[%s]",authNotify.getNotifyType(), authNotify.getAcctNo()));
			}
			// 1、登记通知授权流水
			authLog = cibLogService.applyAuthLogByAuthNotify(queryAuthNotify,
					Const.AUTH_LOG_UNBIND);
			// 已解绑的不能重复绑定
			if (Const.STATUS_INVALID.equals(queryAuthNotify.getValid())) {
				//考虑到目前现实生产环境中，绑定卡时会默认开通动户通知，而绑卡与开通动户通知又是分属于weibank与openbank数据库的，无法在一个事务里完成操作
				//考虑到业务的实现顺序为1、进行卡绑定2、开通动户通知，所以这里的事务配置决定了动户通知信息会被先执行，如果此刻绑定卡失败，会造成动户通知成功的情况
				//基于上述理由允许重复开通、关闭动户通知，依赖于前端进行控制
				logger.warn(String.format("重复解绑！通知类型:[%s],账号:[%s]", authNotify.getNotifyType(),authNotify.getAcctNo()));
				authLog.setRemark(String.format("重复解绑！通知类型:[%s],账号:[%s]", authNotify.getNotifyType(),authNotify.getAcctNo()));
			}
			// 2、更新通知绑定状态为无效
			else {
				queryAuthNotify.setValid(Const.STATUS_INVALID);
				queryAuthNotify.setUpdTime(today);
				authNotifyDao.update(queryAuthNotify);
			}
			// 3、更新流水状态为成功
			authLog.setStatus(Const.STATUS_SUCCESS);
			cibLogService.updAuthLog(authLog);
		} catch (AppRTException e) {
			if (authLog != null) {
				authLog.setStatus(Const.STATUS_FAIL);
				authLog.setRemark(String.format("解除通知授权绑定失败！通知类型：[%s],卡号：[%s]",
						queryAuthNotify.getNotifyType(),
						queryAuthNotify.getAcctNo()));
				cibLogService.updAuthLog(authLog);
			}
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (authLog != null) {
				authLog.setStatus(Const.STATUS_FAIL);
				authLog.setRemark(String.format("解除通知授权绑定失败！通知类型：[%s],卡号：[%s]",
						queryAuthNotify.getNotifyType(),
						queryAuthNotify.getAcctNo()));
				cibLogService.updAuthLog(authLog);
			}
		}

	}

	@Override
	public List<AuthNotify> findAuthNotifyByAcctNo(String appId,
			String appUserId, String acctNo) {
		return authNotifyDao.findByAcctNo(appId, appUserId, acctNo);
	}

	@Override
	public AuthNotify updAuthNotify(AuthNotify authNotify) {
		return authNotifyDao.update(authNotify);
	}

}
