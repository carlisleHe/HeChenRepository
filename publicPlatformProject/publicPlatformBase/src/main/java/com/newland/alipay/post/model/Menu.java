package com.newland.alipay.post.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.json.annotations.JSON;
/**
 * 微信菜单根类
 * @author Shiznn
 *
 */
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单项
	 */
	private List<ButtonItem> buttons;
	
	public void addButton(ButtonItem item){
		if (CollectionUtils.isEmpty(buttons)){
			buttons = new ArrayList<ButtonItem>();
		}
		buttons.add(item);
	}
	
	@JSON(name = "button")
	public List<ButtonItem> getButtons() {
		return buttons;
	}
	@JSON(name = "menu", deserialize = false)
	public void setButtons(List<ButtonItem> buttons) {
		this.buttons = buttons;
	}



}
