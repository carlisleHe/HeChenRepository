package com.newland.weixinService.dispatch;

import com.newland.wechat.req.Req;
import com.newland.wechat.resp.Resp;
import com.newland.weixinService.session.WeixinSession;

public interface Dispatcher {
	
	Resp dispatch(Req req, WeixinSession ses) throws Exception;

}
