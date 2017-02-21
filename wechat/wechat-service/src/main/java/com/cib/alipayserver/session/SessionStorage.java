package com.cib.alipayserver.session;

import java.util.Map;

/**
 * session存储接口
 * @author Shizn
 *
 */
public interface SessionStorage <T, S>{
	/**
	 * 存入
	 * @param key
	 * @param value
	 * @return TODO
	 */
	public AlipaySession put(T key, S value);
	/**
	 * 读取
	 * @param key
	 * @return
	 */
	public S get(T key);
	/**
	 * 移除
	 * @param key
	 */
	public S remove(T key);
	/**
	 * 清除
	 */
	public void clear();
	
	/**
	 * 获取所有
	 * @return
	 */
	public Map<T,S> getMap();
	/**
	 * 设置超时
	 * @param fromUserId
	 * @param expireTime
	 */
	public void setExpireTime(String fromUserId, int expireTime);

}
