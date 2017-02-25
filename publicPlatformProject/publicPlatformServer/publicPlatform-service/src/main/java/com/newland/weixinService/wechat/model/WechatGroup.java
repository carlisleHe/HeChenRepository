/**
 * @Title: WechatGroup.java
 * @Package package com.cib.weixin.wechat.model;
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:20:05
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @ClassName: WechatGroup
 * @Description: 微信分组表（每日从微信平台更新）		
 * @author hongye
 * @date 2014-3-10 13:20:05
 */
@Entity
@Table(name="t_wechat_group")
public class WechatGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3015784506599248L;

	 /**
     *组ID
     */
    @Id 
    @Column(name="group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
	 /**
     * 组名称
     */
    @Column(name="name")
    private String name;
    
    /**
     * 微信服务号
     */
    @Column(name="app_id")
    private String appId;
	 /**
     * 更新日期
     */
    @Column(name="update_time")
    private Date updateTime;

    public WechatGroup() {
    }

	
    public WechatGroup(Integer groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }
   
    public Integer getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}


