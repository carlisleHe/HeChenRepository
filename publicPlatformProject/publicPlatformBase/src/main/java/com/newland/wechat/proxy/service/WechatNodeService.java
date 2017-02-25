package com.newland.wechat.proxy.service;

import java.util.List;

import com.newland.wechat.proxy.WechatNode;

public interface WechatNodeService {
	
	/**
	 * 取得所有服务器列表
	 * @return
	 */
	public List<WechatNode> getWechatNodeList();
	
	/**
	 * 取得所有服务器列表
	 * @return
	 */
	public WechatNode[] getWechatNodeArray();
	
	/**
	 * 取得所有活动服务器列表
	 * @return
	 */
	//public List<WechatNode> getActiveWechatNodeList();
	
	
	/**
	 * 取得所有失败服务器列表
	 * @return
	 */
	public List<WechatNode> getFailWechatNodeList();
	
	/**
	 * 
	 */
	public void failWechatList();
	
	/**
	 * 
	 */
	public void wechatNodeList();

}
