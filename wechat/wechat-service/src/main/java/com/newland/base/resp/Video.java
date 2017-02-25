package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;
@XmlRootElement(name = "Video")
@XmlAccessorType(XmlAccessType.FIELD)
public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "MediaId")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String mediaId;
	
	@XmlElement(name = "Title")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String title;
	
	@XmlElement(name = "Description")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String description;
	
//	@JSON(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	@JSON(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
//	@JSON(name = "media_id")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
