package com.newland.weixinService.event;

import org.springframework.context.ApplicationEvent;

import com.newland.weixinService.session.WeixinSession;
/**
 * 微信超时事件
 * @author dvlp
 *
 */
public class SessionTimeoutEvent extends ApplicationEvent {
	

	public SessionTimeoutEvent(WeixinSession source) {
		super(source);
	}
	
	public WeixinSession getSession(){
		return (WeixinSession)this.getSource();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
