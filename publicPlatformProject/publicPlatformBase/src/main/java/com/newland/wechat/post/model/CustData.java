package com.newland.wechat.post.model;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class CustData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> openid;

	public List<String> getOpenid() {
		return openid;
	}
	@JSON(name = "openid", deserialize = false)
	public void setOpenid(List<String> openid) {
		this.openid = openid;
	}
	
	

}
