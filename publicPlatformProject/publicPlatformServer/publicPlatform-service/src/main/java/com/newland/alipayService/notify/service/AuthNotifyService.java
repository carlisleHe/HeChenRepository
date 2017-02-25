package com.newland.alipayService.notify.service;

import java.util.List;

import com.newland.alipayService.notify.model.AuthNotify;
/**
 * 
 * @ClassName: AuthNotifyService  
 * @Description: 通知授权服务类 
 * @author: xuzz 
 * @date:2014-3-20 下午01:01:06
 */
public interface AuthNotifyService {

	/**
	 * 根据通知类型查询授权信息
	 * 
	 * @param appId
	 *            应用ID
	 * @param appUserId
	 *            客户ID
	 * @param acctNo
	 *            账号信息
	 * @param notifyTypes
	 *            通知类型
	 * @return 授权通知信息
	 */
	public AuthNotify findAuthNotify(String appId, String appUserId,
			String acctNo, String... notifyTypes);
	/**
	 * 绑定通知授权信息
	 * @param authNotify
	 * @return 授权信息
	 */
	public AuthNotify bindAuthNotify(AuthNotify authNotify);
	/**
	 * 解绑定通知授权信息
	 * @param authNotify
	 */
	public void unbindAuthNotify(AuthNotify authNotify);
	/**
	 * 根据卡号查询通知授权绑定信息
	 * @param appId 应用ID
	 * @param appUserId 客户ID
	 * @param acctNo 卡号
	 * @return
	 */
	public List<AuthNotify> findAuthNotifyByAcctNo(String appId,String appUserId,String acctNo);
	/**
	 * 更新通知授权信息
	 * @param authNotify
	 * @return
	 */
	public AuthNotify updAuthNotify(AuthNotify authNotify);

}
