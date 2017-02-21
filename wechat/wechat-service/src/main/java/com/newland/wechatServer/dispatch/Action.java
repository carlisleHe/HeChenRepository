package com.newland.wechatServer.dispatch;

import com.cib.alipay.req.Req;
import com.cib.alipayserver.session.AlipaySession;
/**
 * 微信操作类接口定义
 * @author Shizn
 *
 */
public interface Action {
	/**
	 * 执行action操作
	 * @param req
	 * @param sess
	 * @return
	 * @throws Exception
	 */
	ActionResult execute(Req req, AlipaySession sess)throws Exception;
	/**
	 * 获取actionKey值
	 * @return
	 */
	public String getActionKey();

}
