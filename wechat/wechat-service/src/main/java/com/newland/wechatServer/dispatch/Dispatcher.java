package com.newland.wechatServer.dispatch;

import com.cib.alipay.req.Req;
import com.cib.alipay.resp.Resp;
import com.cib.alipayserver.session.AlipaySession;

public interface Dispatcher {
	
	Resp dispatch(Req req, AlipaySession ses) throws Exception;

}
