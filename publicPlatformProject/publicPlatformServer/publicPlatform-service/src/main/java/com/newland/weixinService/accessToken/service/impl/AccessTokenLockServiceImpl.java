package com.newland.weixinService.accessToken.service.impl;

import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.intensoft.exception.AppBizException;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.post.WeixinPostUtils;
import com.newland.wechat.post.model.PostResult;
import com.newland.weixinService.accessToken.dao.AccessTokenDao;
import com.newland.weixinService.accessToken.service.AccessTokenLockService;
import com.newland.weixinService.exception.WeixinException;
import com.newland.weixinService.exception.WeixinExceptionType;
import com.newland.weixinService.model.AccessToken;
/**
 * AccessToken锁定更新服务
 * @author Shizn
 *
 */
@Service("accessTokenLockService")
public class AccessTokenLockServiceImpl implements AccessTokenLockService {

	@Value("@[wx_access_token]")
	private String postUrl;
	@Resource (name = "accessTokenDao")
	private AccessTokenDao tokenDao;
	
	/**
	 * token超时时间
	 * 提前20分钟
	 */
	public static final long DEFAULT_EXPIRE_TIME = 20 * 60 * 1000;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(value="weixinTransactionManager", propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ, rollbackFor = Throwable.class)
	public AccessToken updateForLock() throws AppBizException {
		AccessToken token = this.tokenDao.findById(WeixinApplication.getAppId(), true);
		if (token != null){
			if (expire(token) == false){
				logger.info("accesstoken已被更新，从数据库中取值：[" + token.getAccessToken() + "]");
				return token;
			}
		}else{
			token = new AccessToken();
			token.setAppId(WeixinApplication.getAppId());
		}
		PostResult result;
		try {
			logger.info("获取accesstoken开始！");
			result = WeixinPostUtils.getAccessToken(postUrl, WeixinApplication.getAppId(), WeixinApplication.getAppsecret());
			logger.info("获取accesstoken结束！");
		} catch (Exception e) {
			throw new WeixinException(WeixinExceptionType.WEIXIN_POST_ERROR);
		}
		if (result.getErrCode() == 0 && StringUtils.isNoneBlank(result.getAccessToken())
				&& result.getExpireIn() != 0){
			token.setAccessToken(result.getAccessToken());
			Calendar cal = Calendar.getInstance();
			cal.setTime(tokenDao.getDbServerCurrentTime());
			cal.add(Calendar.SECOND, result.getExpireIn());
			token.setExpireTime(cal.getTime());
			token = this.tokenDao.saveOrUpdate(token);
			logger.info("accesstoken更新成功：[" + token.getAccessToken() + "]");
			return token;
		}else{
			throw new WeixinException(WeixinExceptionType.GET_ACCESS_TOKEN_ERR);
		}
	}
	@Override
	public boolean expire(AccessToken token){
		if (token.getExpireTime().getTime() < tokenDao.getDbServerCurrentTime().getTime() + DEFAULT_EXPIRE_TIME){
			return true;
		}
		return false;
	}
	@Override
	public AccessToken getAccessToken() {
		try{
			AccessToken token =  this.tokenDao.findById(WeixinApplication.getAppId());
			if (token == null){
				token = new AccessToken();
				token.setAccessToken("");
				token.setExpireTime(tokenDao.getDbServerCurrentTime());
				token.setAppId(WeixinApplication.getAppId());
				token = this.tokenDao.saveOrUpdate(token);
			}
			return token;
		}catch(Throwable e){
			return null;
		}
	}
	public AccessTokenDao getTokenDao() {
		return tokenDao;
	}
	public void setTokenDao(AccessTokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}
	public String getPostUrl() {
		return postUrl;
	}
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	@Override
	@Transactional(value="weixinTransactionManager",propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ, rollbackFor = Throwable.class)
	public void invalidateToken() {
		AccessToken token = this.tokenDao.findById(WeixinApplication.getAppId(), true);
		if (token != null){
			// 无论token的expiretime是什么时候，都让这个token失效，以重新获取
			this.update(token);
		}
	}
	@Override
	public void update(AccessToken token){
		token.setExpireTime(this.tokenDao.getDbServerCurrentTime());
		this.tokenDao.update(token);
	}
	
}
