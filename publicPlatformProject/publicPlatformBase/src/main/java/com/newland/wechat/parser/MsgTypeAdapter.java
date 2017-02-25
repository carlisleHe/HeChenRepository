package com.newland.wechat.parser;

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML转换消息类型包装类
 * @author Shiznn
 *
 */
public class MsgTypeAdapter extends XmlAdapter<String, MsgType> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public MsgType unmarshal(String v) throws Exception {
		return MsgType.valueOf(v);
	}

	@Override
	public String marshal(MsgType v) throws Exception {
		//return "<![CDATA[" + v.name()+ "]]>";
		return v.name();
	}

}
