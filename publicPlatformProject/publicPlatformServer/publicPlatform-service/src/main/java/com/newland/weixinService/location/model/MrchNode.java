package com.newland.weixinService.location.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class MrchNode extends Location implements LocationNode {

	private static final long serialVersionUID = 3216063822293481633L;

	public MrchNode() {
	}

	public MrchNode(Double lng, Double lat) {
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
	 * 商户类型
	 */
	private String mrchType;
	/**
	 * 电话
	 */
	private String contact;

	/**
	 * 距离
	 */
	private Double distance;

	/**
	 * 优惠信息
	 */
	private String prefInfo;
	/**
	 * 截止日期
	 */
	private String endDate;
	/**
	 * 状态
	 */
	private Integer status;

	@Override
	@JSONField(name = "custaddr")
	public String getAddress() {
		return this.address;
	}

	@JSONField(name = "custaddr")
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	@JSONField(name = "custtel")
	public String getContact() {
		return this.contact;
	}

	@JSONField(name = "custtel")
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

	@JSONField(name = "custname")
	public String getName() {
		return name;
	}

	@JSONField(name = "custname")
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

	@JSONField(name = "custtype")
	public String getMrchType() {
		return mrchType;
	}

	@JSONField(name = "custtype")
	public void setMrchType(String mrchType) {
		this.mrchType = mrchType;
	}

	@JSONField(name = "prefinfo")
	public String getPrefInfo() {
		return prefInfo;
	}

	@JSONField(name = "prefinfo")
	public void setPrefInfo(String prefInfo) {
		this.prefInfo = prefInfo;
	}

	@JSONField(name = "enddate")
	public String getEndDate() {
		return endDate;
	}

	@JSONField(name = "enddate")
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@JSONField(name = "status")
	public Integer getStatus() {
		return status;
	}

	@JSONField(name = "status")
	public void setStatus(Integer status) {
		this.status = status;
	}

}
