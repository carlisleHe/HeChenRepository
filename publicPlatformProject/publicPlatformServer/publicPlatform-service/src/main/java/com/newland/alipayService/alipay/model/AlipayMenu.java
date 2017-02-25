/**
 * @Title: WechatMenu.java
 * @Package package com.cib.weixin.wechat.model;
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:21:27
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.alipayService.alipay.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newland.alipay.post.model.ButtonType;

/**
 * @ClassName: WechatMenu
 * @Description: 微信菜单表		 
 * @author hongye
 * @date 2014-3-10 13:21:27
 */
@Entity
@Table(name="t_alipay_menu")
public class AlipayMenu  implements java.io.Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8549690012923072147L;

	/**
	 * 一级菜单标志
	 */
	public static final Integer MAIN_PARENT = 0;
	/**
	 * 叶子菜单标志
	 */
	public static final Integer MAIN_LEAL = 1;
	 /**
     * 菜单ID
     */
    @Id 
    @Column(name="menu_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    
    /**
     * 微信服务号
     */
    @Column(name="app_id")
    private String appId;
	 /**
	 * 菜单类型
	 * 
	 * @see ButtonType
	 */
    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private ButtonType  type;
	 /**
     * 菜单名称
     */
    @Column(name="name")
    private String name;
	 /**
     * 菜单KEY值
     */
    @Column(name="key")
    private String key;
	 /**
     * 菜单层级
     */
    @Column(name="level")
    private String level;
	 /**
     * 父菜单
     */
    @Column(name="parent")
    private Integer parent;
    
	@Transient
	private String parentName;
    /**
     * 排序字段
     */
    @Column(name="SEQ")
	private Integer seq;

	 /**
     * 创建时间
     */
    @Column(name="cre_time")
    private Date creTime;
	 /**
     *备注信息
     */
    @Column(name="remark")
    private String remark;

    public AlipayMenu() {
    }

	
    public AlipayMenu(Integer menuId, String name, String level) {
        this.menuId = menuId;
        this.name = name;
        this.level = level;
    }
   
    public Integer getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
   
    
    public ButtonType getType() {
		return type;
	}


	public void setType(ButtonType type) {
		this.type = type;
	}


	public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) { 
        this.key = key;
    }
    public String getLevel() {
        return this.level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    public Integer getParent() {
        return this.parent;
    }
    
    public void setParent(Integer parent) {
        this.parent = parent;
    }
    public Date getCreTime() {
        return this.creTime;
    }
    
    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    

	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


	public String getParentName() {
		return parentName;
	}


	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}


