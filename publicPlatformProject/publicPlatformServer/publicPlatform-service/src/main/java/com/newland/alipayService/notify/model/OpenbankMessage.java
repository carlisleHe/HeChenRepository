package com.newland.alipayService.notify.model;

import java.util.Map;

public interface OpenbankMessage {

	/**
	 * 获取消息关联账户
	 * 
	 * @return
	 */
	public abstract String getAccountNo();
	
	/**
	 * 获取应用用户ID
	 * 
	 * @return
	 */
	public abstract String getAppUserId();

	/**
	 * 获取应用ID
	 * 
	 * @return
	 */
	public abstract String getAppId();

	/**
	 * 获取消息类型
	 * 
	 * @return
	 */
	public abstract String getMessageType();

	/**
	 * 获取键值列表
	 * 
	 * @return
	 */
	public abstract Map<String, Object> toParameterMap();

	/**
	 * 设置发送结果
	 * 
	 * @param result
	 */
	public abstract void setResult(String result);

	/**
	 * 设置发送结果备注
	 * 
	 * @param remark
	 */
	public abstract void setRemark(String remark);

}
