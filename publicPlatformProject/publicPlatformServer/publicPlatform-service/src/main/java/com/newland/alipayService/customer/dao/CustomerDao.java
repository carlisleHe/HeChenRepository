package com.newland.alipayService.customer.dao;


import com.intensoft.dao.GenericDao;
import com.newland.alipayService.customer.model.Customer;
/**
 * 
 * @ClassName: AuthCustomerDao  
 * @Description: 授权客户信息Dao 
 * @author: xuzz 
 * @date:2014-3-20 下午06:07:20
 */
public interface CustomerDao extends GenericDao<Customer, String>{
	/**
	 * 根据应用ID与客户ID查询授权客户信息
	 * @param appId 应用ID
	 * @param openId 客户ID
	 * @return 有效状态的数据
	 */
	public Customer findByClientId(String appId,String openId);
	/**
	 * 根据用户ID与客户ID查询授权客户信息，无关状态
	 * @param appId
	 * @param openId
	 * @return
	 */
	public Customer findByClientIdWithoutStatus(String appId,String openId);

}
