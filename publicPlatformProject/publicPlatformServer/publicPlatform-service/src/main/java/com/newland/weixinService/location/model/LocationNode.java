package com.newland.weixinService.location.model;

import java.io.Serializable;

/**
 * 地理位置节点接口（网点、ATM机，特约商户等）
 * 
 * @author maple
 * 
 */
public interface LocationNode extends Serializable {

	/**
	 * 获得纬度
	 * 
	 * @return
	 */
	public Double getLatitude();

	/**
	 * 获得经度
	 * 
	 * @return
	 */
	public Double getLongitude();

	/**
	 * 获取位置名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 返回地址字符串
	 * 
	 * @return
	 */
	public String getAddress();

	/**
	 * 获取联系方式（电话等）
	 * 
	 * @return
	 */
	public String getContact();

}
