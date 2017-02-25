package com.newland.weixinService.session.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.newland.weixinService.session.SessionStorage;
import com.newland.weixinService.session.WeixinSession;
/**
 * session 存储类，与memcache进行同步操作
 * @author Shizn
 *
 */
@Service("sessionStorage")
public class MemSessionStorageImpl implements SessionStorage<String, WeixinSession> {
	
	
	@Override
	public WeixinSession put(String key, WeixinSession value) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.put(key, value);
		
	}

	@Override
	public WeixinSession get(String key) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.get(key);
	}

	@Override
	public WeixinSession remove(String key) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.remove(key);
	}

	@Override
	public void clear() {
		this.maps.clear();
	}
	
	private Map<String, WeixinSession> maps = new HashMap<String, WeixinSession>();

	@Override
	public Map<String, WeixinSession> getMap() {
		return maps;
	}

	@Override
	public void setExpireTime(String key, int expireTime) {
		
	}
}
