package com.cib.alipayserver.session.impl;

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

import com.cib.alipay.req.Req;
import com.cib.alipayserver.event.SessionTimeoutEvent;
import com.cib.alipayserver.session.AlipaySession;
import com.cib.alipayserver.session.SessionManager;
import com.cib.alipayserver.session.SessionStorage;
import com.cib.alipayserver.session.id.SessionIdGenerator;

@Service("sessionManager")
public class SessionManagerImpl implements SessionManager, InitializingBean,BeanFactoryAware {
	
	@Resource (name = "sessionStorage")
	private SessionStorage<String, AlipaySession> sessions;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ApplicationEventMulticaster[] casters;
	
	@Override
	public AlipaySession getSession(Req req, boolean create) {
		AlipaySession sess = null;
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

	private AlipaySession newSession(Req req) {
		logger.debug("新建session:[" + req.getFromUserName() + "]");
		AlipaySession sess = new AlipaySession();
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
					Map<String, AlipaySession> sessions = new HashMap<String, AlipaySession>();
					sessions.putAll(manager.sessions.getMap());
					if (sessions.isEmpty()){
						continue;
					}
					for (Map.Entry<String , AlipaySession> entry:sessions.entrySet()){
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

    private void fireTimeoutEvent(AlipaySession value) {
		if (this.casters != null){
			SessionTimeoutEvent event = new SessionTimeoutEvent(value);
			logger.debug("session 失效！" + value.getFromUserId());
			for (ApplicationEventMulticaster caster:this.casters){
				caster.multicastEvent(event);				
			}
		}
	}
    
	@Override
	public AlipaySession put(String key, AlipaySession value){
		return this.sessions.put(key, value);
	}
	
	@Override
	public AlipaySession get(String key){
		AlipaySession sess =  this.sessions.get(key);
		if (sess != null) sess.setManager(this);
		return sess;
	}
	
	@Override
	public AlipaySession remove(String key){
		AlipaySession sess =   this.sessions.remove(key);
		if (sess != null) {
			sess.setManager(this);
			sess.invalidate();
		}
		return sess;
	}
	
	@Override
    public void invalidate(AlipaySession sess){
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
