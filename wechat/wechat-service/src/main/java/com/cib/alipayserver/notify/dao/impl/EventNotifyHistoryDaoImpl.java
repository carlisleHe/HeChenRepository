package com.cib.alipayserver.notify.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.notify.dao.EventNotifyHistoryDao;
import com.cib.alipayserver.notify.model.EventNotifyHistory;
import com.intensoft.dao.hibernate.HibernateGenericDao;

/**
 * 
 * @Description: 事件通知历史Dao实现类
 * @ClassName:CoreNotifyHistoryDaoImpl
 * @author:fangsq
 * @date:2014-8-21
 */
@Service("eventNotifyHistoryDao")
public class EventNotifyHistoryDaoImpl extends
		HibernateGenericDao<EventNotifyHistory, Integer> implements
		EventNotifyHistoryDao {

	@Override
	@Resource(name = "alipaySessionFactory")  
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
