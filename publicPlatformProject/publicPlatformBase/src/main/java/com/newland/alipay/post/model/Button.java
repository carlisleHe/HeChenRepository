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
public class Button implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单项
	 */
	private List<ButtonItem> button;
	
	public void addButton(ButtonItem item){
		if (CollectionUtils.isEmpty(button)){
			button = new ArrayList<ButtonItem>();
		}
		button.add(item);
	}
	
	@JSON(name = "button")
	public List<ButtonItem> getButton() {
		return button;
	}
	@JSON(name = "menu", deserialize = false)
	public void setButton(List<ButtonItem> button) {
		this.button = button;
	}



}
