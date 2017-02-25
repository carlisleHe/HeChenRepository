package com.newland.base.parser;

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML解析事件类型包装类
 * @author Shizn
 *
 */
public class EventTypeAdapter extends XmlAdapter<String, EventType> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public EventType unmarshal(String v) throws Exception {
		return EventType.valueOf(v);
	}

	@Override
	public String marshal(EventType v) throws Exception {
		//return  "<![CDATA[" + v.name() + "]]>";
		return v.name();
	}

}
