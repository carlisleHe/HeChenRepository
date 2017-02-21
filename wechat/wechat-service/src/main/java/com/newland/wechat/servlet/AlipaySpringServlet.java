package com.newland.wechat.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cib.alipay.handle.AlipayReqHandle;
import com.cib.alipay.parser.AlipayMsgConvert;
import com.cib.alipay.req.Req;
import com.cib.alipay.resp.Resp;

public class AlipaySpringServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Spring 处理器服务名
	 */
	private String handleBeanName;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BufferedInputStream bin = new BufferedInputStream(req.getInputStream());
			byte[] bufs = new byte[2048];
			int len = 0;
			ByteArrayBuffer bb = new ByteArrayBuffer(10);
			while((len = bin.read(bufs)) >= 0){
				bb.append(bufs, 0, len);
			}
			if(bb.byteAt(0)==0){
				throw new RuntimeException("请求报文体数据为空！");
			}
			bufs = bb.toByteArray();
			String temp = new String(bufs, 0, bufs.length, ProxySpringServlet.ALIPAY_ENCODING);
			logger.info("节点机器收到支付宝信息：【"+temp+"】");
			Req weixinReq = AlipayMsgConvert.convertFrom(temp);
			Resp weixinResp = this.getHandle(req).handleAlipayReq(weixinReq);
			resp.setContentType("text/xml");
			resp.setCharacterEncoding(ProxySpringServlet.ALIPAY_ENCODING);
			String result = AlipayMsgConvert.convertToXml(weixinResp, ProxySpringServlet.ALIPAY_ENCODING);
			logger.info(String.format("节点机器返回支付宝的信息：【%s】", result));
			resp.getOutputStream().write(result.getBytes(ProxySpringServlet.ALIPAY_ENCODING));
		} catch (Exception e) {
			logger.error("处理消息失败，错误原因:" + e.getMessage(), e);
			resp.sendError(503);
		}finally{
			resp.flushBuffer();
			resp.getOutputStream().close();
		}
	}
	
	private AlipayReqHandle getHandle(HttpServletRequest req){
		WebApplicationContext context = WebApplicationContextUtils.
		getRequiredWebApplicationContext(req.getSession().getServletContext());
		return (AlipayReqHandle)context.getBean(handleBeanName);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		Assert.notNull(config.getInitParameter("handleBeanName"), "handleBeanName 未配置");
		this.handleBeanName = config.getInitParameter("handleBeanName");
		super.init(config);
	}

}
