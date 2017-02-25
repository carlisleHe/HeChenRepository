package com.cib.alipayserver.alipay;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.cib.alipayserver.BeanTest;
import com.newland.alipay.env.AlipayApplication;
import com.newland.alipay.post.model.ButtonType;
import com.newland.alipayService.alipay.model.AlipayMenu;
import com.newland.alipayService.alipay.service.AlipayMenuService;

public class AlipayMenuServiceTest extends BeanTest{
	@Resource
	private AlipayMenuService alipayMenuService;
	@Test
	public void testMenuAdd(){
		AlipayMenu alipayMenu = new AlipayMenu();
		alipayMenu.setAppId(AlipayApplication.getAppId());
		alipayMenu.setCreTime(new Date());
		alipayMenu.setKey("click.finbalance");
		alipayMenu.setLevel("0");
		alipayMenu.setMenuId(1);
		alipayMenu.setName("余额查询");
		alipayMenu.setParent(-1);
		alipayMenu.setSeq(1);
		alipayMenu.setType(ButtonType.out);
		alipayMenuService.saveAlipayMenu(alipayMenu);
	}

}
