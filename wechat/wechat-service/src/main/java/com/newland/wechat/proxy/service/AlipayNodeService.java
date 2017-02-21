package com.newland.wechat.proxy.service;

import java.util.List;

import com.cib.alipay.proxy.AlipayNode;

public interface AlipayNodeService {
	
	/**
	 * 取得所有服务器列表
	 * @return
	 */
	public List<AlipayNode> getAlipayNodeList();
	
	/**
	 * 取得所有服务器列表
	 * @return
	 */
	public AlipayNode[] getAlipayNodeArray();
	
	
	/**
	 * 取得所有失败服务器列表
	 * @return
	 */
	public List<AlipayNode> getFailAlipayNodeList();
	
	/**
	 * 
	 */
	public void failAlipayList();
	
	/**
	 * 
	 */
	public void alipayNodeList();

}
