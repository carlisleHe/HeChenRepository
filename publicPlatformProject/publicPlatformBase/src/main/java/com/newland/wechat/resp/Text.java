package com.newland.wechat.resp;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

public class Text implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String content;

	public Text(String content) {
		this.content = content;
	}


	@JSON(name = "content")
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	

}
