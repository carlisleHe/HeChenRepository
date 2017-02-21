package com.newland.wechatServer.dispatch;

import com.cib.alipay.resp.Resp;

public class RedirectResult extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String actionKey;
	
	private Resp resp;

	public RedirectResult(String actionKey) {
		super(ResultType.CONTEXT);
		this.actionKey = actionKey;
	}

	public String getActionKey() {
		return actionKey;
	}

	public Resp getResp() {
		return resp;
	}

	public void setResp(Resp resp) {
		this.resp = resp;
	}



}
