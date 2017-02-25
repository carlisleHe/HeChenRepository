package com.newland.weixinService.location.model;

import com.alibaba.fastjson.annotation.JSONField;


public class ATMNode extends Location implements LocationNode {

	private static final long serialVersionUID = 3216063822293481633L;

	public ATMNode() {
	}

	public ATMNode(Double lng, Double lat) {
		super(lng, lat);
	}


	/**
	 * 地点名称
	 */
	private String name;
	/**
	 * 地址
	 */
	private String address;

	/**
	 * 距离
	 */
	private Double distance;

	/**
	 * 状态
	 */
	private Integer status;
	
	
	@Override
	@JSONField(name = "atmaddress")
	public String getAddress() {
		return this.address;
	}

	@JSONField(name = "atmaddress")
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	@JSONField(serialize = false, deserialize = false)
	public String getContact() {
		return "";
	}

	@Override
	@JSONField(name = "latitude")
	public Double getLatitude() {
		return this.latitude;
	}

	@Override
	@JSONField(name = "longitude")
	public Double getLongitude() {
		return this.longitude;
	}

	@JSONField(name = "atmname")
	public String getName() {
		return name;
		
	}

	@JSONField(name = "atmname")
	public void setName(String name) {
		this.name = name;
	}

	@JSONField(name = "distance")
	public Double getDistance() {
		return distance;
	}

	@JSONField(name = "distance")
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@JSONField(name = "atmstatus" )
	public Integer getStatus() {
		return status;
	}
	@JSONField(name = "atmstatus")
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
