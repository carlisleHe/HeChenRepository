package com.newland.alipayService.blacklist.dao;

import java.util.Date;

import com.intensoft.dao.GenericDao;
import com.newland.alipayService.blacklist.model.BlackList;

/***
 * 
 * @ClassName: BlacklistDao
 * @Description: 黑名单DAO
 * @author Maple
 */
public interface BlackListDao extends GenericDao<BlackList, Integer> {
	/**
	 * 删除指定日期前的所有记录
	 * @param date
	 */
	public int delByDate(Date date);

	
	/**
	 * 根据指定应用名用户ID及日期查找黑名单记录
	 * 
	 * @param appid
	 * @param appUserId
	 * @param date
	 * @param type
	 * @return
	 */
	public BlackList findByClientId(String appid, String appUserId, Date date,String type);

}
