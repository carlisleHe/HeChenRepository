package com.newland.alipay.parser;


/**
 * 微信事件类型定义
 * @author Shiznn
 *
 */
public enum EventType {
	/**
	 * 点击事件
	 */
	click,
	/**
	 * 关注
	 */
	follow,
	/**
	 * 取消关注
	 */
	unfollow,
	/**
	 * 进入用户进入服务窗页面，包括用户点击和扫码二维码等方式进入服务窗
	 */
	enter,
	/**
	 * 验证
	 */
	verifygw

}
