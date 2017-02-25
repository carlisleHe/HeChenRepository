package com.newland.weixinService.apiTicket.service.impl;

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
import com.newland.weixinService.accessToken.service.AccessTokenLockService;
import com.newland.weixinService.apiTicket.dao.ApiTicketDao;
import com.newland.weixinService.apiTicket.service.ApiTicketLockService;
import com.newland.weixinService.exception.WeixinException;
import com.newland.weixinService.exception.WeixinExceptionType;
import com.newland.weixinService.model.ApiTicket;
/**
 * ApiTicket锁定更新服务
 * @author wengxy
 *
 */
@Service("apiTicketLockService")
public class ApiTicketLockServiceImpl implements ApiTicketLockService {

	@Value("@[jsapi_ticket_url]")
	private String jsapiTicketUrl;
	
	@Resource (name = "apiTicketDao")
	private ApiTicketDao apiTicketDao;
	
	@Resource(name="accessTokenLockService")
	private AccessTokenLockService accessTokenLockService;
	
	private String accesstoken;
	
	/**
	 * api_ticket超时时间
	 * 提前20分钟
	 */
	public static final long DEFAULT_EXPIRE_TIME = 20 * 60 * 1000;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(value="weixinTransactionManager", propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ, rollbackFor = Throwable.class)
	public ApiTicket updateForLock() throws AppBizException {
    	ApiTicket ticket = this.apiTicketDao.findById(WeixinApplication.getAppId(), true);
		logger.info("ticket:"+ticket);
		if (ticket != null){
			if (expire(ticket) == false){
				logger.info("apiticket已被更新，从数据库中取值：[" + ticket.getApiTicket() + "]");
				return ticket;
			}
		}else{
			ticket = new ApiTicket();
			ticket.setAppId(WeixinApplication.getAppId());
		}
		PostResult result;
		try {
			//获取Token
			accesstoken = accessTokenLockService.updateForLock().getAccessToken();
			logger.info("获取apiticket开始！");
			result = WeixinPostUtils.getApiTicket(jsapiTicketUrl, accesstoken);
			logger.info("获取apiticket结束！");
		} catch (Exception e) {
			throw new WeixinException(WeixinExceptionType.WEIXIN_POST_ERROR);
		}
		if (result.getErrCode() == 0 && StringUtils.isNoneBlank(result.getApiTicket())
				&& result.getExpireIn() != 0){
			ticket.setApiTicket(result.getApiTicket());
			Calendar cal = Calendar.getInstance();
			cal.setTime(apiTicketDao.getDbServerCurrentTime());
			cal.add(Calendar.SECOND, result.getExpireIn());
			ticket.setExpireTime(cal.getTime());
			ticket = this.apiTicketDao.saveOrUpdate(ticket);
			logger.info("apiticket更新成功：[" + ticket.getApiTicket() + "]");
			return ticket;
		}else{
			throw new WeixinException(WeixinExceptionType.GET_API_TICKET_ERR);
		}
	}
	@Override
	public boolean expire(ApiTicket ticket){
		if (ticket.getExpireTime().getTime() < apiTicketDao.getDbServerCurrentTime().getTime() + DEFAULT_EXPIRE_TIME){
			return true;
		}
		return false;
	}
}
