package com.newland.wechatServer.dispatch;

import com.cib.alipay.req.Req;

public class ForwardResult extends ActionResult {
	

	private String actionKey;
	
	private static final long serialVersionUID = 1L;
	
	private Req overrideReq;

	public ForwardResult(String actionKey) {
		super(ResultType.FORWARD);
		this.actionKey = actionKey;
	}
	
	public ForwardResult(String actionKey, Req overrideReq){
		super(ResultType.FORWARD);
		this.actionKey = actionKey;
		this.overrideReq = overrideReq;
	}

	public String getActionKey() {
		return actionKey;
	}

	public Req getOverrideReq() {
		return overrideReq;
	}

	public void setOverrideReq(Req overrideReq) {
		this.overrideReq = overrideReq;
	}

	
}
