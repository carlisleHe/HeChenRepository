package com.newland.weixinService.dispatch;

import java.io.Serializable;

public abstract class ActionResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 返回类型
	 */
	private ResultType type;
	
	public ActionResult(ResultType type){
		this.type = type;
	}

	
	public ResultType getType() {
		return type;
	}
	public void setType(ResultType type) {
		this.type = type;
	}

}
