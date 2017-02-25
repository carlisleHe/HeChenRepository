package com.newland.wechat.proxy.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.newland.base.post.HttpProxyUtil;
import com.newland.wechat.proxy.NodeLocator;
import com.newland.wechat.proxy.WechatNode;
import com.newland.wechat.proxy.dao.WechatNodeDao;
import com.newland.wechat.proxy.service.WechatNodeService;



@Service("wechatNodeService") 
public class WechatNodeServiceImpl implements WechatNodeService , InitializingBean{
	
	
	@Resource(name = "wechatNodeDao")
	private WechatNodeDao wechatNodeDao;
	
	@Resource(name = "ketamaNodeLocator")
	private NodeLocator nodeLocator;
	
    private static final  Logger logger = Logger.getLogger(WechatNodeServiceImpl.class);

    public static List<WechatNode> wechatNodeList = new ArrayList<WechatNode>();
    
    public static List<WechatNode> failWechatList = new Vector<WechatNode>();
    
    private static final String HEALTH_EXAMINATION_CONTEXT ="weibank/hb.html";

	@Override
	public List<WechatNode> getWechatNodeList() {
		if(wechatNodeList.size()<1){
			wechatNodeList();
		}
		if(logger.isDebugEnabled()){
			logger.info("服务器有："+wechatNodeList.size()+"台");
			for (Iterator<WechatNode> iterator = wechatNodeList.iterator(); iterator.hasNext();) {
				WechatNode wechatNode = (WechatNode) iterator.next();
				logger.info("服务IP："+wechatNode.getNodeHost());
			}
		}
		return wechatNodeList;
	}

	@Override
	public WechatNode[] getWechatNodeArray() {
		return (WechatNode[]) getWechatNodeList().toArray();
	}

	@Override
	public List<WechatNode> getFailWechatNodeList() {
		logger.info("服务器有："+wechatNodeList.size()+"台");
		if(logger.isDebugEnabled()){
			logger.info("失败服务器有："+failWechatList.size()+"台");
			for (Iterator<WechatNode> iterator = failWechatList.iterator(); iterator.hasNext();) {
				WechatNode wechatNode = (WechatNode) iterator.next();
				logger.info("失败服务IP："+wechatNode.getNodeHost());
			}
		}
		
		return failWechatList;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化wechatNodeList开始！");
		wechatNodeList();
		logger.info("初始化wechatNodeList结束！");
	}
	@Override
	public void failWechatList(){
		failWechatList.clear();
		if(wechatNodeList.size()<1){
			wechatNodeList();
		}
		logger.info("服务器有："+wechatNodeList.size()+"台");
		for (Iterator<WechatNode> iterator = wechatNodeList.iterator(); iterator.hasNext();) {
			WechatNode wechatNode = (WechatNode) iterator.next();
			if(!HttpProxyUtil.isActiveByPool("http://"+wechatNode.getNodeHost()+":"+wechatNode.getNodePort()+"/"+HEALTH_EXAMINATION_CONTEXT)){
				failWechatList.add(wechatNode);	
			}
		}
		
	}
	@Override
	public void wechatNodeList(){

		wechatNodeList.clear();
		
		wechatNodeList = wechatNodeDao.findAll();
		
		//更新分发服务器
		nodeLocator.updateLocator(wechatNodeList);
		
	}
	
	public static void main(String[] args) {
		
		
		WechatNodeServiceImpl ws = new WechatNodeServiceImpl();
		
		System.out.println(ws.getWechatNodeList().size());
		
		System.out.println(ws.getWechatNodeList().size());
		
		//System.out.println(ws.getActiveWechatNodeList().size());
		
		//System.out.println(ws.getActiveWechatNodeList().size());
		
		//System.out.println(ws.getActiveWechatNodeList().size());
		
	}
	
	

}
