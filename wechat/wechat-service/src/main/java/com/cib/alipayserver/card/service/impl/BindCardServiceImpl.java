package com.cib.alipayserver.card.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipayserver.card.dao.BindCardDao;
import com.cib.alipayserver.card.model.BindCard;
import com.cib.alipayserver.card.service.BindCardService;
import com.cib.alipayserver.common.AppExCode;
import com.cib.alipayserver.common.Const;
import com.cib.alipayserver.customer.model.Customer;
import com.cib.alipayserver.customer.service.CustomerService;
import com.cib.alipayserver.idgen.service.IdentifierGenerator;
import com.cib.alipayserver.log.model.CibLog;
import com.cib.alipayserver.log.service.CibLogService;
import com.cib.alipayserver.notify.model.AuthNotify;
import com.cib.alipayserver.notify.service.AuthNotifyService;
import com.cib.alipayserver.util.AccountUtil;
import com.intensoft.exception.AppRTException;
/**
 * 
 * @ClassName: BindCardServiceImpl  
 * @Description: 微信卡绑定服务实现 
 * @author: xuzz 
 * @date:2014-3-20 下午03:07:43
 */
@Service("bindCardService")
public class BindCardServiceImpl implements BindCardService{
	private static final Logger logger = LoggerFactory.getLogger(BindCardService.class);
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	@Resource(name = "bindCardDao")
	private BindCardDao bindCardDao;
	@Resource(name = "customerService")
	private CustomerService customerService;
	@Resource(name = "cibLogService")
	private CibLogService cibLogService;
	@Resource(name = "authNotifyService")
	private AuthNotifyService authNotifyService;


