package com.newland.alipayService.blacklist.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.newland.alipayService.base.AlipayBaseDao;
import com.newland.alipayService.blacklist.dao.BlackListDao;
import com.newland.alipayService.blacklist.model.BlackList;

/**
 * 
 * @ClassName: BlacklistDaoImpl
 * @Description: 统一黑名单控制
 * @author Maple
 */
@Service("blackListDao")
public class BlackListDaoImpl extends AlipayBaseDao<BlackList, Integer>
		implements BlackListDao {


	@Override
	public int delByDate(Date date) {
		String hql = "delete from " + BlackList.class.getName()
				+ " b where b.transDate < ?";
		return super.excute(hql, date);
	}

	@Override
	public BlackList findByClientId(String appId, String appUserId, Date date,String type) {
		String hql = "from " + BlackList.class.getName()
				+ " b where b.appId = ? and b.appUserId = ? and b.transDate = ? and b.blackType = ?";
		List<BlackList> list = this.find(hql, appId, appUserId, date,type);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
