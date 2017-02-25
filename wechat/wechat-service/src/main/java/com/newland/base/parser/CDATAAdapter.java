package com.newland.base.parser;

import java.io.Serializable;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XML CDATA包装 器
 * @author Shizn
 *
 */
public class CDATAAdapter extends XmlAdapter<String, String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String unmarshal(String v) throws Exception {
		return v;
	}

	@Override
	public String marshal(String v) throws Exception {
		//return  "<![CDATA[" + v+ "]]>";
		return v;
	}

}
