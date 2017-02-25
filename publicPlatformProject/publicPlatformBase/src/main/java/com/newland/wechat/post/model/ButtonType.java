package com.newland.wechat.post.model;

/**
 * 按钮类型
 * 
 * @refer http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html
 * 
 * @author Shiznn
 * 
 */
public enum ButtonType {

	/**
	 * 事件型
	 */
	click,
	/**
	 * 链接型
	 */
	view,
	/**
	 * 弹出位置选择器
	 */
	location_select,
	/**
	 * 扫码推事件
	 */
	scancode_push,
	/**
	 * 扫码推事件且弹出“消息接收中”提示框
	 */
	scancode_waitmsg,
	/**
	 * 弹出系统拍照发图
	 */
	pic_sysphoto,
	/**
	 * 弹出拍照或相册发图
	 */
	pic_photo_or_album,
	/**
	 * 弹出微信相册发图器
	 */
	pic_weixin;
}
