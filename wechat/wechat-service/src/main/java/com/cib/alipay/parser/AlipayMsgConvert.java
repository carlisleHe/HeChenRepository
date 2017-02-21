package com.cib.alipay.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.internal.util.AlipaySignature;
import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.req.EventReq;
import com.cib.alipay.req.ImageReq;
import com.cib.alipay.req.Req;
import com.cib.alipay.req.TextReq;
import com.cib.alipay.resp.Resp;
/**
 * 微信消息转换工具类
 * @author Shizn
 *
 */
public class AlipayMsgConvert {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	private static Logger logger = LoggerFactory.getLogger(AlipayMsgConvert.class);
	/**
	 * XML转请求消息
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static Req convertFrom(InputStream in, String encoding) throws Exception{
		BufferedInputStream bin = new BufferedInputStream(in);
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
		String temp = new String(bufs, 0, bufs.length, encoding);
		return convertFrom(temp, encoding);
	}
	
	/**
	 *  XML转请求消息
	 * @param xml
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static Req convertFrom(String xml, String encoding) throws Exception {
		logger.info("接收消息：\n[" + xml+"]\n");
		Req req = JAXBUtils.converyToJavaBean(xml, Req.class);
		logger.info("解析请求：\n" + req+"\n");
		switch(req.getMsgType()){
		case event:
			return JAXBUtils.converyToJavaBean(xml, EventReq.class);
		case image:
			return JAXBUtils.converyToJavaBean(xml, ImageReq.class);
		case text:
			return JAXBUtils.converyToJavaBean(xml, TextReq.class);
		}
		throw new Exception("");
	}

	/**
	 *  XML转请求消息,默认编码UTF-8
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Req convertFrom(String xml) throws Exception{
		return convertFrom(xml, DEFAULT_ENCODING);
	}
	/**
	 * XML转请求消息,默认编码UTF-8
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static Req convertFrom(InputStream in) throws Exception{
		return convertFrom(in, DEFAULT_ENCODING);
	}
	
	/**
	 * 响应对象转xml流
	 * @param resp
	 * @param writer
	 * @param encoding
	 * @throws Exception
	 */
	public static void convertToXml(Resp resp, PrintWriter writer, String encoding) throws Exception{
		String temp = convertToXml(resp, encoding);
		writer.write(temp);
	}
	
	/**
	 * 响应对象转xml流
	 * @param resp
	 * @param writer
	 * @throws Exception
	 */
	public static void convertToXml(Resp resp, PrintWriter writer) throws Exception{
		convertToXml(resp, writer, DEFAULT_ENCODING);
	}
	
	/**
	 * 响应对象转xml字符串
	 * @param resp
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String convertToXml(Resp resp, String encoding) throws Exception{
		//加上签名信息
		String xml = resp.getXMLRespContent();
		String content = AlipaySignature.encryptAndSign(xml, AlipayApplication.getAlipayPublicKey(), AlipayApplication.getCusPrivateKey(), encoding, false, true);
		return content;
	}

	/**
	 * 响应对象转xml流
	 * @param resp
	 * @param out
	 * @throws Exception
	 */
	public static void convertToXml(Resp resp,
			OutputStream out) throws Exception {
		//加上签名信息
		String xml = resp.getXMLRespContent();
		String content = AlipaySignature.encryptAndSign(xml, AlipayApplication.getAlipayPublicKey(), AlipayApplication.getCusPrivateKey(), "utf-8", false, true);
		out.write(content.getBytes(DEFAULT_ENCODING));
	}
}
