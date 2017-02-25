package com.newland.wechat.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.handle.WeixinReqHandle;
import com.newland.wechat.parser.WeixinMsgConvert;
import com.newland.wechat.req.Req;
import com.newland.wechat.resp.Resp;
import com.newland.wechat.resp.TextResp;
import com.newland.wechat.security.WeixinMessageDigest;
import com.newland.wechat.security.aes.WXBizMsgCrypt;

public class WeixinSpringServlet extends HttpServlet {

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
		try{
			String signature = req.getParameter("signature");
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			String echostr = req.getParameter("echostr");
			String token = this.getHandle(req).getToken();
			logger.debug("signature:[" + signature + "] timestamp:[" + timestamp + "] nonce:[" + nonce + "] echostr:[" + echostr + "]");
			if (StringUtils.isBlank(signature)||StringUtils.isBlank(timestamp)
					||StringUtils.isBlank(nonce)){
				resp.sendError(403);
				return ;
			}
			WeixinMessageDigest digest = WeixinMessageDigest.getInstance(token);
			boolean checked = digest.validate(signature, timestamp, nonce);
			if (checked == false){
				resp.sendError(403);
			}else{
				resp.getOutputStream().write(echostr.getBytes("UTF-8"));
			}
		}catch(Exception e){
			logger.error("请求错误", e);
			resp.sendError(503);
		}finally{
			resp.flushBuffer();
			resp.getOutputStream().close();
		}
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
			String temp = new String(bufs, 0, bufs.length, "UTF-8");
			logger.info("收到微信信息：【"+temp+"】");
			//判断是否有密文
			//验签失败，直接返回500错误
			String encrypt_type = req.getParameter("encrypt_type");
			WXBizMsgCrypt pc = null;
			try{
				if(StringUtils.isNotBlank(encrypt_type)||temp.indexOf("<Encrypt>")>0){
					String nonce = req.getParameter("nonce");
					String timestamp = req.getParameter("timestamp");
					String msgSignature = req.getParameter("msg_signature");
					String token = WeixinApplication.getToken();
					String appId = WeixinApplication.getAppId();
					String encodingaeskey = WeixinApplication.getEncodingAESKey();
					pc = new WXBizMsgCrypt(token, encodingaeskey, appId);
					temp = pc.decryptMsg(msgSignature, timestamp, nonce, temp);
					logger.info("解密后信息：【"+temp+"】");
				}
			}
			catch(Exception e){
				logger.error(e.getMessage(),e);
				logger.warn("解密失败，尝试明文处理");
			}
			
			Req weixinReq = WeixinMsgConvert.convertFrom(temp);
			Resp weixinResp = this.getHandle(req).handleWeixinReq(weixinReq);
			resp.setContentType("application/xml");
			resp.setCharacterEncoding("UTF-8");
			String result = null;
			//空串处理，无需任何的结构
			if(weixinResp instanceof TextResp){
				String content = ((TextResp) weixinResp).getContent();
				if(StringUtils.isBlank(content)){
					result = "";
				}
				else{
					result = WeixinMsgConvert.convertToXml(weixinResp, "UTF-8");
				}
			}
			else{
				result = WeixinMsgConvert.convertToXml(weixinResp, "UTF-8");
			}
			logger.info(String.format("返回微信的信息：【%s】", result));
			//密文需要，对返回报文进行加密
			try{
				if(StringUtils.isNotBlank(encrypt_type)||temp.indexOf("<Encrypt>")>0){
					StringBuffer sb = new StringBuffer();
					for(int i =0;i<6;i++){
						sb.append((int)(Math.random()*10));
					}
					result = pc.encryptMsg(result, String.valueOf(System.currentTimeMillis()), sb.toString());
					logger.info(String.format("加密后的信息：【%s】", result));
				}
			}
			catch(Exception e){
				logger.error(e.getMessage(),e);
				logger.warn("加密失败，尝试明文返回");
			}
			
			resp.getOutputStream().write(result.getBytes("UTF-8"));
		} catch (Exception e) {
			logger.error("处理消息失败，错误原因:" + e.getMessage(), e);
			resp.sendError(503);
		}finally{
			resp.flushBuffer();
			resp.getOutputStream().close();
		}
	}
	
	private WeixinReqHandle getHandle(HttpServletRequest req){
		WebApplicationContext context = WebApplicationContextUtils.
		getRequiredWebApplicationContext(req.getSession().getServletContext());
		return (WeixinReqHandle)context.getBean(handleBeanName);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		Assert.notNull(config.getInitParameter("handleBeanName"), "handleBeanName 未配置");
		this.handleBeanName = config.getInitParameter("handleBeanName");
		super.init(config);
	}

}