	@Override
	@Transactional
	public BindCard bindAuthCard(BindCard bindCard) {
		
		//1、基础条件判断
		//应用ID依赖于客户端的信息，必输
		String openId = bindCard.getOpenId();
		//客户ID依赖于客户端，必输
		String appId = bindCard.getAppId();
		//卡号
		String acctNo = bindCard.getAcctNo();
		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "卡号不能为空！"); 
		}
		
		bindCard.setCibAcctNo(AccountUtil.calcCibAcctNo(acctNo));

		Date today = new Date();
		//String acctNo = authCard.getAcctNo();
		//基础条件判断
		//90592卡16位，普通理财卡（E卡）18位
		if(!AccountUtil.isCreditCard(acctNo)){
			//理财卡判断卡号长度
			if(AccountUtil.isDebitCard(acctNo)){
				if(acctNo.length()!=16&&acctNo.length()!=18){
					throw new AppRTException(AppExCode.CHECK_ERROR, "卡类型长度不符！");
				}
			}
			else{
				throw new AppRTException(AppExCode.CHECK_ERROR, "无效的卡类型！");
			}
		}
		//1、记录卡授权流水
		//申请授权流水
		CibLog cibLog = cibLogService.applyAuthLogByAuthCard(bindCard,Const.AUTH_LOG_BIND);
		//2、绑定卡授权信息
		//查询当前应用ID下该卡号的绑定情况
		try{
			List<BindCard> list = null;
			//信用卡根据全卡号查询
			if(AccountUtil.isCreditCard(acctNo)){
				list = bindCardDao.findByAcctNo(acctNo);
			}
			else{
				if(bindCard.getCibAcctNo() == null){
					throw new AppRTException(AppExCode.CHECK_ERROR, "行内卡号不能为空！");
				}
				list = bindCardDao.findByCibAcctNo(bindCard.getCibAcctNo());
			}
			BindCard persistAuthCard = null;
			//与待绑定记录appId一致的已有记录,状态可为有效，也可为无效。
			BindCard sameAppIdAuthCard = null;
			//与待绑定记录openId一致的已有记录
			//状态无效时，otherClientIdValidAuthCard 肯定不为空
			//状态有效时，otherClientIdValidAuthCard 肯定为空
			BindCard sameClientIdAuthCard = null;
			//与待绑定记录openId不一致的且状态为有效的已有记录
			BindCard otherClientIdValidAuthCard = null;
			//查询定位上述三种变量
			for(BindCard a :list){
				//appId与待绑定记录一致的
				if(a.getAppId().equals(bindCard.getAppId())){
					sameAppIdAuthCard = a;
					//openId与待绑定记录一致的
					if(a.getOpenId().equals(bindCard.getOpenId())){
						//存在openId记录一致的情况，记录当前记录
						sameClientIdAuthCard = a;
					}
					//因为绑定时已有记录的appId与openId与待绑定的如果不一致的话
					//1、状态有效则解绑原有记录，新增对应openId的绑定记录
					//2、状态无效的可以直接新增对应openId绑定记录
					else{
						if(a.getValid().equals(Const.STATUS_VALID)){
							otherClientIdValidAuthCard = a;
						}
					}
				}
			}
			//原先绑定的关系存在且状态有效
			if(otherClientIdValidAuthCard != null){
				//解绑原先的绑定关系
				unbindAuthCard(otherClientIdValidAuthCard);
			}
			//新增绑定关系下列三种情况 适用
			//1、不存在该卡的任何绑定信息
			//2、已有绑定记录里不存在于待绑定记录是属于同一应用ID的
			//3、已有绑定记录里与待绑定记录属于同一应用ID，不同客户ID
			if(sameAppIdAuthCard == null||CollectionUtils.isEmpty(list)||sameClientIdAuthCard == null){
				persistAuthCard = bindCard;
				//4、绑定用户授权信息
				Customer authCustomer = fillAuthCustomer(persistAuthCard);
				authCustomer = customerService.bindAuthCustomer(authCustomer);
				persistAuthCard.setValid(Const.STATUS_VALID);
				persistAuthCard.setBindId(snoGenerator.generate());
				persistAuthCard.setCustId(authCustomer.getCustId());
				persistAuthCard.setCreDate(today);
				persistAuthCard.setUpdTime(today);
				bindCardDao.save(persistAuthCard);
			}
			//同应用ID与客户ID情况
			else{
				if(Const.STATUS_VALID.equals(sameClientIdAuthCard.getValid())){
					//全卡号一致的情况可以判断为同卡的重复绑定
					if(bindCard.getAcctNo().equals(sameClientIdAuthCard.getAcctNo())){
						throw new AppRTException(AppExCode.CHECK_ERROR, String.format("重复卡授权绑定！，卡号:[%s]，应用ID:[%s]", sameClientIdAuthCard.getAcctNo(),sameClientIdAuthCard.getOpenId()));
					}
					else{
						throw new AppRTException(AppExCode.CHECK_ERROR, String.format("重复卡授权绑定，请先解绑原卡再进行新卡的绑定！，卡号:[%s]，应用ID:[%s]", sameClientIdAuthCard.getAcctNo(),sameClientIdAuthCard.getOpenId()));
					}
				}
				//更新原先的绑定关系状态为有效
				else{
					persistAuthCard = sameClientIdAuthCard;
					persistAuthCard.setCustName(bindCard.getCustName());
					//绑定用户授权信息
					Customer authCustomer = fillAuthCustomer(persistAuthCard);
					authCustomer = customerService.bindAuthCustomer(authCustomer);
					persistAuthCard.setValid(Const.STATUS_VALID);
					persistAuthCard.setUpdTime(today);
					persistAuthCard.setCustId(authCustomer.getCustId());
					bindCardDao.update(persistAuthCard);
					customerService.updAuthCustomer(authCustomer);
				}
			}
			//3、默认开通动户通知
			authNotifyService.bindAuthNotify(fillAuthNotity(persistAuthCard));
			//4、更新流水状态为成功
			cibLog.setStatus(Const.STATUS_SUCCESS);
			cibLogService.updAuthLog(cibLog);
			return persistAuthCard;
		}
		catch(AppRTException e){
			cibLog.setStatus(Const.STATUS_FAIL);
			cibLog.setRemark(String.format("卡授权绑定失败！卡号：[%s]，原因:[%s]", bindCard.getAcctNo(),e.getMessage()));
			cibLogService.updAuthLog(cibLog);
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			cibLog.setStatus(Const.STATUS_FAIL);
			cibLog.setRemark(String.format("卡授权绑定失败！卡号：[%s]，原因:[%s]",bindCard.getAcctNo(), e.getMessage()));
			cibLogService.updAuthLog(cibLog);
			throw new AppRTException(AppExCode.UNKNOWN, e.getMessage());
		}
	}
	/**
	 * 利用卡授权信息组装用户授权信息
	 * @param authCard
	 * @return
	 */
	private Customer  fillAuthCustomer(BindCard bindCard){
		Customer authCustomer = new Customer();
		authCustomer.setAppId(bindCard.getAppId());
		authCustomer.setCertNo(bindCard.getCertNo());
		authCustomer.setCertType(bindCard.getCertType());
		authCustomer.setOpenId(bindCard.getOpenId());
		authCustomer.setCustName(bindCard.getCustName());
		authCustomer.setBrNo(bindCard.getBrNo());
		authCustomer.setSubBrNo(bindCard.getSubBrNo());
		//设置当前卡为默认的借记卡或信用卡
		if(AccountUtil.isCreditCard(bindCard.getAcctNo())){
			authCustomer.setDefaultCCCard(bindCard.getAcctNo());
		}
		else{
			authCustomer.setDefaultDCCard(bindCard.getAcctNo());

		}
		//查询状态有效的用户授权关系，因为卡授权绑定时，在用户授权信息已存在的情况下，更新用户授权信息时原先另一种的默认卡类型要保留下来
		//如果当前绑定的是信用卡，则只更新用户授权的默认信用卡信息，默认的借记卡信息保持不变
		Customer queryAuthCustomer = customerService.findAuthCustomerByClientId(bindCard.getAppId(), bindCard.getOpenId());
		if(queryAuthCustomer != null){
			//说明当前绑定的是借记卡，所以默认的信用卡为空
			if(authCustomer.getDefaultCCCard() == null){
				authCustomer.setDefaultCCCard(queryAuthCustomer.getDefaultCCCard());
			}
			//当前绑定的是信用卡的情况
			if(authCustomer.getDefaultDCCard() == null){
				authCustomer.setDefaultDCCard(queryAuthCustomer.getDefaultDCCard());
				// 如果已经绑定了默认借记卡，则绑定信用卡时不改变所属分支行
				if(StringUtils.isNotEmpty(queryAuthCustomer.getDefaultDCCard())){
					authCustomer.setBrNo(queryAuthCustomer.getBrNo());
					authCustomer.setSubBrNo(queryAuthCustomer.getSubBrNo());
				}
			}
		}
		return authCustomer;
	}
	/**
	 * 利用卡授权信息组装通知授权信息
	 * @param authCard
	 * @return
	 */
	private AuthNotify fillAuthNotity(BindCard bindCard){
		AuthNotify authNotify = new AuthNotify();
		authNotify.setCustName(bindCard.getCustName());
		authNotify.setCertType(bindCard.getCertType());
		authNotify.setCertNo(bindCard.getCertNo());
		authNotify.setAcctNo(bindCard.getAcctNo());
		authNotify.setAcctType(bindCard.getAcctType());
		authNotify.setAppId(AlipayApplication.getAppId());
		authNotify.setAppUserId(bindCard.getOpenId());
		authNotify.setBrNo(bindCard.getBrNo());
		authNotify.setSubBrNo(bindCard.getSubBrNo());
		//只有借记卡才有核心客户号
		if(AccountUtil.isDebitCard(bindCard.getAcctNo())){
			authNotify.setCtspNo(AccountUtil.calcCibAcctNo(bindCard.getAcctNo()).substring(0,10));
		} else {
			// 信用卡的CTSP_NO字段直接写全卡号
			authNotify.setCtspNo(bindCard.getAcctNo());
		}
		if (Const.ACCT_TYPE_DEBIT.equals(bindCard.getAcctType())) {
			authNotify.setNotifyType(Const.MESSAGE_TYPE_BALANCE);
		} else {
			authNotify.setNotifyType(Const.MESSAGE_TYPE_CREDIT);
		}
		return authNotify;
	}
	
	
	@Override
	public BindCard findAuthCardByAcctNo(String appId,String openId,String acctNo) {
		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "卡号不能为空！"); 
		}
		return bindCardDao.findByAcctNo(appId, openId,acctNo);
	}
	
	@Override
	public BindCard findAuthCardBycibAcctNo(String appId, String openId,
			String cibAcctNo) {
		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(StringUtils.isBlank(cibAcctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "核心卡号不能为空！"); 
		}
		return bindCardDao.findByCibAcctNo(appId, openId,cibAcctNo);
	}
	
	@Override
	public List<BindCard> findAuthCardsByClientId(String appId, String openId) {
		return bindCardDao.findByOpenId(appId, openId);
	}
	@Override
	@Transactional
	public void unbindAuthCard(BindCard bindCard) {
		
		//1、基础条件判断
		//应用ID依赖于客户端的信息，必输
		String openId = bindCard.getOpenId();
		//客户ID依赖于客户端，必输
		String appId = bindCard.getAppId();
		//卡号
		String acctNo = bindCard.getAcctNo();

		if(StringUtils.isBlank(openId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(StringUtils.isBlank(acctNo)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "卡号不能为空！"); 
		}

		Date today = new Date();
		//查询卡授权绑定信息
		BindCard queryAuthCard = bindCardDao.findByAcctNo(bindCard.getAppId(),bindCard.getOpenId(),bindCard.getAcctNo());
		CibLog cibLog = null;
		try{
			if(queryAuthCard == null){
				throw new AppRTException(AppExCode.CHECK_ERROR, String.format("不存在的卡授权信息！应用ID:[%s],账号:[%s]", bindCard.getAppId(),bindCard.getAcctNo()));
			}
			else{
				//1、登记卡授权解绑流水
				cibLog = cibLogService.applyAuthLogByAuthCard(queryAuthCard,Const.AUTH_LOG_UNBIND);
				//状态无效，说明已解绑过
				if(Const.STATUS_INVALID.equals(queryAuthCard.getValid())){
					throw new AppRTException(AppExCode.CHECK_ERROR, String.format("重复解绑！应用ID:[%s],账号:[%s]", bindCard.getAppId(),bindCard.getAcctNo()));
				}
				else{
					//2、解绑卡授权信息
					queryAuthCard.setUpdTime(today);
					queryAuthCard.setValid(Const.STATUS_INVALID);
					bindCardDao.update(queryAuthCard);
					//3、解绑通知信息
					//查询是否开通动户通知
					List<AuthNotify> authNotifys = authNotifyService.findAuthNotifyByAcctNo(AlipayApplication.getAppId(), queryAuthCard.getOpenId(), queryAuthCard.getAcctNo());
					//未开通动户通知或动户通知被关闭则无需要解绑
					for(AuthNotify a : authNotifys){
						authNotifyService.unbindAuthNotify(a);
					}
					//4、重新设置默认卡
					//查询用户授权信息
					Customer authCustomer = customerService.findAuthCustomerByClientId(bindCard.getAppId(), bindCard.getOpenId());
					if(authCustomer == null){
						throw new AppRTException(AppExCode.CHECK_ERROR, String.format("无效的用户授权信息，应用ID：[%s]，客户ID：[%s]", bindCard.getAppId(),bindCard.getOpenId()));
					}
					//已绑定卡列表
					List<BindCard> cards = bindCardDao.findByOpenId(queryAuthCard.getAppId(), queryAuthCard.getOpenId());
					//如果解绑卡为默认卡，存在其它绑定卡的情况下需要重新设置默认卡
					//判断当前解绑的卡类型
					String acctType = queryAuthCard.getAcctType();
					//是否解绑的卡为默认卡
					boolean flag = false;
					if(Const.ACCT_TYPE_CREDIT.equals(acctType)){
						flag = queryAuthCard.getAcctNo().equals(authCustomer.getDefaultCCCard());
						if(flag){
							//清空原先的默认卡信息
							authCustomer.setDefaultCCCard(null);
						}
					}
					else{
						flag = queryAuthCard.getAcctNo().equals(authCustomer.getDefaultDCCard());
						if(flag){
							//清空原先的默认卡信息
						    authCustomer.setDefaultDCCard(null);
						}
					}
					if(flag){
						BindCard card = null;
						for(BindCard a :cards){
							//设置同类型的其它卡为默认卡
							if(acctType.equals(a.getAcctType())&&!bindCard.getAcctNo().equals(a.getAcctNo())){
								card = a;
								break;
							}
						}
						// 重新设置默认卡,并更新分支行信息
						// 若重设默认信用卡且没有默认借记卡，则分支行信息为信用卡信息
						// 若重设默认借记卡，则分支行信息为借记卡信息
						if(card != null){
							if(Const.ACCT_TYPE_CREDIT.equals(acctType)){
								authCustomer.setDefaultCCCard(card.getAcctNo());
							}
							else{
								authCustomer.setDefaultDCCard(card.getAcctNo());
								authCustomer.setBrNo(card.getBrNo());
								authCustomer.setSubBrNo(card.getSubBrNo());
							}
							
						}
						if (StringUtils
								.isEmpty(authCustomer.getDefaultDCCard())) {
							if(StringUtils
									.isEmpty(authCustomer.getDefaultCCCard())){
								authCustomer.setBrNo(null);
								authCustomer.setSubBrNo(null);
							}else{
								authCustomer.setBrNo(Const.CC_DEFAULT_BRANCH);
								authCustomer.setSubBrNo(Const.CC_DEFAULT_SUBBRANCH);
							}
						}
						//更新用户授权信息的默认卡绑定关系
						customerService.updAuthCustomer(authCustomer);
					}
				}
				//5、更新卡授权解绑流水状态为成功
				cibLog.setStatus(Const.STATUS_SUCCESS);
				cibLogService.updAuthLog(cibLog);
			}
		}
		catch(AppRTException e){
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("卡授权解绑失败！卡号：[%s]，原因:[%s]", queryAuthCard.getAcctNo(),e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			if(cibLog != null){
				cibLog.setStatus(Const.STATUS_FAIL);
				cibLog.setRemark(String.format("卡授权解绑失败！卡号：[%s]，原因:[%s]", queryAuthCard.getAcctNo(),e.getMessage()));
				cibLogService.updAuthLog(cibLog);
			}
			throw new AppRTException(AppExCode.UNKNOWN, e.getMessage());
		}
	}

	@Override
	public List<BindCard> findAuthCardByCustId(String custId) {
		return bindCardDao.findByCustId(custId);
	}
	@Override
	public BindCard updBindCard(BindCard bindCard) {
		return bindCardDao.update(bindCard);
	}
	
}
