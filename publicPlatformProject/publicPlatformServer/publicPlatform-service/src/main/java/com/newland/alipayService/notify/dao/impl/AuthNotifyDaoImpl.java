package com.newland.alipayService.notify.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.newland.alipayService.base.AlipayBaseDao;
import com.newland.alipayService.notify.dao.AuthNotifyDao;
import com.newland.alipayService.notify.model.AuthNotify;
import com.newland.alipayService.notify.model.BigPage;
import com.newland.base.common.Const;

/**
 * 
 * @ClassName: AuthNotifyDaoImpl
 * @Description: 通知授权信息
 * @author: xuzz
 * @date:2014-3-25 下午04:31:34
 */
@Service("authNotifyDao")
public class AuthNotifyDaoImpl extends AlipayBaseDao<AuthNotify, String>
		implements AuthNotifyDao {

	@Override
	public AuthNotify findByClientNotifyType(String appId, String appUserId,
			String acctNo, String... notifyTypes) {
		String hql = "from  "
				+ AuthNotify.class.getName()
				+ " a where a.appId = ? and a.appUserId = ? and a.acctNo = ? and ( ";
		if (ArrayUtils.isNotEmpty(notifyTypes)) {
			for (String notifyType : notifyTypes) {
				hql += " a.notifyType = " + notifyType + " or ";
			}
			hql += " 1=0 ) and a.valid=? ";
		} else {
			hql += " 1=1 ) and a.valid=? ";
		}
		List<AuthNotify> list = super.find(hql, appId, appUserId, acctNo,
				Const.STATUS_VALID);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<AuthNotify> findByUpdateTime(Date updateTime, String status,
			int startIndex, int pageSize) {
		BigPage<AuthNotify> page = this.findBig("from " + AuthNotify.class.getName()
				+ " a where a.updTime >= ? and a.valid = ?", startIndex,
				pageSize, updateTime, status);
		return page.getItems();
	}

	@Override
	public List<AuthNotify> findByUpdateTime(Date updateTime, int startIndex,
			int pageSize) {
		BigPage<AuthNotify> page = this.findBig("from " + AuthNotify.class.getName()
				+ " a where a.updTime >= ?", startIndex, pageSize, updateTime);
		return page.getItems();
	}

	@Override
	public AuthNotify findByClientNotifyTypeWithoutStatus(String appId,
			String appUserId, String acctNo, String notifyType) {
		String hql = "from  "
				+ AuthNotify.class.getName()
				+ " a where a.appId = ? and a.appUserId = ? and a.acctNo = ? and a.notifyType = ?";
		List<AuthNotify> list = super.find(hql, appId, appUserId, acctNo,
				notifyType);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public List<AuthNotify> findByAcctNo(String appId, String appUserId,
			String acctNo) {
		String hql = "from  "
				+ AuthNotify.class.getName()
				+ " a where a.appId = ? and a.appUserId = ? and a.acctNo = ? and a.valid = ?";
		List<AuthNotify> list = super.find(hql, appId, appUserId, acctNo, Const.STATUS_VALID);
		return list;
	}
	
	/**
	 * HQL分页查询
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected BigPage<AuthNotify> findBig(final String hql, final int startIndex, final int pageSize, final Object... values) {
		return (BigPage<AuthNotify>) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				// 查总记录数
				int totalCount = countHqlResult(hql, values);
				// 查分页数据，先创建分页对象是为了对startIndex和pageSize进行容错处理
				BigPage<AuthNotify> page = new BigPage<AuthNotify>(null, totalCount, startIndex, pageSize);
				Query query = createQuery(session, hql, values);
				query.setFirstResult(page.getStartIndex());
				query.setMaxResults(page.getPageSize());
				page.setItems(query.list());				
				return page;
			}
		});

	}
	
		@Override
	public int countAuthNotify(String appId,String acctType,String notifyType){
		String hql = "SELECT COUNT(*) from "+AuthNotify.class.getName()+ 
		             " a where a.appId = ? and a.acctType = ? and a.notifyType = ? and a.valid = ?";
		Long count = (Long) super.findUnique(hql,appId,acctType,notifyType,Const.STATUS_VALID);
		return count.intValue();

	}
}
