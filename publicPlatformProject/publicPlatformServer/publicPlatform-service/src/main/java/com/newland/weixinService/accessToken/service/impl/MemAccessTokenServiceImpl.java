package com.newland.weixinService.accessToken.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.intensoft.exception.AppBizException;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.post.WeixinPostUtils;
import com.newland.wechat.post.model.PostResult;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.cache.CacheService;
import com.newland.weixinService.exception.WeixinException;
import com.newland.weixinService.exception.WeixinExceptionType;
import com.newland.weixinService.model.AccessToken;
//@Service("accessTokenService")
public class MemAccessTokenServiceImpl implements AccessTokenService {
	
	@Value("@[wx_access_token]")
	private String postUrl;
	@Resource (name = "memcacheService")
	private CacheService<String, Object> cacheService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 微信AccessToken key定义
	 */
	public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
	/**
	 * 锁对象Key定义
	 */
	public static final String LOCK_KEY = "LOCK_KEY";
	
	@Override
	public String getAccessToken() throws AppBizException {
		AccessToken token = getLocalToken();
		boolean retry = false;
		do{
			if (token == null){
				/**
				 * 先判断是否被锁定
				 */
				boolean islock = isLock();
				/**
				 * 如果锁定，等待锁释放
				 */
				while(isLock()){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
				/**
				 * 如果之前被锁定过，说明其它线程或服务器刚更新过，从内存中取accessToken
				 */
				if (islock){
					token = getLocalToken();
					retry = false;
				}else{
					/**
					 * 如果之前未锁定，则获取锁并从微信上重新获取accessToken
					 */
					islock = lock();
					/**
					 * 锁定并获取成功，释放锁资源
					 */
					if (islock){
						token = getRemoteToken();
						releaseLock();
						retry = false;
					}else{
						/**
						 * 锁定不成功，重新获取
						 */
						retry = true;
					}
				}
			}
		}while(retry);
		return token.getAccessToken();
	}
	/**
	 * 判断accessToken 是否过期
	 * @param token
	 * @return
	 */
	private boolean expire(AccessToken token){
		if (token.getExpireTime().getTime() < System.currentTimeMillis() - 30 * 60 * 1000){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否锁定
	 * @return
	 */
	private synchronized boolean isLock(){
		Object lockObj = null;
		try {
			lockObj = this.cacheService.get(LOCK_KEY);
		} catch (AppBizException e) {
			logger.error("连接内存数据库出错", e);
		}
		if (lockObj == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 加锁
	 * @return
	 */
	private synchronized boolean lock(){
		try{
			Object lockObj = this.cacheService.get(LOCK_KEY);
			if (lockObj == null){
				cacheService.put(LOCK_KEY, new LockObj(), 15, true );
				return true;
			}
			return false;
		}catch(AppBizException e){
			if (e instanceof WeixinException){
				if (((WeixinException)e).getType() == WeixinExceptionType.DATA_LOCK_ERR){
					return true;
				}
			}
			logger.error("连接内存数据库出错", e);
			return true;
		}
	}
	/**
	 * 释放锁
	 */
	private synchronized void releaseLock(){
		try {
			cacheService.remove(LOCK_KEY);
		} catch (AppBizException e) {
			logger.error("连接内存数据库出错", e);
		}
	}
	
	/**
	 * 从本地获取accessToken
	 */
	private AccessToken getLocalToken() throws AppBizException{
		AccessToken token = (AccessToken)cacheService.get(ACCESS_TOKEN_KEY);
		if (token != null){
			if(expire(token)) return null;
			return token;
		}
		return token;
	}

	/**
	 * 从微信服务器获取accessToken
	 */
	private AccessToken getRemoteToken() throws AppBizException {
		AccessToken token;
		PostResult result;
		try {
			result = WeixinPostUtils.getAccessToken(postUrl, WeixinApplication.getOpenbankId(), WeixinApplication.getAppsecret());
		} catch (Exception e) {
			throw new WeixinException(WeixinExceptionType.WEIXIN_POST_ERROR);
		}
		if (result.getErrCode() == 0 && StringUtils.isNoneBlank(result.getAccessToken())
				&& result.getExpireIn() != 0){
			token = new AccessToken();
			cacheService.put(ACCESS_TOKEN_KEY, token, result.getExpireIn(), false);
			return token;
		}else{
			throw new WeixinException(WeixinExceptionType.GET_ACCESS_TOKEN_ERR);
		}
	}
	
	
	
	public static class LockObj implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}



	@Override
	public void invalidateToken() {
		// TODO Auto-generated method stub
		
	}

}
