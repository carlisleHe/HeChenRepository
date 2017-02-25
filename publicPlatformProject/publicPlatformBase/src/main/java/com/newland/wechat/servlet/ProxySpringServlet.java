package com.newland.wechat.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newland.base.post.HttpProxyUtil;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.proxy.NodeLocator;
import com.newland.wechat.proxy.WechatNode;
import com.newland.wechat.proxy.service.WechatNodeService;
import com.newland.wechat.security.aes.WXBizMsgCrypt;

public class ProxySpringServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8560262692956714743L;

	// 定位节点

	private String nodeLocatorBeanName;

	private static Logger logger = LoggerFactory
			.getLogger(ProxySpringServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/weixin").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BufferedInputStream bin = new BufferedInputStream(
					req.getInputStream());
			byte[] bufs = new byte[2048];
			int len = 0;
			ByteArrayBuffer bb = new ByteArrayBuffer(10);
			while ((len = bin.read(bufs)) >= 0) {
				bb.append(bufs, 0, len);
			}
			if (bb.byteAt(0) == 0) {
				throw new RuntimeException("请求报文体数据为空！");
			}
			bufs = bb.toByteArray();
			String temp = new String(bufs, 0, bufs.length, "UTF-8");
			logger.info("收到微信信息：【" + temp + "】");
			// 有密文，对密文进行解密
			// 验签失败，直接返回500错误
			String encrypt_type = req.getParameter("encrypt_type");
			WXBizMsgCrypt pc = null;
			try {
				if (StringUtils.isNotBlank(encrypt_type)
						|| temp.indexOf("<Encrypt>") > 0) {
					String nonce = req.getParameter("nonce");
					String timestamp = req.getParameter("timestamp");
					String msgSignature = req.getParameter("msg_signature");
					String token = WeixinApplication.getToken();
					String appId = WeixinApplication.getAppId();
					String encodingaeskey = WeixinApplication
							.getEncodingAESKey();
					pc = new WXBizMsgCrypt(token, encodingaeskey, appId);
					temp = pc.decryptMsg(msgSignature, timestamp, nonce, temp);
					logger.info("解密后信息：【" + temp + "】");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.warn("解密失败，尝试明文处理");
			}

			// 增加微信公众号原始ID验证
			String wechatId = null;
			String regex = ".*<ToUserName><!\\[CDATA\\[(.*)\\]\\]></ToUserName>.*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(temp);
			if (m.find()) {
				if (m.groupCount() > 0) {
					wechatId = m.group(1);
				}
			}
			logger.info("取得微信公众号原始ID：[" + wechatId + "]");
			if (!WeixinApplication.getWechatId().equals(wechatId)) {
				throw new Exception("非法的微信公众号[" + wechatId + "]");
			}

			String OpenID = null;
			regex = ".*<FromUserName><!\\[CDATA\\[(.*)\\]\\]></FromUserName>.*";
			p = Pattern.compile(regex);
			m = p.matcher(temp);
			if (m.find()) {
				if (m.groupCount() > 0) {
					OpenID = m.group(1);
				}
			}
			logger.info("取得微信用户OpenID为：[" + OpenID + "]");

			String url;
			String result;
			/**
			 * 开发环境
			 */
			if ("dev".equals(WeixinApplication.getRunEvnVar())) {
				url = "http://localhost:8006/publicPlatform/weixin";
				result = HttpProxyUtil
						.transmitByPool(url, temp, "UTF-8", false);
				logger.info("收到服务器[本机]返回内容:【" + result + "】");
			} else {

				NodeLocator nl = getNodeLocator(req);

				WechatNode primaryNode = nl.getPrimary(OpenID);

				String url1 = "http://" + primaryNode.getNodeHost() + ":"
						+ primaryNode.getNodePort() + "/"
						+ primaryNode.getNodeContext();
				logger.info("取得服务器信息：" + url1);
				String localAddr = req.getLocalAddr();
				logger.debug("取得本服务器ip:" + localAddr);

				/*
				 * if (req.getLocalAddr().equals(primaryNode.getNodeHost())) {
				 * req.getRequestDispatcher("/weixin").forward(req, resp); }
				 */

				WechatNode node = null;

				WechatNodeService sns = getWechatNodeService(req);

				if (!sns.getFailWechatNodeList().contains(primaryNode)) {// 不在失败列表
					node = primaryNode;
				} else {
					for (Iterator<WechatNode> i = nl.getSequence(OpenID); node == null
							&& i.hasNext();) {
						WechatNode n = i.next();
						logger.debug("取得下一个服务器：" + n.getNodeHost());
						if (!sns.getFailWechatNodeList().contains(n)) {// 不在失败列表
							node = n;
						}
					}
					if (node == null) {
						node = primaryNode;
					}
				}
				/*
				 * if (req.getLocalAddr().equals(node.getNodeHost())) {
				 * req.getRequestDispatcher("/weixin").forward(req, resp); }
				 */
				url = "http://" + node.getNodeHost() + ":" + node.getNodePort()
						+ "/" + node.getNodeContext();
				logger.info("最后取得服务器信息：" + url);

				result = HttpProxyUtil.transmitByPool(url, temp, "UTF-8");
				logger.info("收到服务器[" + node.getNodeHost() + "]返回内容:【" + result
						+ "】");
			}
			// 有密文对密文进行加密返回
			try {
				if (StringUtils.isNotBlank(encrypt_type)
						|| temp.indexOf("<Encrypt>") > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < 6; i++) {
						sb.append((int) (Math.random() * 10));
					}
					result = pc.encryptMsg(result,
							String.valueOf(System.currentTimeMillis()),
							sb.toString());
					logger.info("加密后信息：【" + result + "】");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				logger.warn("加密失败，尝试明文返回");
			}
			resp.getOutputStream().write(result.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("请求错误", e);
			resp.sendError(503);
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

	private WechatNodeService getWechatNodeService(HttpServletRequest req) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(req.getSession()
						.getServletContext());
		return (WechatNodeService) context.getBean("wechatNodeService");
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
