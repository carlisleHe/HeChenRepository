package com.newland.alipayService.customer.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intensoft.exception.AppRTException;
import com.newland.alipay.env.AlipayApplication;
import com.newland.alipayService.card.model.BindCard;
import com.newland.alipayService.card.service.BindCardService;
import com.newland.alipayService.customer.dao.CustomerDao;
import com.newland.alipayService.customer.model.Customer;
import com.newland.alipayService.customer.service.CustomerService;
import com.newland.alipayService.idgen.service.IdentifierGenerator;
import com.newland.alipayService.log.model.CibLog;
import com.newland.alipayService.log.service.CibLogService;
import com.newland.alipayService.notify.model.AuthNotify;
import com.newland.alipayService.notify.service.AuthNotifyService;
import com.newland.base.common.AppExCode;
import com.newland.base.common.Const;
import com.newland.base.util.AccountUtil;
/**
 * 
 * @ClassName: CustomerServiceImpl  
 * @Description: 用户授权服务类 
 * @author: xuzz 
 * @date:2014-3-20 下午04:04:19
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	@Resource(name = "customerDao")
	private CustomerDao  customerDao;
	@Resource(name = "bindCardService")
	private BindCardService bindCardService;
	@Resource(name = "authNotifyService")
	private AuthNotifyService authNotifyService;
	@Resource(name = "cibLogService")
	private CibLogService cibLogService;

	@Override
	@Transactional
	public Customer bindAuthCustomer(Customer authCustomer) {
		//申请流水
		CibLog cibLog = cibLogService.applyAuthLogByAuthCustomer(authCustomer, Const.AUTH_LOG_BIND);
		try{
			//1、查询用户授权绑定信息
			String appId = authCustomer.getAppId();
			String openId = authCustomer.getOpenId();
			Customer persisitAuthCustomer = null; 
			Customer queryAuthCustomer = customerDao.findByClientIdWithoutStatus(appId, openId);
			//不存在用户授权信息则直接保存
			if(queryAuthCustomer == null){
				authCustomer.setCustId(snoGenerator.generate());
				authCustomer.setUpdTime(new Date());
				authCustomer.setCreDate(new Date());
				authCustomer.setValid(Const.STATUS_VALID);
				customerDao.save(authCustomer);
				persisitAuthCustomer = authCustomer;
			}
			else{
				queryAuthCustomer.setCertNo(authCustomer.getCertNo());
				queryAuthCustomer.setCertType(authCustomer.getCertType());
				queryAuthCustomer.setCustName(authCustomer.getCustName());
				queryAuthCustomer.setValid(Const.STATUS_VALID);
				queryAuthCustomer.setDefaultCCCard(authCustomer.getDefaultCCCard());
				queryAuthCustomer.setDefaultDCCard(authCustomer.getDefaultDCCard());
				queryAuthCustomer.setBrNo(authCustomer.getBrNo());
				queryAuthCustomer.setSubBrNo(authCustomer.getSubBrNo());
				queryAuthCustomer.setUpdTime(new Date());
				customerDao.update(queryAuthCustomer);
				persisitAuthCustomer = queryAuthCustomer;
			}
			//2、更新流水信息
			cibLog.setStatus(Const.STATUS_SUCCESS);
			cibLogService.updAuthLog(cibLog);
			return persisitAuthCustomer;
		}
		catch(AppRTException e){
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("用户授权绑定失败！原因:[%s]", e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("用户授权绑定失败！原因:[%s]", e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw new AppRTException(AppExCode.UNKNOWN, e.getMessage());
		}
		
		
	}

	@Override
	public Customer findAuthCustomerByClientId(String appId,String openId) {
		Customer authCustomer =  customerDao.findByClientId(appId, openId);
		return authCustomer;
	}

	@Override
	public Customer updAuthCustomer(Customer authCustomer) {
		return customerDao.update(authCustomer);
	}

	@Override
	@Transactional
	public void unbindAuthCustomer(Customer authCustomer) {
	   String openId = authCustomer.getOpenId();
	   String appId = authCustomer.getAppId();
		
		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		
		Date today = new Date();
		//1、申请流水
		CibLog cibLog = cibLogService.applyAuthLogByAuthCustomer(authCustomer, Const.AUTH_LOG_UNBIND);
		try{
			//2、查询绑定授权信息
			Customer queryAuthCustomer = customerDao.findByClientId(authCustomer.getAppId(), authCustomer.getOpenId());
			//后续可能不是这样，目前没有用户授权相关的需要，所以无有效的用户授权信息则忽略不处理
			if(queryAuthCustomer != null){
				//3、更新状态为无效
				queryAuthCustomer.setValid(Const.STATUS_INVALID);
				queryAuthCustomer.setUpdTime(today);
				customerDao.update(queryAuthCustomer);
				//4、解绑卡授权信息
				List<BindCard> list = bindCardService.findAuthCardByCustId(queryAuthCustomer.getCustId());
				for(BindCard a : list){
					//设置卡状态为无效
					a.setValid(Const.STATUS_INVALID);
					a.setUpdTime(today);
					bindCardService.updBindCard(a);
					//5、解绑通知授权
					List<AuthNotify> notifys = authNotifyService.findAuthNotifyByAcctNo(AlipayApplication.getAppId(), a.getOpenId(), a.getAcctNo());
					for(AuthNotify n:notifys){
						n.setValid(Const.STATUS_INVALID);
						n.setUpdTime(today);
						authNotifyService.updAuthNotify(n);
					}
				}
				//6、更新流水
				cibLog.setStatus(Const.STATUS_SUCCESS);
				cibLogService.updAuthLog(cibLog);
			}
			else{
				throw new AppRTException(AppExCode.CHECK_ERROR, "已取消关注！");
			}
		}
		catch(AppRTException e){
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("用户授权解绑失败！原因:[%s]", e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("用户授权解绑失败！原因:[%s]", e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw new AppRTException(AppExCode.UNKNOWN, e.getMessage());
		}
		
		
	}

	@Override
	public Customer findAuthCustomerByClientIdWithoutStatus(String appId,
			String openId) {
		return customerDao.findByClientIdWithoutStatus(appId, openId);
	}
	
	@Override
	public Customer findAuthCustomer(String appId, String openId) {
		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "获取客户信息失败，请重试！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		Customer authCustomer = this.findAuthCustomerByClientId(appId, openId);
		if(authCustomer !=null){
			List<BindCard> list = bindCardService.findAuthCardByCustId(authCustomer.getCustId());
			authCustomer.setAuthCards(list);
		}
		return authCustomer;
	}


	@Override
	public Customer defaultCardUpd(String appId, String openId,String acctNo,String type) {
		//1、查询用户授权信息
		Customer authCustomer = this.findAuthCustomerByClientId(appId, openId);
		if(authCustomer != null){
			if(AccountUtil.isCreditCard(acctNo)){
				if(Const.CARD_BIND.equals(type)){
					authCustomer.setDefaultCCCard(acctNo);
				}
				else{
					authCustomer.setDefaultCCCard(null);
				}
			}
			else{
				if(Const.CARD_BIND.equals(type)){
					authCustomer.setDefaultDCCard(acctNo);
				}
				else{
					authCustomer.setDefaultDCCard(null);
				}
			}
			this.updAuthCustomer(authCustomer);
		}
		else{
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format("用户授权信息不存在，应用ID：[%s]，客户ID：[%s]！", appId,openId));
		}
		return authCustomer;
	}
	

}
