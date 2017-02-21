package com.cib.alipay.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alipay.api.internal.util.AlipaySignature;
import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.post.HttpProxyUtil;
import com.cib.alipay.proxy.AlipayNode;
import com.cib.alipay.proxy.NodeLocator;
import com.newland.wechat.proxy.service.AlipayNodeService;

public class ProxySpringServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8560262692956714743L;

	// 定位节点

	private String nodeLocatorBeanName;


	private static Logger logger = LoggerFactory.getLogger(ProxySpringServlet.class);
	/**
	 * 支付宝默认编码
	 */
	public static final String ALIPAY_ENCODING = "GBK";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			req.setCharacterEncoding(ALIPAY_ENCODING);
			//获取请求参数
			//签名数据
			String sign = req.getParameter("sign");
			//消息内容
			String bizContent = req.getParameter("biz_content");
			//签名类型
			String signType = req.getParameter("sign_type");
			//支付宝接口名称
			String service = req.getParameter("service");
			//编码
			String charset = req.getParameter("charset");
			
			if(StringUtils.isBlank(sign)||StringUtils.isBlank(bizContent)||StringUtils.isBlank(signType)||StringUtils.isBlank(service)||StringUtils.isBlank(charset)){
				resp.sendError(500, "error input!");
				return;
			}
			//验证签名
			Map<String, String> params = new HashMap<String, String>();
			params.put("sign", sign);
			params.put("biz_content", bizContent);
			params.put("sign_type", signType);
			params.put("service", service);
			params.put("charset", charset);
			boolean verify = AlipaySignature.rsaCheckV2(params, AlipayApplication.getAlipayPublicKey(),charset);
			if(!verify){
				resp.sendError(500,"verify sign error!");
				logger.error("verify error!");
				return;
			}
			//负载均衡处理请求
			//支付宝服务号的校验
			String appId = null;
			String regex =  ".*<AppId><!\\[CDATA\\[(.*)\\]\\]></AppId>.*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(bizContent);
			if (m.find()) {
				if (m.groupCount() > 0) {
					appId = m.group(1);
				}
			}
			logger.info("取得支付宝钱包服务窗appid：[" + appId + "]");
			if(!AlipayApplication.getAppId().equals(appId)){
				throw new Exception("非法的服务窗号["+appId+"]");
			}
			String openId = null;
			regex = ".*<FromUserId><!\\[CDATA\\[(.*)\\]\\]></FromUserId>.*";
			p = Pattern.compile(regex);
			m = p.matcher(bizContent);
			if (m.find()) {
				if (m.groupCount() > 0) {
					openId = m.group(1);
				}
			}
			logger.info("取得支付宝用户OpenID为：[" + openId + "]");
			String url = "";
			String response = "";
			/**
			 * 开发环境
			 */
			if("dev".equals(AlipayApplication.getRunEvnVar())){
				url = "http://localhost:8006/alipaybank/alipay";
				response = HttpProxyUtil.transmitByPool(url, bizContent,ALIPAY_ENCODING,false);
				logger.info("收到服务器[本机]返回内容:【" + response+"】");
			}
			else{
				AlipayNodeService sns = getAlipayNodeService(req);
				if(sns.getAlipayNodeList().size()==0){
					throw new Exception("系统繁忙，请稍候再试！");
				}
				AlipayNode primaryNode = null;
				NodeLocator nl = getNodeLocator(req);
				if(openId == null){
					primaryNode = sns.getAlipayNodeList().get(0);
				}
				else{
					primaryNode = nl.getPrimary(openId);
				}
				String url1 = "http://" + primaryNode.getNodeHost() + ":"+ primaryNode.getNodePort() + "/"+ primaryNode.getNodeContext();
				logger.info("取得服务器信息：" + url1);
				String localAddr = req.getLocalAddr();
				logger.debug("取得本服务器ip:" + localAddr);
				AlipayNode node = null;
				if (!sns.getFailAlipayNodeList().contains(primaryNode)) {//不在失败列表
					node = primaryNode;
				} else {
					for (Iterator<AlipayNode> i = nl.getSequence(openId); node == null&& i.hasNext();) {
						AlipayNode n = i.next();
						logger.debug("取得下一个服务器："+n.getNodeHost());
						if (!sns.getFailAlipayNodeList().contains(n)) {//不在失败列表
							node = n;
						}
					}
					if (node == null) {
						node = primaryNode;
					}
				}
				url = "http://" + node.getNodeHost() + ":"+ node.getNodePort() + "/" + node.getNodeContext();
				logger.info("最后取得服务器信息：" + url);
				response = HttpProxyUtil.transmitByPool(url, bizContent,ALIPAY_ENCODING);
				logger.info("收到服务器["+node.getNodeHost()+"]返回内容:【" + response+"】");
			}
			
			resp.reset();
			resp.setContentType("text/xml");
			resp.setCharacterEncoding(ALIPAY_ENCODING);
			resp.getOutputStream().write(response.getBytes(ALIPAY_ENCODING));
		} catch (Exception e) {
			resp.sendError(503);
			logger.error(e.getMessage(),e);
		} finally {
			resp.flushBuffer();
			resp.getOutputStream().close();
		}

	}
	


	private NodeLocator getNodeLocator(HttpServletRequest req) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(req.getSession()
						.getServletContext());
		return (NodeLocator) context.getBean(nodeLocatorBeanName);
	}

	private AlipayNodeService getAlipayNodeService(HttpServletRequest req) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(req.getSession()
						.getServletContext());
		return (AlipayNodeService) context.getBean("alipayNodeService");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		Assert.notNull(config.getInitParameter("nodeLocatorBeanName"),
				"nodeLocatorBeanName 未配置");
		this.nodeLocatorBeanName = config
				.getInitParameter("nodeLocatorBeanName");
		super.init(config);
	}

}
