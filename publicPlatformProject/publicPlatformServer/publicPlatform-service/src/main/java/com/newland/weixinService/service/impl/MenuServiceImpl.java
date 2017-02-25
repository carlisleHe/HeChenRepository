package com.newland.weixinService.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.post.WeixinPostUtils;
import com.newland.wechat.post.model.ButtonItem;
import com.newland.wechat.post.model.Menu;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.service.MenuService;
import com.newland.weixinService.wechat.model.WechatMenu;
import com.newland.weixinService.wechat.service.WechatMenuService;
@Service("menuService")
public class MenuServiceImpl implements MenuService, InitializingBean {
	
	private final static Logger logger = LoggerFactory
			.getLogger(MenuService.class);
	
	@Resource (name = "wechatMenuService")
	private WechatMenuService wechatMenuService;
	
	@Resource (name = "accessTokenService")
	private AccessTokenService accessTokenService;
	@Value("@[wx_menu_create]")
	private String menuCreateUrl;	
	@Override
	public void addMenu() throws Exception {
		Menu menu = new Menu();
		List<WechatMenu> allMenus = wechatMenuService.findMenuByAppId(WeixinApplication.getAppId());
		if(allMenus.size()>0){
			try{
				WeixinPostUtils.addMenu(menuCreateUrl, accessTokenService.getAccessToken(),initMenu(allMenus, menu));			}catch (AppBizException e) {
				if (e.getCode().equals("42001")|| e.getCode().equals("40001")){
					logger.warn("accessToken过期", e);
					this.accessTokenService.invalidateToken();
					// 需要清空menu对象中已经加入的元素
					menu = new Menu();
					WeixinPostUtils.addMenu(menuCreateUrl, accessTokenService.getAccessToken(),initMenu(allMenus, menu));					return;
				}else{
					logger.error("发送菜单失败，原因："+e.getMessage(), e);
					throw e;
				}
				
				}catch(Exception e){
				logger.error("发送菜单失败，原因："+e.getMessage(), e);	
				throw e;
			}
		}else{
			logger.info("find menu items size[" + allMenus.size() + "] for 微信银行,不进行菜单更新。" );
		}
		
	}
	private Menu initMenu(List<WechatMenu> allMenus, Menu menu) {
	     logger.info("find menu items size[" + allMenus.size() + "] for 微信银行。" );
		if (CollectionUtils.isEmpty(allMenus)) return menu;
		List<WechatMenu> menuList = this.queryMenuListByParentId(allMenus,WechatMenu.MAIN_PARENT);
		
		for (int i = 0; i < menuList.size(); i++) {
			WechatMenu wechatMenu = menuList.get(i);
			ButtonItem root = new ButtonItem();
			
			root.setName(wechatMenu.getName());
			root.setKey(wechatMenu.getKey());
			root.setButtonType(wechatMenu.getType());
			root.setUrl(wechatMenu.getUrl());
		    logger.info("一级菜单："+ wechatMenu.getName());
			if(wechatMenu.getLevel().equals("0")){
				List<WechatMenu> subMenus = this.queryMenuListByParentId(allMenus, wechatMenu.getMenuId());
				for (int j = 0; j < subMenus.size(); j++) {
					WechatMenu subMenu = subMenus.get(j);
					ButtonItem item = new ButtonItem();
					
					item.setName(subMenu.getName());
					item.setKey(subMenu.getKey());
					item.setButtonType(subMenu.getType());
					item.setUrl(subMenu.getUrl());
					logger.info("      二级菜单："+ subMenu.getName());
					  if(subMenu.getLevel().equals("0")){
					        logger.info("微信银行不支持三级菜单");
					  }
					root.addSubButton(item);
				}
			}
			menu.addButton(root);
		}
		return menu;
		
	}
	private List<WechatMenu> queryMenuListByParentId(List<WechatMenu> allMenuList, Integer parentId) {
        List<WechatMenu> resultList = new ArrayList<WechatMenu>();
        for (WechatMenu Menu : allMenuList) {
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
			 logger.error("微信银行菜单加载失败",e);
		}
	}

}
