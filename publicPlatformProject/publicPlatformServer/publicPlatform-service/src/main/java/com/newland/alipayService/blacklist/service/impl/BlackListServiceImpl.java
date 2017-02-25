package com.newland.alipayService.blacklist.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppRTException;
import com.newland.alipayService.blacklist.dao.BlackListDao;
import com.newland.alipayService.blacklist.model.BlackList;
import com.newland.alipayService.blacklist.service.BlackListService;
import com.newland.base.common.AppExCode;
/**
 * 
 * 
 * @ClassName: BlackListServiceImpl  
 * @Description: 黑名单服务实现类
 * @author: xuzz 
 * @date:2014-4-4
 */
@Service("blackListService")
public class BlackListServiceImpl implements BlackListService {
	@Resource(name = "blackListDao")
	private BlackListDao blackListDao;

	@Override
	public BlackList findBlackListByTransDate(String appId,String appUserId,Date date, String type) {
		return blackListDao.findByClientId(appId, appUserId, date, type);
	}

	@Override
	public BlackList saveBlackList(BlackList blackList) {
		return blackListDao.save(blackList);
	}

	@Override
	public BlackList updBlackList(BlackList blackList) {
		return blackListDao.update(blackList);
	}

	@Override
	public int delBlackListByDate(Date date) {
		return blackListDao.delByDate(date);
	}
	@Override
	public BlackList acctErrCount(String appId, String appUserId,Date date,String type) {
		//1、基础条件判断
		if(StringUtils.isBlank(appUserId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(date == null){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单登记日期不能为空！"); 
		}
		if(StringUtils.isBlank(type)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单类型不能为空！"); 
		}
		//2、查询黑名单记录
		BlackList blackList = findBlackListByTransDate(appId, appUserId, date, type);
		//新增
		if(blackList == null){
			blackList = new BlackList();
			blackList.setAppId(appId);
			blackList.setAppUserId(appUserId);
			blackList.setApplyErr(1);
			blackList.setTransDate(date);
			blackList.setBlackType(type);
			saveBlackList(blackList);
		}
		//更新
		else{
			blackList.setApplyErr(blackList.getApplyErr()+1);
			updBlackList(blackList);
		}
		return blackList;
	}

	@Override
	public void clearErrCount(String appId, String appUserId,Date date,String type) {
		//1、基础条件判断
		if(StringUtils.isBlank(appUserId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(date == null){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单登记日期不能为空！"); 
		}
		if(StringUtils.isBlank(type)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单类型不能为空！"); 
		}
		//2、查询黑名单记录
		BlackList blackList = findBlackListByTransDate(appId, appUserId, date, type);
		if(blackList == null){
			throw new AppRTException(AppExCode.CHECK_ERROR, String.format("黑名单记录不存在！用户ID:[%s],客户ID:[%s],类型:[%s]", appId,appUserId,type));
		}
		//清空错误次数
		blackList.setApplyErr(0);
		updBlackList(blackList);
	}

	@Override
	public BlackList findBlackList(String appId, String appUserId,Date date,String type) {
		//1、基础条件判断
		if(StringUtils.isBlank(appUserId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用客户ID不能为空！");
		}
		if(StringUtils.isBlank(appId)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "应用ID不能为空！"); 
		}
		if(date == null){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单登记日期不能为空！"); 
		}
		if(StringUtils.isBlank(type)){
			throw new AppRTException(AppExCode.CHECK_ERROR, "黑名单类型不能为空！"); 
		}
		return findBlackListByTransDate(appId, appUserId, date, type);
	}
}
