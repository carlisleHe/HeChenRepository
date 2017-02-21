package com.cib.alipay.proxy.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.cib.alipay.post.HttpProxyUtil;
import com.cib.alipay.proxy.AlipayNode;
import com.cib.alipay.proxy.NodeLocator;
import com.cib.alipay.proxy.dao.AlipayNodeDao;
import com.cib.alipay.proxy.service.AlipayNodeService;



@Service("alipayNodeService") 
public class AlipayNodeServiceImpl implements AlipayNodeService , InitializingBean{
	
	
	@Resource(name = "alipayNodeDao")
	private AlipayNodeDao alipayNodeDao;
	
	@Resource(name = "ketamaNodeLocator")
	private NodeLocator nodeLocator;
	
    private static final  Logger logger = Logger.getLogger(AlipayNodeServiceImpl.class);

    public static List<AlipayNode> alipayNodeList = new ArrayList<AlipayNode>();
    
    public static List<AlipayNode> failWechatList = new Vector<AlipayNode>();
    
    private static final String HEALTH_EXAMINATION_CONTEXT ="alipaybank/hb.html";

	@Override
	public List<AlipayNode> getAlipayNodeList() {
		if(alipayNodeList.size()<1){
			alipayNodeList();
		}
		if(logger.isDebugEnabled()){
			logger.info("服务器有："+alipayNodeList.size()+"台");
			for (Iterator<AlipayNode> iterator = alipayNodeList.iterator(); iterator.hasNext();) {
				AlipayNode alipayNode = (AlipayNode) iterator.next();
				logger.info("服务IP："+alipayNode.getNodeHost());
			}
		}
		return alipayNodeList;
	}

	@Override
	public AlipayNode[] getAlipayNodeArray() {
		return (AlipayNode[]) getAlipayNodeList().toArray();
	}

	@Override
	public List<AlipayNode> getFailAlipayNodeList() {
		logger.info("服务器有："+alipayNodeList.size()+"台");
		if(logger.isDebugEnabled()){
			logger.info("失败服务器有："+failWechatList.size()+"台");
			for (Iterator<AlipayNode> iterator = failWechatList.iterator(); iterator.hasNext();) {
				AlipayNode alipayNode = (AlipayNode) iterator.next();
				logger.info("失败服务IP："+alipayNode.getNodeHost());
			}
		}
		
		return failWechatList;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("初始化alipayNodeList开始！");
		alipayNodeList();
		logger.info("初始化alipayNodeList结束！");
	}
	@Override
	public void failAlipayList(){
		failWechatList.clear();
		if(alipayNodeList.size()<1){
			alipayNodeList();
		}
		logger.info("服务器有："+alipayNodeList.size()+"台");
		for (Iterator<AlipayNode> iterator = alipayNodeList.iterator(); iterator.hasNext();) {
			AlipayNode alipayNode = (AlipayNode) iterator.next();
			if(!HttpProxyUtil.isActiveByPool("http://"+alipayNode.getNodeHost()+":"+alipayNode.getNodePort()+"/"+HEALTH_EXAMINATION_CONTEXT)){
				failWechatList.add(alipayNode);	
			}
		}
		
	}
	@Override
	public void alipayNodeList(){

		alipayNodeList.clear();
		
		alipayNodeList = alipayNodeDao.findAll();
		
		//更新分发服务器
		nodeLocator.updateLocator(alipayNodeList);
		
	}
	

}
