package com.cib.alipayserver.notify.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cib.alipayserver.notify.dao.CoreNotifyDao;
import com.cib.alipayserver.notify.model.CoreNotify;
import com.intensoft.dao.hibernate.HibernateGenericDao;

/**
 * 
 * @Description: 动户通知Dao实现类
 * @ClassName:CoreNotifyDaoImpl
 * @author:fangsq
 * @date:2014-8-21
 */
@Service("coreNotifyDao")
public class CoreNotifyDaoImpl extends HibernateGenericDao<CoreNotify, Integer>
		implements CoreNotifyDao {

	@Override
	@Resource(name = "alipaySessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	@Override
	public List<CoreNotify> findPenddingMsg(final int beginMsgId,
			final int limitSize) {
		final String hql = "from com.cib.openbank.notify.model.CoreNotify where msgId > ? order by msgId";
		List<CoreNotify> results = (List<CoreNotify>) getHibernateTemplate()
				.executeWithNativeSession(new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = createQuery(session, hql, beginMsgId);
						query.setFirstResult(0);
						query.setMaxResults(limitSize);
						return query.list();
					}
				});
		return results;
	}

	@Override
	public void deleteBlockMsgs(List<CoreNotify> messages) {
		if (!CollectionUtils.isEmpty(messages)) {
			int startIndex = messages.get(0).getMsgId();
			int endIndex = messages.get(messages.size() - 1).getMsgId();
			final String hql = "delete from com.cib.openbank.notify.model.CoreNotify where msgId between ? and ? ";
			this.excute(hql, startIndex, endIndex);
		}
	}
}
