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
 * 新闻内容
 * @author dvlp
 *
 */
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	@XmlElement(name = "Title")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String title;
	/**
	 * 内容简介
	 */
	@XmlElement(name = "Description")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String description;
	
	/**
	 * 图片链接
	 */
	@XmlElement(name = "PicUrl")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String picUrl;
	
	/**
	 * 新闻url
	 */
	@XmlElement(name = "Url")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String url;
	
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
	@JSON(name = "picurl")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

}
