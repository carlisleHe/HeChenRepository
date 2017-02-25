package com.newland.weixinService.location.service;

import java.util.List;

import com.newland.weixinService.location.model.ATMNode;
import com.newland.weixinService.location.model.BankNode;
import com.newland.weixinService.location.model.Location;
import com.newland.weixinService.location.model.MrchNode;

public interface LocationService {
	/**
	 * 根据当前坐标查找附近的银行网点<br/>
	 * 数据来源：手机银行<br/>
	 * 目前查询经纬度差别在0.1度范围内的网点
	 * 
	 * @param myloc
	 *            当前坐标位置
	 * @return
	 */
	public List<BankNode> queryBankNodesNearBy(Location myloc);

	/**
	 * 根据当前坐标查找附近的银行自助机具<br/>
	 * 数据来源：手机银行<br/>
	 * 目前查询经纬度差别在0.1度范围内的网点
	 * 
	 * @param myloc
	 *            当前坐标位置
	 * @return
	 */
	public List<ATMNode> queryATMNodesNearBy(Location myloc);

	/**
	 * 根据当前坐标查找附近的银行特约商户<br/>
	 * 数据来源：手机银行<br/>
	 * 目前查询经纬度差别在0.1度范围内的商户
	 * 
	 * @param myloc
	 *            当前坐标位置
	 * @return
	 */
	public List<MrchNode> queryMrchNodesNearBy(Location myloc);

	/**
	 * 对银行网点位置及点的链接参数进行Base64加密（用于展示给客户）
	 * 
	 * @param myloc
	 * @param node
	 * @return
	 */
	public String encodeUrlParam(Location myloc, BankNode node) throws Exception;

	/**
	 * 对ATM网点位置及点的链接参数进行Base64加密（用于展示给客户）
	 * 
	 * @param myloc
	 * @param node
	 * @return
	 */
	public String encodeUrlParam(Location myloc, ATMNode node) throws Exception;
	
	/**
	 * 对商户网点位置及点的链接参数进行Base64加密（用于展示给客户）
	 * 
	 * @param myloc
	 * @param node
	 * @return
	 */
	public String encodeUrlParam(Location myloc, MrchNode node) throws Exception;
}
