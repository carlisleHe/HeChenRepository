package com.newland.weixinService.dispatch;

import com.newland.wechat.resp.Resp;

public class DefaultResult extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Resp resp;

	public DefaultResult(Resp resp) {
		super(ResultType.DEFAULT);
		this.resp = resp;
	}

	public Resp getResp() {
		return resp;
	}
}
