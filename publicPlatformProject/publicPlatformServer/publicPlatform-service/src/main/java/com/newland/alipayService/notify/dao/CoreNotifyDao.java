package com.newland.alipayService.notify.dao;

import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.alipayService.notify.model.CoreNotify;

/**
 * 
 * @Description: 待发送动户通知Dao
 * @ClassName:CoreNotifyDao
 * @author:fangsq
 * @date:2014-8-18
 */
public interface CoreNotifyDao extends GenericDao<CoreNotify, Integer> {
	/**
	 * 查询需要发送的动户消息 <br/>
	 * 查询规则：<br/>
	 * ①按照交易日期和交易时间的升序排列(无索引) <br/>
	 * 
	 * @param beginMsgId
	 *            起始消息Id
	 * @param limitSize
	 *            限制大小
	 * @return
	 */
	public List<CoreNotify> findPenddingMsg(int beginMsgId, int limitSize);

	/**
	 * 删除已发送（无论成功或失败）的动户消息<br/>
	 * 该方法取首尾的MsgId作为条件，所以必须是连续的消息段
	 * 
	 * @param messages
	 */
	public void deleteBlockMsgs(List<CoreNotify> messages);
}
