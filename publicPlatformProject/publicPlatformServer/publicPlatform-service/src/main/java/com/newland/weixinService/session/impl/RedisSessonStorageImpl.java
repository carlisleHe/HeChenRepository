package com.newland.weixinService.session.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.weixinService.cache.CacheService;
import com.newland.weixinService.session.SessionStorage;
import com.newland.weixinService.session.WeixinSession;

//@Service("sessionStorage")
public class RedisSessonStorageImpl implements SessionStorage<String, WeixinSession> {
	
	@Resource (name = "redisService")
	private CacheService<byte[], byte[]> redisService;
	/**
	 * 本地session缓存，主要用于session过期监控，使用时以内存数据库中数据为准
	 */
	private Map<String, WeixinSession> sessions = new HashMap<String, WeixinSession>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public WeixinSession put(String key, WeixinSession value) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(value);
			out.flush();
			int timeout = (int)(value.getExpireTime() - System.currentTimeMillis())/1000;
			this.redisService.put(key.getBytes(), bout.toByteArray(), timeout, false);
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
			byte[] str = this.redisService.get(key.getBytes());
			logger.debug("对象序列化值：[" + str + "]");
			if (str != null){
				byte[] bufs = str;
				ByteArrayInputStream bin = new ByteArrayInputStream(bufs);
				ObjectInputStream in = new ObjectInputStream(bin);
				Object obj = in.readObject();
				if (obj != null){
					WeixinSession session = (WeixinSession)obj;
					sessions.put(key, session);
					return (WeixinSession)obj;
				}else{
					sessions.remove(key);
					return null;
				}
			}else{
				sessions.remove(key);
				return null;
			}
		}catch(Exception e){
			logger.error("从内存数据库取数据出错", e);
			sessions.remove(key);
			return null;
		}
	}

	@Override
	public WeixinSession remove(String key) {
		WeixinSession sess = sessions.remove(key);
		try {
			this.redisService.remove(key.getBytes());
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
			this.redisService.expire(key.getBytes(), timeout);
		} catch (Exception e) {
			logger.error("设置超时出错", e);
		}
		
	}
}
