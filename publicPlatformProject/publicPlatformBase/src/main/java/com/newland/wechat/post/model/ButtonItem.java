package com.newland.wechat.post.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.json.annotations.JSON;
/**
 * 微信菜单项
 * @author Shiznn
 *
 */
public class ButtonItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 按钮类型 
	 */
	private ButtonType buttonType;
	/**
	 * 按钮名称
	 */
	private String name;
	/**
	 * 链接地址,如果按钮类型为view
	 */
	private String url;
	/**
	 * 按钮key，如果按钮类型为click
	 */
	private String key;
	
	private List<ButtonItem> subButtons;
	
	public void addSubButton(ButtonItem item){
		if (CollectionUtils.isEmpty(subButtons)){
			subButtons = new ArrayList<ButtonItem>();
		}
		subButtons.add(item);
	}

	@JSON(name = "type")
	public ButtonType getButtonType() {
		return buttonType;
	}

	@JSON(name = "type", deserialize = false)
	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}

	@JSON(name = "name")
	public String getName() {
		return name;
	}
	@JSON(name = "name", deserialize = false)
	public void setName(String name) {
		this.name = name;
	}

	@JSON(name = "url")
	public String getUrl() {
		return url;
	}
	@JSON(name = "url", deserialize = false)
	public void setUrl(String url) {
		this.url = url;
	}

	@JSON(name = "key")
	public String getKey() {
		return key;
	}
	@JSON(name = "key", deserialize = false)
	public void setKey(String key) {
		this.key = key;
	}
	@JSON(name = "sub_button")
	public List<ButtonItem> getSubButtons() {
		return subButtons;
	}
	@JSON(name = "sub_button", deserialize = false)
	public void setSubButtons(List<ButtonItem> subButtons) {
		this.subButtons = subButtons;
	}

}
