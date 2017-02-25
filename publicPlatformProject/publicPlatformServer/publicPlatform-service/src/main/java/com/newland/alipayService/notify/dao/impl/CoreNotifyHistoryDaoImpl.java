package com.newland.alipayService.notify.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.intensoft.dao.hibernate.HibernateGenericDao;
import com.newland.alipayService.notify.dao.CoreNotifyHistoryDao;
import com.newland.alipayService.notify.model.CoreNotifyHistory;

/**
 * 
 * @Description: 动户通知历史Dao实现类
 * @ClassName:CoreNotifyHistoryDaoImpl
 * @author:fangsq
 * @date:2014-8-21
 */
@Service("coreNotifyHistoryDao")
public class CoreNotifyHistoryDaoImpl extends
		HibernateGenericDao<CoreNotifyHistory, Integer> implements
		CoreNotifyHistoryDao {

	@Override
	@Resource(name = "alipaySessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
