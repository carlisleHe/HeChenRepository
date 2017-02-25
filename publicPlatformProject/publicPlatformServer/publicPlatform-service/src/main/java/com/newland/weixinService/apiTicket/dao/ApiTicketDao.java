package com.newland.weixinService.apiTicket.dao;

import java.util.Date;

import com.intensoft.dao.GenericDao;
import com.newland.weixinService.model.ApiTicket;

public interface ApiTicketDao extends GenericDao<ApiTicket, String>{
	
	public static final Integer ACCESS_TOKEN_KEY = new Integer(0);
	
	Date getDbServerCurrentTime();

}
