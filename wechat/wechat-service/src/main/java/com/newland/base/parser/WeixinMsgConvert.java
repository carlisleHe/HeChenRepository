package com.newland.base.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.base.req.EventReq;
import com.newland.base.req.ImageReq;
import com.newland.base.req.LinkReq;
import com.newland.base.req.LocationReq;
import com.newland.base.req.Req;
import com.newland.base.req.TextReq;
import com.newland.base.req.VideoReq;
import com.newland.base.req.VoiceReq;
import com.newland.base.resp.ArticleResp;
import com.newland.base.resp.Resp;
import com.newland.base.resp.TextResp;
/**
 * 微信消息转换工具类
 * @author Shizn
 *
 */
public class WeixinMsgConvert {
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	private static Logger logger = LoggerFactory.getLogger(WeixinMsgConvert.class);
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
	//	if (logger.isDebugEnabled()){
		logger.info("解析请求：\n" + req+"\n");
	//	}
		switch(req.getMsgType()){
		case event:
			return JAXBUtils.converyToJavaBean(xml, EventReq.class);
		case image:
			return JAXBUtils.converyToJavaBean(xml, ImageReq.class);
		case link:
			return JAXBUtils.converyToJavaBean(xml, LinkReq.class);
		case location:
			return JAXBUtils.converyToJavaBean(xml, LocationReq.class);
		case text:
			return JAXBUtils.converyToJavaBean(xml, TextReq.class);
		case shortvideo:
			return JAXBUtils.converyToJavaBean(xml, VideoReq.class);
		case video:
			return JAXBUtils.converyToJavaBean(xml, VideoReq.class);
		case voice:
			return JAXBUtils.converyToJavaBean(xml, VoiceReq.class);
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
		String temp = JAXBUtils.convertToXml(resp, encoding);
		logger.info("返回信息：\n" + temp);
		return temp;
	}

	/**
	 * 响应对象转xml流
	 * @param resp
	 * @param out
	 * @throws Exception
	 */
	public static void convertToXml(Resp resp,
			OutputStream out) throws Exception {
		//空串处理，无需任何的结构
		if(resp instanceof TextResp){
			String content = ((TextResp) resp).getContent();
			if(StringUtils.isBlank(content)){
				out.write("".getBytes());
				return;
			}
		}
		String temp = convertToXml(resp, DEFAULT_ENCODING);
		out.write(temp.getBytes(DEFAULT_ENCODING));
	}


	
	
	/**
	 *  XML转请求消息,默认编码UTF-8
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static ArticleResp xml2ArticleResp(String xml) throws Exception{
		return JAXBUtils.converyToJavaBean(xml, ArticleResp.class);
	}
}
