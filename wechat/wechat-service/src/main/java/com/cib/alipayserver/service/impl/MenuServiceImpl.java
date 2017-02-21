package com.cib.alipayserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.post.AlipayPostUtils;
import com.cib.alipayserver.alipay.model.AlipayMenu;
import com.cib.alipayserver.alipay.service.AlipayMenuService;
import com.cib.alipayserver.service.MenuService;
import com.newland.wechat.post.model.ButtonItem;
import com.newland.wechat.post.model.Menu;
@Service("menuService")
public class MenuServiceImpl implements MenuService, InitializingBean {
	
	private final static Logger logger = LoggerFactory.getLogger(MenuService.class);
	
	@Resource (name = "alipayMenuService")
	private AlipayMenuService alipayMenuService;
	@Override
	public void addMenu() throws Exception {
		Menu menu = new Menu();
		List<AlipayMenu> allMenus = alipayMenuService
				.findMenuByAppId(AlipayApplication.getAppId());
		if (allMenus.size() > 0) {
			try {
				if(AlipayPostUtils.isExitMenu()){
					AlipayPostUtils.updateMenu(initMenu(allMenus, menu));
				}
				else{
					AlipayPostUtils.addMenu(initMenu(allMenus, menu));
				}
			} catch (Exception e) {
				logger.error("发送菜单失败，原因：" + e.getMessage(), e);
				throw e;
			}
		} else {
			logger.info("find menu items size[" + allMenus.size()+ "] for 支付宝钱包,不进行菜单更新。");
		}

	}
	private Menu initMenu(List<AlipayMenu> allMenus, Menu menu) {
	    logger.info("find menu items size[" + allMenus.size() + "] for 支付宝钱包。" );
		if (CollectionUtils.isEmpty(allMenus)){
			return menu;
		}
		List<AlipayMenu> menuList = this.queryMenuListByParentId(allMenus,AlipayMenu.MAIN_PARENT);
		
		for (int i = 0; i < menuList.size(); i++) {
			AlipayMenu alipayMenu = menuList.get(i);
			ButtonItem root = new ButtonItem();
			root.setName(alipayMenu.getName());
			root.setKey(alipayMenu.getKey());
			root.setButtonType(alipayMenu.getType());
		    logger.info("一级菜单："+ alipayMenu.getName());
			if(alipayMenu.getLevel().equals("0")){
				List<AlipayMenu> subMenus = this.queryMenuListByParentId(allMenus, alipayMenu.getMenuId());
				for (int j = 0; j < subMenus.size(); j++) {
					AlipayMenu subMenu = subMenus.get(j);
					ButtonItem item = new ButtonItem();
					item.setName(subMenu.getName());
					item.setKey(subMenu.getKey());
					item.setButtonType(subMenu.getType());
					logger.info("      二级菜单："+ subMenu.getName());
					  if(subMenu.getLevel().equals("0")){
					        logger.info("支付宝钱包银行不支持三级菜单");
					  }
					root.addSubButton(item);
				}
			}
			menu.addButton(root);
		}
		return menu;
		
	}
	private List<AlipayMenu> queryMenuListByParentId(List<AlipayMenu> allMenuList, Integer parentId) {
        List<AlipayMenu> resultList = new ArrayList<AlipayMenu>();
        for (AlipayMenu Menu : allMenuList) {
            if (Menu.getParent().equals(parentId)) {
                resultList.add(Menu);
            }
        }
        return resultList;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		
		try {
			this.addMenu();
		} catch (Exception e) {
			 logger.error("支付宝钱包银行菜单加载失败",e);
		}
	}

}
