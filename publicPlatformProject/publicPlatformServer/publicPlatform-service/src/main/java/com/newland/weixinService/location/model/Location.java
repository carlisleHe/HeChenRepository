package com.newland.weixinService.location.model;

import java.io.Serializable;

public class Location implements Serializable {

	private static final long serialVersionUID = 5664691883738581083L;

	/**
	 * 纬度，范围 -90 ~ 90 （北纬为正）
	 */
	public Double latitude;
	/**
	 * 经度，范围-180 ~ 180（东经为正）
	 */
	public Double longitude;
	
	/**
	 * 位置名称
	 */
	private String LocationName;

	public Location() {
	}

	public Location(Double lng, Double lat) {
		this.latitude = lat;
		this.longitude = lng;
	}

	/**
	 * 验证地址合法性
	 * 
	 * @return
	 */
	public boolean validate() {
		if (Double.compare(Math.abs(latitude), 90.00) <= 0
				&& Double.compare(Math.abs(longitude), 180.00) <= 0) {
			return true;
		}
		return false;
	}

	public String toString() {
		return "[" + longitude + "," + latitude + "]";
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getLocationName() {
		return LocationName;
	}

	public void setLocationName(String locationName) {
		LocationName = locationName;
	}


}
