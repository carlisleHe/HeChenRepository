package com.cib.alipayserver.session;

import javax.annotation.Resource;

import com.cib.alipayserver.BeanTest;



public class SessionStorageTest extends BeanTest {
	
	@Resource (name = "sessionStorage")
	private SessionStorage<String, AlipaySession> sessionStroage;
	
	public void testSessionStore() throws Exception{
		while(true){
		AlipaySession sess = new AlipaySession();
		sess.setExpireTime(10000);
		sess.setFromUserId("38874293847");
		sess.setSessionId("3298941kskdfkasd");
		sess.setToUserId("2383478");
		sess.addValue("test1", "sdkflskdf");
		this.sessionStroage.put("shizn", sess);
		sess = this.sessionStroage.get("shizn");
		System.err.println(sess.getValue("test1"));
		Thread.sleep(100);
		}
	}

}
