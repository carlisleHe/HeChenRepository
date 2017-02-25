package com.newland.wechat.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;
import com.newland.wechat.parser.MsgType;
/**
 *	链接请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LinkReq(){
		this.setMsgType(MsgType.link);
	}
	/**
	 * 
	 */
	@XmlElement(name = "Title")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String title;
	/**
	 * 
	 */
	@XmlElement(name = "Description")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String description;

	/**
	 *ַ
	 */
	@XmlElement(name = "Url")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
