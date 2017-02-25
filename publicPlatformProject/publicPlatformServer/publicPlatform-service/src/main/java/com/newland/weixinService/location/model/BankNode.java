package com.newland.weixinService.location.model;

import com.alibaba.fastjson.annotation.JSONField;


public class BankNode extends Location implements LocationNode {

	private static final long serialVersionUID = 3216063822293481633L;

	public BankNode() {
	}

	public BankNode(Double lng, Double lat) {
		super(lng, lat);
	}

	private String id;
	
	/**
	 * 电话
	 */
	private String contact;

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

	@JSONField(name = "orgid")
	public String getId() {
		return id;
	}

	@JSONField(name = "orgid")
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@JSONField(name = "orgaddress")
	public String getAddress() {
		return this.address;
	}

	@JSONField(name = "orgaddress")
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	@JSONField(name = "orgtel")
	public String getContact() {
		return this.contact;
	}

	@JSONField(name = "orgtel")
	public void setContact(String contact) {
		this.contact = contact;
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

	@JSONField(name = "orgname")
	public String getName() {
		return name;
	}

	@JSONField(name = "orgname")
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

}
