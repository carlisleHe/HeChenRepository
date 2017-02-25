package com.newland.wechat.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.struts2.json.annotations.JSON;

import com.newland.base.parser.CDATAAdapter;

/**
 * 音乐类
 * @author dvlp
 *
 */
@XmlRootElement(name = "Music")
@XmlAccessorType(XmlAccessType.FIELD)
public class Music implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Title")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String title;
	@XmlElement(name = "Description")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String description;
	
	@XmlElement(name = "MusicUrl")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String musicUrl;
	
	@XmlElement(name = "HQMusicUrl")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String hqMusicUrl;
	
	@XmlElement(name = "ThumbMediaId")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String thumbMedialId;
	
	@JSON(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JSON(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(name = "musicurl")
	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMedialId() {
		return thumbMedialId;
	}

	public void setThumbMedialId(String thumbMedialId) {
		this.thumbMedialId = thumbMedialId;
	}

	

}
