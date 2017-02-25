package com.newland.weixinService.dispatch;

import com.newland.wechat.req.Req;
import com.newland.weixinService.session.WeixinSession;
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
	ActionResult execute(Req req, WeixinSession sess)throws Exception;
	/**
	 * 获取actionKey值
	 * @return
	 */
	public String getActionKey();

}
