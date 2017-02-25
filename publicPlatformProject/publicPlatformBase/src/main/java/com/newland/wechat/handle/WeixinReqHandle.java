package com.newland.wechat.handle;

import com.newland.wechat.req.Req;
import com.newland.wechat.resp.Resp;
/**
 * 微信消息处理接口
 * @author Shizn
 *
 */
public interface WeixinReqHandle {
	/**
	 * 处理微信消息
	 * @param req
	 * @return
	 * @throws Exception
	 */
	Resp handleWeixinReq(Req req) throws Exception;
	/**
	 * 获取验证Token 
	 * @return
	 */
	String getToken();

}
