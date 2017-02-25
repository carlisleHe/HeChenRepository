package com.newland.weixinService.accessToken.service;

import com.intensoft.exception.AppBizException;

/**
 * 微信服务器访问Token接口
 * @author Shizn
 *
 */
public interface AccessTokenService {
	/**
	 * 获取AccessToken
	 * @return
	 * @throws AppBizException 
	 */
	String getAccessToken() throws AppBizException;
	
	void invalidateToken();

}
