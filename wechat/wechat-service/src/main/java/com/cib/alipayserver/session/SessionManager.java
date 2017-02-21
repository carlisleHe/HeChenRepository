package com.cib.alipayserver.session;

import com.cib.alipay.req.Req;

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
	AlipaySession getSession(Req req, boolean create);
	/**
	 * 保存session
	 * @param key
	 * @param value
	 * @return
	 */
	AlipaySession put(String key, AlipaySession value);
	/**
	 * 获取session
	 * @param key
	 * @return
	 */
	AlipaySession get(String key);
	/**
	 * 移除session
	 * @param key
	 * @return
	 */
	AlipaySession remove(String key);
	/**
	 * 设置超时
	 * @param key
	 * @param timeout
	 */
	void setExpireTime(String key, int timeout);
	void invalidate(AlipaySession sess);
	

}
