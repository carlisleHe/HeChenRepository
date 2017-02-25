package com.newland.alipay.post.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String id;
	@JSON(name = "name")
	public String getName() {
		return name;
	}
	@JSON(name = "name", deserialize = false)
	public void setName(String name) {
		this.name = name;
	}
	@JSON(name = "id")
	public String getId() {
		return id;
	}
	@JSON(name = "id", deserialize = false)
	public void setId(String id) {
		this.id = id;
	}

}
