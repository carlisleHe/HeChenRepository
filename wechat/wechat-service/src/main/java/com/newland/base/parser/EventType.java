package com.newland.base.parser;


/**
 * 微信事件类型定义
 * @author Shiznn
 *
 */
public enum EventType {
	/**
	 * 点击事件
	 */
	CLICK,
	/**
	 * 视图
	 */
	VIEW,
	/**
	 * 关注事件
	 */
	subscribe,
	/**
	 * 取消关注事件
	 */
	unsubscribe,
	/**
	 * 模版消息结束事件
	 */
	TEMPLATESENDJOBFINISH,
	/**
	 * 客户地理位置推送事件
	 */
	LOCATION,
	/**
	 * 二维码扫描事件
	 */
	SCAN;

}
