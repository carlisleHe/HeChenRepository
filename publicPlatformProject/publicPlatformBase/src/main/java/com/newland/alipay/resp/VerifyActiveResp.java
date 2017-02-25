package com.newland.alipay.resp;

import java.io.Serializable;

import com.newland.alipay.env.AlipayApplication;
import com.newland.alipay.parser.MsgType;
/**
 * 处理激活验证的响应
 * @author wot_xuzhenzhou
 *
 */
public class VerifyActiveResp extends Resp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VerifyActiveResp(){
		super.setMsgType(MsgType.event.name());
	}

	/**
	 * 生成应答消息
	 * @return
	 */
	@Override
	public String getXMLRespContent(){
		StringBuilder sb = new StringBuilder();
		sb.append("<biz_content>").append(AlipayApplication.getCuspublickey()).append("</biz_content>");
		sb.append("<success>").append("true").append("</success>");
		return sb.toString();
	}

}
