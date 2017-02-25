package com.newland.alipayService.dispatch;

import com.newland.alipay.req.Req;
import com.newland.alipayService.session.AlipaySession;
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
