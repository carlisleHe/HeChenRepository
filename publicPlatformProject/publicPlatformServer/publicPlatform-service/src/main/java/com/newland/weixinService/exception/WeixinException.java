package com.newland.weixinService.exception;

import com.intensoft.exception.AppBizException;

public class WeixinException extends AppBizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private WeixinExceptionType type;
	
	public WeixinException(WeixinExceptionType type){
		super(type.getCode(), type.getMsg());
	}

	public WeixinException(WeixinExceptionType type, String message) {
		super(type.getCode(), message);
	}

	public WeixinException(WeixinExceptionType type, Exception e) {
		super(type.getCode(), e.getMessage());
	}

	public WeixinExceptionType getType() {
		return type;
	}

}
