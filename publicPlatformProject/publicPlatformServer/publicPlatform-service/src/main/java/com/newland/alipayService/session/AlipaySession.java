package com.newland.alipayService.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 微信会话类
 * @author Shizn
 *
 */
public class AlipaySession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 会话容器
	 */
	private Map<Object, Object> sessionMap = new HashMap<Object, Object>();
	/**
	 * sessionId
	 */
	private String sessionId; 
	/**
	 * 客户来源openid
	 */
	private String fromUserId;
	/**
	 * 应用openid
	 */
	private String toUserId;

	/**
	 * 过期时间
	 */
	private long expireTime;
	
	public boolean isCustomServiceFlag() {
		return customServiceFlag;
	}

	public void setCustomServiceFlag(boolean customServiceFlag) {
		this.customServiceFlag = customServiceFlag;
	}

	/**
	 * 转人工标志
	 * luwn
	 */
	private boolean customServiceFlag=false;
	
	private boolean active = true;
	
	private SessionManager manager;

	
	public void addValue(String key, Object obj){
		sessionMap.put(key, obj);
		if (this.manager != null) manager.put(this.getFromUserId(), this);
	}
	
	public AlipaySession(){
		this.setExpireTime(SessionManager.DEFAULT_EXPIRE_TIME);
	}

	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String key){
		return (T)sessionMap.get(key);
	}
	
	public void removeValue(String key){
		sessionMap.remove(key);
		if (this.manager != null) manager.put(this.getFromUserId(), this);
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public long getExpireTime() {
		return expireTime;
	}
    /**
     * 设置超时时间，单位秒
     * @param expireTime
     */
	public void setExpireTime(long expireTime) {
		if (this.manager != null) manager.setExpireTime(this.fromUserId, (int)expireTime);
		this.expireTime = System.currentTimeMillis() + expireTime * 1000;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public void invalidate(){
		if(sessionMap !=null){
			sessionMap.clear();
			this.sessionMap = null;
		}
		this.expireTime = -1;
		this.active = false;
	}
	
	public void clear(){
		sessionMap.clear();
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public boolean isActive() {
		return active;
	}
	
	public SessionManager getManager() {
		return manager;
	}

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}


}
