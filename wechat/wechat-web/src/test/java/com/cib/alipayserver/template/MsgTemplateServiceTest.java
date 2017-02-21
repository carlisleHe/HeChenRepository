package com.cib.alipayserver.template;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cib.alipayserver.BeanTest;
import com.newland.wechatServer.template.service.MsgTemplateService;

public class MsgTemplateServiceTest extends BeanTest{
	
	@Autowired
	MsgTemplateService msgTemplateService;
	@Test
	public void test(){
		System.err.println("ok");
	}

}
