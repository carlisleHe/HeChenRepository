package com.newland.weixinService.cache.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.InitializingBean;

import com.intensoft.exception.AppBizException;
import com.newland.weixinService.cache.CacheService;
import com.newland.weixinService.exception.WeixinException;
import com.newland.weixinService.exception.WeixinExceptionType;

public class MemcacheServiceImpl implements CacheService<String, Object>, InitializingBean {
	
	private String servers;
	
	private int cacheExpire = 3600;
	
	private MemcachedClient memcachedClient;


	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	private MemcachedClient getCacheClient() throws Exception{
		return this.memcachedClient;
	}


	@Override
	public Object get(String key) throws AppBizException {
		try {
			return this.getCacheClient().get(key);
		} catch (Exception e) {
			throw new WeixinException(WeixinExceptionType.SYS_ERR, e);
		}
	}


	@Override
	public void put(String key, Object value, boolean lock) throws AppBizException{
		this.put(key, value, cacheExpire, false);
	}


	@Override
	public Object remove(String key) throws AppBizException{
		Object obj = null;
		try{
			obj = this.getCacheClient();
			this.getCacheClient().delete(key);
		}catch(Exception e){
			throw new WeixinException(WeixinExceptionType.SYS_ERR, e);
		}
		return obj;
	}


	@Override
	public void put(String key, Object value, int timeout, boolean lock) throws AppBizException{
		try{
			if (lock){
				this.getCacheClient().set(key, timeout, value);
			}else{
				boolean res = this.getCacheClient().add(key, timeout, value);
				if (res == false){
					throw new WeixinException(WeixinExceptionType.DATA_LOCK_ERR);
				}
			}
		}catch(Exception e){
			throw new WeixinException(WeixinExceptionType.SYS_ERR, e);
		}
	}


	@Override
	public void expire(String key, int timeout) throws AppBizException{
		Object obj = this.get(key);
		if (obj != null){
			try {
				this.getCacheClient().set(key, timeout, obj);
			} catch (Exception e) {
				throw new WeixinException(WeixinExceptionType.SYS_ERR, e);
			}
		}
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public int getCacheExpire() {
		return cacheExpire;
	}

	public void setCacheExpire(int cacheExpire) {
		this.cacheExpire = cacheExpire;
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

}
