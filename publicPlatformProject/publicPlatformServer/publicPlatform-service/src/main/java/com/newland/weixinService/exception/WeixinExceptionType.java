package com.newland.weixinService.exception;

public enum WeixinExceptionType {
	/**
	 * 系统错误
	 */
	SYS_ERR("00", "系统错误"), 
	/**
	 * 微信用户不存在
	 */
	USER_NOTFOUNT("01", "微信用户不存在"), 
	/**
	 * 动户通知信息模板不存在
	 */
	TEMPLATE_NOTFOUNT("02", "信息模板不存在"), 
	/**
	 * 与微信服务器通讯错误
	 */
	WEIXIN_POST_ERROR("03", "与微信服务器通讯错误"), 
	/**
	 * 获取微信ACCESS_TOKEN出错
	 */
	GET_ACCESS_TOKEN_ERR("04", "获取微信ACCESS_TOKEN出错"), 
	/**
	 * 记录被锁定
	 */
	DATA_LOCK_ERR("05", "记录锁定"), 
	/**
	 * 客户不存在
	 */
	CUST_NOFUOUD("06", "客户不存在"), 
	/**
	 * 默认卡未配置
	 */
	DEFAULT_CARD_NOFOUD("07", "未配置默认卡"),
	/**
	 * 获取微信API_TICKET出错
	 */
	GET_API_TICKET_ERR("08", "获取微信API_TICKET出错")
	;
	
	private String code;
	
	private String msg;
	
	WeixinExceptionType(String code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
