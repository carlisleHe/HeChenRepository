package com.newland.weixinService.accessToken.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.newland.weixinService.accessToken.service.AccessTokenLockService;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.model.AccessToken;
/**
 * AccessToken多机共享服务数据库实现
 * @author Shizn
 *
 */
@Service("accessTokenService")
public class DBAccessTokenServiceImpl implements AccessTokenService{
	
	
	@Resource (name = "accessTokenLockService")
	private AccessTokenLockService lockService;

	
	@Override
	public synchronized String getAccessToken() throws AppBizException {
		AccessToken token = getLocalToken();
		if (token == null){
			token = lockService.updateForLock();
			this.token = token;
		}
		return token.getAccessToken();
	}
	
	transient AccessToken token;
	
	/**
	 * 从本地获取accessToken
	 */
	private AccessToken getLocalToken() throws AppBizException{
		if (token != null){
			if(this.lockService.expire(token)){
				this.token = null;
				return token;
			}
			return token;
		}else{
			token = this.lockService.getAccessToken();
			if (token != null) return getLocalToken();
			else return token;
		}
	}

	@Override
	public void invalidateToken() {
		this.token = null;
		this.lockService.invalidateToken();
		
	}
}
