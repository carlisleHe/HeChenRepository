package com.newland.wechatServer.dispatch;
/**
 * 操作返回类型定义
 * @author Shizn
 *
 */
public enum ResultType {
	/**
	 * 默认返回类型
	 * 分发处理直接返回resp
	 */
	DEFAULT,
	/**
	 * 重定向类型
	 * 除要包装返回对象外，还要通知分发请求将下个action装入待命
	 * 类似于web重定向处理
	 */
	CONTEXT,
	/**
	 * 转发类型
	 * 转发到指定的action处理
	 */
	FORWARD;
	

}
