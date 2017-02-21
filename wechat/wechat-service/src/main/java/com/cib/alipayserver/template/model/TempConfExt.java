package com.cib.alipayserver.template.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
@Entity
@Table (name = "t_alipay_temp_conf_ext")
public class TempConfExt implements Serializable {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column (name = "temp_ext_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tempConfExtId;
	
	@Column (name = "key_word")
	private String keyword;
	
	@Column (name = "content")
	private String content;
	
	@Column (name = "upd_time")
	private Date updateTime;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "temp_conf_id")
    @NotFound(action=NotFoundAction.IGNORE)
	private TempConf tempConf;

	public Integer getTempConfExtId() {
		return tempConfExtId;
	}

	public void setTempConfExtId(Integer tempConfExtId) {
		this.tempConfExtId = tempConfExtId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public TempConf getTempConf() {
		return tempConf;
	}

	public void setTempConf(TempConf tempConf) {
		this.tempConf = tempConf;
	}
	

}
