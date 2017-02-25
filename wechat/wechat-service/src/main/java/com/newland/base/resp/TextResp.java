package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;
import com.newland.base.parser.MsgType;
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TextResp(){
		this.setMsgType(MsgType.text);
	}
	
	/**
	 * 
	 */
	@XmlElement(name = "Content")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
