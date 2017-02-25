package com.newland.alipayService.notify.dao;

import java.util.Date;
import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.alipayService.notify.model.AuthNotify;

/**
 * 
 * @ClassName: AuthNotifyDao
 * @Description: 通知授权DAO
 * @author: xuzz
 * @date:2014-3-22 下午04:35:04
 */
public interface AuthNotifyDao extends GenericDao<AuthNotify, String> {
	/**
	 * 查询通知授权信息
	 * 
	 * @param appId
	 *            应用ID
	 * @param appUserId
	 *            应用用户ID
	 * @param acctNo
	 *            绑定卡号
	 * @param notifyTypes
	 *            通知授权类型(动户通知;营销通知)
	 * @return
	 */
	public AuthNotify findByClientNotifyType(String appId, String appUserId,
			String acctNo, String... notifyTypes);

	/**
	 * 根据更新时间和状态找出所有的通知绑定记录（分页）
	 * 
	 * @param updateTime
	 * @param status
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<AuthNotify> findByUpdateTime(Date updateTime,
			String status, int startIndex, int pageSize);
	
	/**
	 * 根据更新时间找出所有的通知绑定记录（分页）
	 * 
	 * @param updateTime
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<AuthNotify> findByUpdateTime(Date updateTime, int startIndex,
			int pageSize);

	/**
	 * 查询通知授权信息
	 * 
	 * @param appId
	 *            应用ID
	 * @param appUserId
	 *            应用用户ID
	 * @param acctNo
	 *            绑定卡号
	 * @param notifyType
	 *            通知授权类型(动户通知;营销通知)
	 * @return
	 */
	public AuthNotify findByClientNotifyTypeWithoutStatus(String appId, String appUserId,
			String acctNo, String notifyType);
	/**
	 * 查询通知授权信息
	 * 
	 * @param appId
	 *            应用ID
	 * @param appUserId
	 *            应用用户ID
	 * @param acctNo
	 *            绑定卡号
	 * @return
	 */
	public List<AuthNotify> findByAcctNo(String appId, String appUserId,String acctNo);

	/**
	 * 后台 根据账号类型和通知类型查询动户通知开通数
	 * @param acctType
	 * @param notifyType
	 */
	public int countAuthNotify(String appId,String acctType, String notifyType);

}
