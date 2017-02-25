package com.newland.alipayService.dispatch;

import com.newland.alipay.req.Req;
import com.newland.alipay.resp.Resp;
import com.newland.alipayService.session.AlipaySession;

public interface Dispatcher {
	
	Resp dispatch(Req req, AlipaySession ses) throws Exception;

}
