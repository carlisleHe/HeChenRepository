package com.newland.wechat.post.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

public class CustList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int count;
	
	private int total;
	
	private String nextOpenid;
	
	private CustData data;

	public int getCount() {
		return count;
	}
	@JSON(name = "count", deserialize = false)
	public void setCount(int count) {
		this.count = count;
	}

	public int getTotal() {
		return total;
	}
	@JSON(name = "total", deserialize = false)
	public void setTotal(int total) {
		this.total = total;
	}

	@JSON(name = "next_openid", deserialize = false)
	public String getNextOpenid() {
		return nextOpenid;
	}
	
	@JSON(name = "next_openid", deserialize = false)
	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}

	public CustData getData() {
		return data;
	}
	@JSON(name = "data", deserialize = false)
	public void setData(CustData data) {
		this.data = data;
	}

}
