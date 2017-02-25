package com.newland.alipay.handle;

import com.newland.alipay.req.Req;
import com.newland.alipay.resp.Resp;
/**
 * 微信消息处理接口
 * @author Shizn
 *
 */
public interface AlipayReqHandle {
	/**
	 * 处理微信消息
	 * @param req
	 * @return
	 * @throws Exception
	 */
	Resp handleAlipayReq(Req req) throws Exception;

}
