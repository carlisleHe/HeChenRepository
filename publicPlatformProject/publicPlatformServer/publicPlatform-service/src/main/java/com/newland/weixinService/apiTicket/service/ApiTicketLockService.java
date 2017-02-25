package com.newland.weixinService.apiTicket.service;

import com.intensoft.exception.AppBizException;
import com.newland.weixinService.model.ApiTicket;
/**
 * ApiTicket更新锁定服务
 * @author wengxy
 *
 */
public interface ApiTicketLockService {

	/**
	 * 锁定并更新
	 * @return
	 * @throws AppBizException
	 */
	ApiTicket updateForLock() throws AppBizException;
	/**
	 * 判断是否更新
	 * @param token
	 * @return
	 */
	boolean expire(ApiTicket ticket);

}
