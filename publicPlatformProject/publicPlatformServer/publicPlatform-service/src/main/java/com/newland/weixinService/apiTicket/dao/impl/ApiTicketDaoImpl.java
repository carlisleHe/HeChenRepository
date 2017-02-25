package com.newland.weixinService.apiTicket.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.newland.weixinService.apiTicket.dao.ApiTicketDao;
import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.model.ApiTicket;

@Service("apiTicketDao")
public class ApiTicketDaoImpl 
				extends WeixinBaseDao<ApiTicket, String>	implements ApiTicketDao{
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Date getDbServerCurrentTime() {
		HibernateTemplate ht = this.getHibernateTemplate();
		List<Object> list = (List<Object>)ht.execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery("select current timestamp from informix.systables where tabid=1");
				//query.setParameter("taskName", taskName);
                return query.list();
			}
		});
		
		if(list.size()>0){
			return (Date) list.get(0);
		}
		
		//未能获取到取weblogic服务器时间
		return new Date();
	}

}
