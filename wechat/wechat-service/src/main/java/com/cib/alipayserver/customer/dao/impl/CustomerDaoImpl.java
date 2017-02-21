package com.cib.alipayserver.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.common.Const;
import com.cib.alipayserver.customer.dao.CustomerDao;
import com.cib.alipayserver.customer.model.Customer;


/**
 * 
 * @ClassName: AuthCustomerDaoImpl
 * @Description: 客户授权信息dao
 * @author: xuzz
 * @date:2014-3-20 下午06:13:03
 */
@Service("customerDao")
public class CustomerDaoImpl extends AlipayBaseDao<Customer, String>
		implements CustomerDao {

	@Override
	public Customer findByClientId(String appId, String openId) {
		String hql = "from " + Customer.class.getName()
				+ " c where c.appId = ? and c.openId = ? and c.valid = ?";
		List<Customer> list = super.find(hql, appId, openId,
				Const.STATUS_VALID);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public Customer findByClientIdWithoutStatus(String appId,String openId) {
		String hql = "from " + Customer.class.getName()
				+ " c where c.appId = ? and c.openId = ?";
		List<Customer> list = this.find(hql, appId, openId);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
