package com.newland.alipay.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newland.alipay.parser.MsgType;
/**
 * 文本请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "XML")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TextReq(){
		this.setMsgType(MsgType.text);
	}
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Text{
		@XmlElement(name = "content")
		private String content;
		
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	@XmlElement(name = "Text")
	private Text text;

	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
