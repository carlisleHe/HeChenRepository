package com.newland.base.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;
import com.newland.base.parser.MsgType;
/**
 * 图片请求类
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImageReq(){
		this.setMsgType(MsgType.image);
	}
	/**
	 *
	 */
	@XmlElement(name = "PicUrl")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String picUrl;
	/**
	 *
	 */
	@XmlElement(name = "MediaId")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String mediaId;
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
