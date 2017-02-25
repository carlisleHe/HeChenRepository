package com.newland.alipayService.log.service;


import com.newland.alipayService.card.model.BindCard;
import com.newland.alipayService.customer.model.Customer;
import com.newland.alipayService.log.model.CibLog;
import com.newland.alipayService.notify.model.AuthNotify;

/**
 * 
 * @ClassName: AuthLogService  
 * @Description: 卡授权日志服务类 
 * @author: xuzz 
 * @date:2014-3-20 下午04:07:14
 */
public interface CibLogService {
	/**
	 * 根据卡授权信息申请卡授权日志流水 
	 * @param authCard
	 * @param type 0-解绑;1-绑定
	 * @return 卡授权日志
	 */
	public CibLog applyAuthLogByAuthCard(BindCard authCard,int type);

	/**
	 * 根据用户授权信息申请通知授权日志流水
	 * @param authNotify
	 * @param type 0-解绑;1-绑定
	 * @return 用户授权流水
	 */
	public CibLog applyAuthLogByAuthCustomer(Customer authCustomer,int type);
	/**
	 * 保存授权日志
	 * @param authLog
	 * @return
	 */
	public CibLog saveAuthLog(CibLog authLog);
	/**
	 * 更新授权日志
	 * @param authLog
	 * @return
	 */
	public CibLog updAuthLog(CibLog authLog);
	/**
	 * 根据通知授权信息申请通知授权日志流水
	 * @param authNotify
	 * @param type 0-解绑;1-绑定
	 * @return 通知授权日志流水
	 */
	public CibLog applyAuthLogByAuthNotify(AuthNotify authNotify,int type);

}
