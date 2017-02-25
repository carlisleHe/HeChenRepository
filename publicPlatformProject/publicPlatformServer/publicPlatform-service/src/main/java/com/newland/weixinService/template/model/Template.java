package com.newland.weixinService.template.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 模板定义类
 * @author dvlp
 *
 */
@Entity
@Table (name = "t_template")
public class Template implements Serializable {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@Id
	private String id;
	/**
	 * 描述
	 */
	@Column (name = "descript")
	private String descript;
	/**
	 * 腾讯模板id
	 */
	@Column (name = "template_id")
	private String templateId;
	
	@Column (name = "top_color")
	private String topColor;
	
	@Column (name = "url")
	private String url;
	/**
	 * 创建时间
	 */
	@Column (name = "cre_time")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@Column (name = "upd_time")
	private Date updateTime;
	
	/**
	 * 模板配置
	 */
	@OneToMany(mappedBy = "template", cascade={CascadeType.ALL}, fetch = FetchType.EAGER,orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	private Set<TempConf> tempConfs;
	
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Set<TempConf> getTempConfs() {
		return tempConfs;
	}
	public void setTempConfs(Set<TempConf> tempConfs) {
		this.tempConfs = tempConfs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopColor() {
		return topColor;
	}
	public void setTopColor(String topColor) {
		this.topColor = topColor;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


}
