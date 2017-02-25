package com.newland.weixinService.session;

import com.newland.wechat.req.Req;

/**
 * 会话管理类
 * @author Shizn
 *
 */
public interface SessionManager {
	
	
	public static final int DEFAULT_EXPIRE_TIME = 300;//单位秒
	/**
	 * 获取微信会话
	 * @param req
	 * @param create
	 * @return
	 */
	WeixinSession getSession(Req req, boolean create);
	/**
	 * 保存session
	 * @param key
	 * @param value
	 * @return
	 */
	WeixinSession put(String key, WeixinSession value);
	/**
	 * 获取session
	 * @param key
	 * @return
	 */
	WeixinSession get(String key);
	/**
	 * 移除session
	 * @param key
	 * @return
	 */
	WeixinSession remove(String key);
	/**
	 * 设置超时
	 * @param key
	 * @param timeout
	 */
	void setExpireTime(String key, int timeout);
	void invalidate(WeixinSession sess);
	

}
