package com.cib.alipayserver.base;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import com.intensoft.dao.hibernate.HibernateGenericDao;

/**
 * @ClassName: OpenBankBaseDao
 * @Description: 微信基础Dao实现
 * @author hongye
 * @date 2014-3-10 13:23:37
 */

public class AlipayBaseDao <T, ID extends Serializable> extends HibernateGenericDao<T, ID> {

	
	@Override
	@Resource(name="alipaySessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
		
	}
	@Override  
	protected List<T> findAll(){ 
		return super.findAll();
	}
	
}
