package com.newland.weixinService.session.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import com.newland.wechat.req.Req;
import com.newland.weixinService.event.SessionTimeoutEvent;
import com.newland.weixinService.session.SessionManager;
import com.newland.weixinService.session.SessionStorage;
import com.newland.weixinService.session.WeixinSession;
import com.newland.weixinService.session.id.SessionIdGenerator;

@Service("sessionManager")
public class SessionManagerImpl implements SessionManager, InitializingBean,BeanFactoryAware {
	
	@Resource (name = "sessionStorage")
	private SessionStorage<String, WeixinSession> sessions;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ApplicationEventMulticaster[] casters;
	
	@Override
	public WeixinSession getSession(Req req, boolean create) {
		WeixinSession sess = null;
		if (create){
			sess = remove(req.getFromUserName());
			if (sess != null) sess.invalidate();
			sess = newSession(req);
			put(req.getFromUserName(), sess);
		}else{
			sess = get(req.getFromUserName());
			if (sess == null){
				sess = newSession(req);
				put(req.getFromUserName(), sess);
			}else{
				if (sess.isActive() == false || sess.getExpireTime() < System.currentTimeMillis()){
					sess = remove(req.getFromUserName());
					if (sess != null) {
						this.invalidate(sess);
					}
					sess = newSession(req);
					put(req.getFromUserName(), sess);
				}else{
					sess.setExpireTime(SessionManager.DEFAULT_EXPIRE_TIME);
				}
			}
		}
		return sess;
	}

	private WeixinSession newSession(Req req) {
		logger.debug("新建session:[" + req.getFromUserName() + "]");
		WeixinSession sess = new WeixinSession();
		SessionIdGenerator gen = new SessionIdGenerator();
		sess.setSessionId(gen.generateSessionId());
		sess.setFromUserId(req.getFromUserName());
		sess.setToUserId(req.getToUserName());
		sess.setExpireTime(SessionManager.DEFAULT_EXPIRE_TIME);
		sess.setManager(this);
		return sess;
	}
	/**
	 * session失效监听器
	 * @author dvlp
	 *
	 */
	private static class SessionListener extends Thread{
		

		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private SessionManagerImpl manager;

		public SessionListener(SessionManagerImpl manager){
			this.manager = manager;
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while(true){
				try{
					Map<String, WeixinSession> sessions = new HashMap<String, WeixinSession>();
					sessions.putAll(manager.sessions.getMap());
					if (sessions.isEmpty()){
						continue;
					}
					for (Map.Entry<String , WeixinSession> entry:sessions.entrySet()){
						//session有可能已被invalid，此时可以直接移除
						if(entry.getValue() == null){
							manager.sessions.remove(entry.getKey());
							continue;
						}
						else{
							if (entry.getValue().getExpireTime() < System.currentTimeMillis()){
								
								manager.remove(entry.getKey());
								manager.invalidate(entry.getValue());
							}
						}
						
					}
					sleep(5000);
				}catch(Exception e){
					logger.error("监控session超时错误", e);
				}
			}
		}
	}
	
	private SessionListener listener;

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, ApplicationEventMulticaster> map = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)factory, ApplicationEventMulticaster.class);
		if (map != null){
			logger.debug("事件监听器：[" + map.size() + "]");
			this.casters = map.values().toArray(new ApplicationEventMulticaster[map.size()]);
		}
		listener = new SessionListener(this);
		listener.start();
	}

    private void fireTimeoutEvent(WeixinSession value) {
		if (this.casters != null){
			SessionTimeoutEvent event = new SessionTimeoutEvent(value);
			logger.debug("session 失效！" + value.getFromUserId());
			for (ApplicationEventMulticaster caster:this.casters){
				caster.multicastEvent(event);				
			}
		}
	}
    
	@Override
	public WeixinSession put(String key, WeixinSession value){
		return this.sessions.put(key, value);
	}
	
	@Override
	public WeixinSession get(String key){
		WeixinSession sess =  this.sessions.get(key);
		if (sess != null) sess.setManager(this);
		return sess;
	}
	
	@Override
	public WeixinSession remove(String key){
		WeixinSession sess =   this.sessions.remove(key);
		if (sess != null) {
			sess.setManager(this);
			sess.invalidate();
		}
		return sess;
	}
	
	@Override
    public void invalidate(WeixinSession sess){
		synchronized(sess){	
			fireTimeoutEvent(sess);
		}
    }
    
	private BeanFactory factory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.factory = beanFactory;
	}

	@Override
	public void setExpireTime(String key, int timeout) {
		this.sessions.setExpireTime(key, timeout);
	}

}
