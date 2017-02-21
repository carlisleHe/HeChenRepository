package com.cib.alipayserver.session.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.session.AlipaySession;
import com.cib.alipayserver.session.SessionStorage;
/**
 * session 存储类，与memcache进行同步操作
 * @author Shizn
 *
 */
@Service("sessionStorage")
public class MemSessionStorageImpl implements SessionStorage<String, AlipaySession> {
	
	
	@Override
	public AlipaySession put(String key, AlipaySession value) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.put(key, value);
		
	}

	@Override
	public AlipaySession get(String key) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.get(key);
	}

	@Override
	public AlipaySession remove(String key) {
		if (StringUtils.isBlank(key)) return null;
		return this.maps.remove(key);
	}

	@Override
	public void clear() {
		this.maps.clear();
	}
	
	private Map<String, AlipaySession> maps = new HashMap<String, AlipaySession>();

	@Override
	public Map<String, AlipaySession> getMap() {
		return maps;
	}

	@Override
	public void setExpireTime(String key, int expireTime) {
		
	}
}
