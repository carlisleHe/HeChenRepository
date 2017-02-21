package com.cib.alipayserver.notify.dao;

import java.util.List;

import com.cib.alipayserver.notify.model.EventNotify;
import com.intensoft.dao.GenericDao;

/**
 * 
 * @Description: 待发送事件通知Dao
 * @ClassName:EventNotifyDao
 * @author:fangsq
 * @date:2014-8-18
 */
public interface EventNotifyDao extends GenericDao<EventNotify, Integer> {
	/**
	 * 查询需要发送的事件消息 <br/>
	 * 查询规则：<br/>
	 * ①按照交易日期和交易时间的升序排列(无索引) <br/>
	 * 
	 * @param beginMsgId
	 *            起始消息Id
	 * @param limitSize
	 *            限制大小
	 * @return
	 */
	public List<EventNotify> findPenddingMsg(int beginMsgId, int limitSize);

	/**
	 * 删除已发送（无论成功或失败）的事件消息<br/>
	 * 该方法取首尾的MsgId作为条件，所以必须是连续的消息段
	 * 
	 * @param messages
	 */
	public void deleteBlockMsgs(List<EventNotify> messages);
}
