package com.newland.weixinService.model;

/**
 * 临时票据
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
@Entity
@Table(name = "T_API_TICKET")
public class ApiTicket implements Serializable {
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column (name = "api_ticket")
	private String apiTicket;
	
	@Column (name = "expire_time")
	private Date expireTime;
	@Id
	@Column (name = "app_id")
	private String appId;
	
	public ApiTicket(){
		
	}


	public String getApiTicket() {
		return apiTicket;
	}


	public void setApiTicket(String apiTicket) {
		this.apiTicket = apiTicket;
	}


	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
