package com.cib.alipayserver.event;

import org.springframework.context.ApplicationEvent;

import com.cib.alipayserver.session.AlipaySession;
/**
 * 微信超时事件
 * @author dvlp
 *
 */
public class SessionTimeoutEvent extends ApplicationEvent {
	

	public SessionTimeoutEvent(AlipaySession source) {
		super(source);
	}
	 
	public AlipaySession getSession(){
		return (AlipaySession)this.getSource();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
