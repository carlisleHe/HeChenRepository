package com.cib.alipay.handle.copy;

import com.cib.alipay.req.Req;
import com.cib.alipay.resp.Resp;
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
