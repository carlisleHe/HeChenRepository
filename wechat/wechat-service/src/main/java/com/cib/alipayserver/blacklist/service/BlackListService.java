package com.cib.alipayserver.blacklist.service;

import java.util.Date;

import com.cib.alipayserver.blacklist.model.BlackList;

/**
 * 
 * 
 * @ClassName: BlackListService
 * @Description: 黑名单服务类
 * @author: xuzz
 * @date:2014-4-3
 */
public interface BlackListService {
	// 卡授权黑名单
	public static final String BLACK_LIST_TYPE_AUTH_CARD = "00";
	// 动户通知黑名单
	public static final String BLACK_LIST_TYPE_NOTIFY = "01";
	// 卡授权错误最大数
	public static final int BLACK_LIST_AUTH_CARD_MAI = 10;
	// 动户通知错误最大数
	public static final int BLACK_LIST_NOTIFY_MAI = 10;

	/**
	 * 登记黑名单
	 * 
	 * @param blackList
	 * @return
	 */
	public BlackList saveBlackList(BlackList blackList);

	/**
	 * 根据登记日期查询
	 * 
	 * @param appId
	 *            应用ID
	 * @param appUserId
	 *            客户ID
	 * @param date
	 *            登记日期
	 * @param type
	 *            黑名单类型
	 * @return
	 */
	public BlackList findBlackListByTransDate(String appId, String appUserId,
			Date date, String type);

	/**
	 * 更新黑名单
	 * 
	 * @param blackList
	 * @return
	 */
	public BlackList updBlackList(BlackList blackList);

	/**
	 * 根据交易日期清除旧数据
	 * 
	 * @param date
	 *            交易日期
	 * @return
	 */
	public int delBlackListByDate(Date date);
	/**
	 * 累加黑名单错误次数<br/>
	 * <p>累加时如果不存在则新增黑名单记录，存在则对原有错误次数进行累加</p>
	 * @param appId 应用ID
	 * @param appUserId 客户ID
	 * @param date 登记日期
	 * @param type 黑名单类型
	 * @return
	 */
	public BlackList acctErrCount(String appId, String appUserId,Date date,String type);
	/**
	 * 清空错误次数
	 * @param appId 应用ID
	 * @param appUserId 客户ID
	 * @param date 登记日期
	 * @param type 黑名单类型
	 */
	public void clearErrCount(String appId,String appUserId,Date date,String type);
	/**
	 * 根据黑名单类型查询对应黑名单
	 * @param appId 应用id
	 * @param appUserId 客户ID
	 * @param date 登记日期
	 * @param type 黑名单类型
	 * @return
	 */
	public BlackList findBlackList(String appId,String appUserId,Date date,String type);

}
