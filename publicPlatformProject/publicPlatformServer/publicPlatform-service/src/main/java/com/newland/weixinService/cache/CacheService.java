package com.newland.weixinService.cache;

import com.intensoft.exception.AppBizException;

/**
 * Redis内存数据库接口
 * @author dvlp
 *
 */
public interface CacheService <T, S>{
	/**
	 * 获取值
	 * @param key
	 * @return
	 * @throws AppBizException TODO
	 */
	public S get(T key) throws AppBizException;
	/**
	 * put 数据
	 * @param key
	 * @param value
	 * @param lock TODO
	 * @throws AppBizException TODO
	 */
	public void put(T key, S value, boolean lock) throws AppBizException;
	/**
	 * 移除数据
	 * @param key
	 * @return
	 * @throws AppBizException TODO
	 */
	public S remove(T key) throws AppBizException;
	/**
	 * put 数据设置超时
	 * @param key
	 * @param value
	 * @param timeout
	 * @param lock TODO
	 * @throws AppBizException TODO
	 */
	void put(T key, S value, int timeout, boolean lock) throws AppBizException;
	/**
	 * 设置超时
	 * @param key
	 * @param timeout
	 * @throws AppBizException TODO
	 */
	void expire(T key, int timeout) throws AppBizException;
	

}
