package com.newland.wechat.post.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

/**
 * 动户通知消息
 * @author dvlp
 *
 */
public class NotifyMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private String touser;
	/**
	 * 模板id
	 */
	private String templateId;
	/**
	 * 点击跳转的url
	 */
	private String url;
	/**
	 * 颜色
	 */
	private String topcolor;
	/**
	 * 需要发送的数据
	 */
	private Map<Object, Object> data;
	@JSON(name = "touser")
	public String getTouser() {
		return touser;
	}
	
	public void setTouser(String touser) {
		this.touser = touser;
	}
	@JSON(name = "template_id")
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@JSON(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@JSON(name = "topcolor")
	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	@JSON(name = "data")
	public Map<Object, Object> getData() {
		return data;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

}
