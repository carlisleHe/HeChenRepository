package com.newland.alipayService.template.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 模板配置类
 * @author dvlp
 *
 */
@Entity
@Table(name = "t_alipay_temp_conf")
public class TempConf implements Serializable {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 标题
	 */
	public static final String KEYWORD_TITLE = "title";
	/**
	 * 交易时间
	 */
	public static final String KEYWORD_TIME = "time";
	/**
	 * 交易类型
	 */
	public static final String KEYWORD_TYPE = "type";
	/**
	 * 交易金额
	 */
	public static final String KEYWORD_NUMBER = "number";
	/**
	 * 余额
	 */
	public static final String KEYWORD_BALANCE = "balance";
	/**
	 * 备注
	 */
	public static final String KEYWORD_REMARK = "remark";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column (name = "temp_conf_id")
	private Integer tempConfId;
	
	/**
	 * 关键字
	 */
	@Column (name = "key_word")
	private String keyword;
	
	/**
	 * 描述
	 */
	@Column (name = "descript")
	private String descript;
	
	/**
	 * 内容
	 */
	@Column (name = "content")
	private String content;
	
	@Column (name = "color")
	private String color;
	
	/**
	 * 是否有特殊模板
	 */
	@Column (name = "addition")
	private String addtion = "0";
	
	/**
	 * 配置类型 0-腾讯模板属性 1-内部属性
	 */
	@Column (name = "conf_type")
	private String confType = "0";
	
	/**
	 * 更新日期
	 */
	@Column (name = "upd_time")
	private Date updateTime;
	
	
	/**
	 * 模板
	 */
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    @NotFound(action=NotFoundAction.IGNORE)
	private Template template;
	
	
	@OneToMany(mappedBy = "tempConf", cascade={CascadeType.ALL}, fetch = FetchType.EAGER,orphanRemoval=true)
	@Fetch(FetchMode.SUBSELECT)
	private Set<TempConfExt> exts;
	
	

	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public Integer getTempConfId() {
		return tempConfId;
	}
	public void setTempConfId(Integer tempConfId) {
		this.tempConfId = tempConfId;
	}
	public Set<TempConfExt> getExts() {
		return exts;
	}
	public void setExts(Set<TempConfExt> exts) {
		this.exts = exts;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getAddtion() {
		return addtion;
	}
	public void setAddtion(String addtion) {
		this.addtion = addtion;
	}
	public String getConfType() {
		return confType;
	}
	public void setConfType(String confType) {
		this.confType = confType;
	}


}
