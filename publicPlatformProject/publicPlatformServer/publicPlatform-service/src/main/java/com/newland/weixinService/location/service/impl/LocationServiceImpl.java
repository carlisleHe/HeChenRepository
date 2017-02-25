package com.newland.weixinService.location.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.newland.alipayService.env.MobileBankEnviroment;
import com.newland.base.common.Const;
import com.newland.base.util.CryptoUtil;
import com.newland.wechat.post.HttpWechatUtil;
import com.newland.weixinService.location.model.ATMNode;
import com.newland.weixinService.location.model.BankNode;
import com.newland.weixinService.location.model.Location;
import com.newland.weixinService.location.model.MrchNode;
import com.newland.weixinService.location.service.LocationService;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

	private static final Logger logger = LoggerFactory
			.getLogger(LocationServiceImpl.class);

	@Resource(name = "mobileBankEnv")
	private MobileBankEnviroment mobileBankEnv;

	@Override
	public List<BankNode> queryBankNodesNearBy(Location myloc) {
		List<NameValuePair> list = initBankPayParameters(
				mobileBankEnv.SEARCH_TYPE_NODE, myloc);
		String response = "";
		try {
			response = HttpWechatUtil.submitRequestListByPool(mobileBankEnv.getMobileBankLocaitonURI(), list, HTTP.UTF_8);
		} catch (Exception e) {
			// 超时，认为是处理失败
			logger.error(e.getMessage(), e);
			return null;
		}
		if (StringUtils.isBlank(response)) {
			// 沒有查找到點
			logger.info(myloc + "位置查询网点无结果返回");
			return null;
		} else {
			List<BankNode> nodes = null;
			try {
				nodes = JSON.parseArray(response, BankNode.class);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return nodes;
		}
	}

	private List<NameValuePair> initBankPayParameters(String searchType,
			Location myloc) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair(mobileBankEnv.PARAM_SEARCHTYPE,
				searchType));
		list.add(new BasicNameValuePair(mobileBankEnv.PARAM_LANGITUDE, Double
				.toString(myloc.longitude)));
		list.add(new BasicNameValuePair(mobileBankEnv.PARAM_LATITUDE, Double
				.toString(myloc.latitude)));
		return list;
	}

	@Override
	public List<ATMNode> queryATMNodesNearBy(Location myloc) {
		List<NameValuePair> list = initBankPayParameters(
				mobileBankEnv.SEARCH_TYPE_ATM, myloc);
		String response = "";
		try {
			response = HttpWechatUtil.submitRequestListByPool(mobileBankEnv.getMobileBankLocaitonURI(), list, HTTP.UTF_8);
		} catch (Exception e) {
			// 超时，认为是处理失败
			logger.error(e.getMessage(), e);
			return null;
		}
		if (StringUtils.isBlank(response)) {
			// 沒有查找到點
			logger.info(myloc + "位置查询ATM设备无结果返回");
			return null;
		} else {
			List<ATMNode> nodes = null;
			try {
				nodes = JSON.parseArray(response, ATMNode.class);
			} catch (Exception e) {
				// 超时，认为是处理失败
				logger.error(e.getMessage(), e);
			}
			return nodes;
		}
	}

	@Override
	public List<MrchNode> queryMrchNodesNearBy(Location myloc) {
		List<NameValuePair> list = initBankPayParameters(
				mobileBankEnv.SEARCH_TYPE_MRCH, myloc);
		String response = "";
		try {
			response =HttpWechatUtil.submitRequestListByPool(mobileBankEnv.getMobileBankLocaitonURI(), list, HTTP.UTF_8);
		} catch (Exception e) {
			// 超时，认为是处理失败
			logger.error(e.getMessage(), e);
			return null;
		}
		if (StringUtils.isBlank(response)) {
			// 沒有查找到點
			logger.info(myloc + "位置查询特约商户无返回结果");
			return null;
		} else {
			List<MrchNode> nodes = null;
			try {
				nodes = JSON.parseArray(response, MrchNode.class);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return nodes;
		}
	}

	
	@Override
	public String encodeUrlParam(Location myloc, BankNode node)
			throws Exception {
		StringBuilder sb = new StringBuilder();		
		
		String result = sb.append("mylng=").append(myloc.longitude).append("&")
				.append("mylat=").append(myloc.latitude).append("&")
				.append("destlng=").append(node.getLongitude()).append("&")
				.append("destlat=").append(node.getLatitude()).append("&")
				.append("name=").append(node.getName()).append("&")
				.append("address=").append(node.getAddress()).append("&")
				.append("nodeType=BankNode&")
				.append("contact=").append(node.getContact()).toString();
		return CryptoUtil.encryptByDES(Const.ENC_DES_KEY, result, HTTP.UTF_8);
	}

	@Override
	public String encodeUrlParam(Location myloc, ATMNode node) throws Exception {
		StringBuilder sb = new StringBuilder();	
		
		String result = sb.append("mylng=").append(myloc.longitude).append("&")
				.append("mylat=").append(myloc.latitude).append("&")
				.append("destlng=").append(node.getLongitude()).append("&")
				.append("destlat=").append(node.getLatitude()).append("&")
				.append("name=").append(node.getName()).append("&")
				.append("address=").append(node.getAddress()).append("&")
				.append("status=").append(node.getStatus()).append("&")
				.append("nodeType=ATMNode&")
				.append("contact=").append(node.getContact()).toString();
		return CryptoUtil.encryptByDES(Const.ENC_DES_KEY, result, HTTP.UTF_8);
	}

	@Override
	public String encodeUrlParam(Location myloc, MrchNode node)
			throws Exception {
		StringBuilder sb = new StringBuilder();	
		
		String result = sb.append("mylng=").append(myloc.longitude).append("&")
				.append("mylat=").append(myloc.latitude).append("&")
				.append("destlng=").append(node.getLongitude()).append("&")
				.append("destlat=").append(node.getLatitude()).append("&")
				.append("name=").append(node.getName()).append("&")
				.append("address=").append(node.getAddress()).append("&")
				.append("status=").append(node.getStatus()).append("&")
				
				.append("prefInfo=").append(node.getPrefInfo()).append("&")
				.append("endDate=").append(node.getEndDate()).append("&")
				
				.append("nodeType=MrchNode&")
				.append("contact=").append(node.getContact()).toString();
		return CryptoUtil.encryptByDES(Const.ENC_DES_KEY, result, HTTP.UTF_8);
	}

}
