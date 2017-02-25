package com.newland.alipayService.customer.service;

import com.newland.alipayService.customer.model.Customer;



/**
 * 
 * @ClassName: AuthCustomerService  
 * @Description: 用户授权服务类 
 * @author: xuzz 
 * @date:2014-3-20 下午03:50:44
 */
public interface CustomerService {
	/**
	 * 根据卡授权绑定用户授权信息
	 * <h3>注意：</h3>
	 * <ol>
	 * <li>一期系统并未上OAUTH统一认证服务，所以也就无需进行所谓的用户授权信息登记，但是考虑到系统的后续规划，
	 * 为了保证数据的完整性，在一期进行卡授权登记的时候，会自动级联设置相关的用户授权信息，以便于后续的数据统一维护。
	 * </li>
	 * <li>该方法在二期OAUTH上线后应当禁止使用或者进行改造。</li>
	 * <ol>
	 * @param authCustomer
	 * @return 用户授权服务信息
	 */
	public Customer bindAuthCustomer(Customer authCustomer);
	/**
	 * 根据客户ID查询用户授权信息
	 * @param appId 应用ID
	 * @param openId 客户ID
	 * @return 状状有效的数据
	 */
	
	public Customer findAuthCustomerByClientId(String appId,String openId);
	/**
	 * 根据客户ID查询用户授权信息
	 * @param appId 应用ID
	 * @param openId 客户ID
	 * @return 无关状态的数据
	 */
	public Customer findAuthCustomerByClientIdWithoutStatus(String appId,String openId);
	/**
	 * 更新用户授权信息
	 * @param authCustomer
	 * @return
	 */
	public Customer updAuthCustomer(Customer authCustomer);
	/**
	 * 解绑授权信息
	 * @param authCustomer
	 */
	public void unbindAuthCustomer(Customer authCustomer);
	
	
	/**
	 * 根据应用ID与客户ID查询用户授权信息
	 * @param appId
	 * @param openId
	 * @return
	 */
	public Customer findAuthCustomer(String appId,String openId);

	/**
	 * 默认卡管理
	 * @param appId 应用ID
	 * @param openId 客户ID
	 * @param acctNo 账号
	 * @param operType 操作类型，绑定：1;解绑：0
	 * @return
	 */
	public Customer defaultCardUpd(String appId, String openId,String acctNo,String operType);

}
