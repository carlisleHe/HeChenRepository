package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;

/**
 * 图片类
 * @author dvlp
 *
 */
@XmlRootElement(name = "Image")
@XmlAccessorType(XmlAccessType.FIELD)
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "MediaId")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String mediaId;

//	@JSON(name = "media_id")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	

}
