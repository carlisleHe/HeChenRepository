package com.cib.alipayserver.wechat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.cib.alipayserver.BeanTest;
import com.newland.wechat.post.model.ButtonType;
import com.newland.wechatServer.alipay.model.AlipayMenu;
import com.newland.wechatServer.alipay.service.AlipayMenuService;

public class WechatMenuServiceTest extends BeanTest{
	@Resource (name = "alipayMenuService")
	private AlipayMenuService alipayMenuService;
	
	
	public void testAddMenu() throws Exception{
		AlipayMenu menu = new AlipayMenu();
		menu.setCreTime(new Date());
		menu.setKey("5");
		menu.setLevel("1");
		menu.setParent(2);
		menu.setName("二级一");
		menu.setType(ButtonType.out);
		menu.setSeq(1);
		menu.setRemark("测试1111菜单2...");
		alipayMenuService.saveAlipayMenu(menu);		
	}
	
	public  void testGetMenu() throws Exception{
	List list =	alipayMenuService.findAll();
	System.out.println(list.size());
	}

}
