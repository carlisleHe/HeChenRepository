package com.newland.weixinService.accessToken.dao;

import java.util.Date;

import com.intensoft.dao.GenericDao;
import com.newland.weixinService.model.AccessToken;

public interface AccessTokenDao extends GenericDao<AccessToken, String>{
	
	public static final Integer ACCESS_TOKEN_KEY = new Integer(0);
	
	Date getDbServerCurrentTime();

}
