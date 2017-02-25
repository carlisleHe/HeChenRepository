package com.newland.weixinService.session.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.weixinService.cache.CacheService;
import com.newland.weixinService.session.SessionStorage;
import com.newland.weixinService.session.WeixinSession;

//@Service("sessionStorage")
public class MemcacheSessionStorageImpl implements SessionStorage<String, WeixinSession> {

	@Resource (name = "memcacheService")
	private CacheService<String, Object> memcacheService;
	/**
	 * 本地session缓存，主要用于session过期监控，使用时以内存数据库中数据为准
	 */
	private Map<String, WeixinSession> sessions = new HashMap<String, WeixinSession>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WeixinSession put(String key, WeixinSession value) {
		try {
			int timeout = (int)(value.getExpireTime() - System.currentTimeMillis())/1000;
			this.memcacheService.put(key, value, timeout, false);
			return value;
		} catch (Exception e) {
			logger.error("连接内存数据库时出错", e);
		}finally{
			sessions.put(key, value);
		}
		return value;
	}

	@Override
	public WeixinSession get(String key) {
		try{
			WeixinSession sess = (WeixinSession)this.memcacheService.get(key);
			if (sess != null){
				return sess;
			}else{
				sess = sessions.remove(key);
				if (sess != null) sess.invalidate();
				return null;
			}
		}catch(Exception e){
			logger.error("从内存数据库取数据出错", e);
			WeixinSession sess = sessions.remove(key);
			sess.invalidate();
			return null;
		}
	}

	@Override
	public WeixinSession remove(String key) {
		WeixinSession sess = sessions.remove(key);
		try {
			sess = (WeixinSession)this.memcacheService.remove(key);
		} catch (Exception e) {
			logger.error("移除session出错:[" + key + "]", e);
		}
		return sess;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public Map<String, WeixinSession> getMap() {
		return this.sessions;
	}

	@Override
	public void setExpireTime(String key, int timeout) {
		if (key == null) return;
		try {
			this.memcacheService.expire(key, timeout);
		} catch (Exception e) {
			logger.error("设置超时出错", e);
		}
		
	}

}
