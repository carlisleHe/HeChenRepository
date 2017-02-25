package com.newland.weixinService.accessToken.service;

import com.intensoft.exception.AppBizException;
import com.newland.weixinService.model.AccessToken;
/**
 * AccessToken更新锁定服务
 * @author dvlp
 *
 */
public interface AccessTokenLockService {

	/**
	 * 锁定并更新
	 * @return
	 * @throws AppBizException
	 */
	AccessToken updateForLock() throws AppBizException;
	/**
	 * 判断是否更新
	 * @param token
	 * @return
	 */
	boolean expire(AccessToken token);
	/**
	 * 从查询库中查询AccessToken
	 * @return
	 */
	AccessToken getAccessToken();
	
	void invalidateToken();
	void update(AccessToken token);
}
