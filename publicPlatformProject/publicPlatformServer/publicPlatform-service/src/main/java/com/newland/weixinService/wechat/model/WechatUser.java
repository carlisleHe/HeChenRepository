/**
 * @Title: WechatUser.java
 * @Package package com.cib.weixin.wechat.model;
 * @Description: TODO
 * @author hongye
 * @date 2014-3-10 13:22:22
 * @version V1.0
 * 
 * Copyright (c) 2010-2013 NewlandComputer All Rights Reserved.
 */
package com.newland.weixinService.wechat.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @ClassName: WechatUser
 * @Description: 微信用户表（每日从微信平台更新）		 
 * @author hongye
 * @date 2014-3-10 13:22:22
 */
@Entity
@Table(name="t_wechat_user")
public class WechatUser  implements java.io.Serializable {
	
	 /**
     *用户ID
     */
    @Id 
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

	 /**
     * 用户openId
     */
    @Column(name="open_id")
    private String openId;
	 /**
     * 用户昵称
     */
    @Column(name="nickname")
    private String nickname;
    
    /**
     * 微信服务号
     */
    @Column(name="app_id")
    private String appId;
	 /**
     * 用户所属组ID
     */
    @Column(name="group_id")
    private Integer groupId;
	 /**
     * 性别
     */
    @Column(name="sex")
    private String sex;
	 /**
     * 用户所在城市
     */
    @Column(name="city")
    private String city;
	 /**
     * 用户所处省份
     */
    @Column(name="province")
    private String province;
	 /**
     * 用户所处国家
     */
    @Column(name="country")
    private String country;
	 /**
     * 用户语言
     */
    @Column(name="language")
    private String language;
	 /**
     * 用户头像URL
     */
    @Column(name="headimgurl")
    private String headimgurl;
	 /**
     * 关注标志
     */
    @Column(name="subscribe")
    private String subscribe;
	 /**
     * 关注日期
     */
  //  @Temporal(TemporalType.DATE)
    @Column(name="subscribe_date")
    private Date subscribeDate;
	 /**
     * 关注时间
     */
   // @Temporal(TemporalType.DATE)
    @Column(name="subscribe_time")
    private Date subscribeTime;
	 /**
     * 访问令牌
     */
    @Column(name="access_token")
    private String accessToken;
	 /**
     * 令牌过期时间
     */
    @Column(name="expiretime")
    private Date expiretime;

    public WechatUser() {
    }

   
    public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public String getOpenId() {
        return this.openId;
    }
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Integer getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getHeadimgurl() {
        return this.headimgurl;
    }
    
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
    public String getSubscribe() {
        return this.subscribe;
    }
    
    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
    public Date getSubscribeDate() {
        return this.subscribeDate;
    }
    
    public void setSubscribeDate(Date subscribeDate) {
        this.subscribeDate = subscribeDate;
    }
    public Date getSubscribeTime() {
        return this.subscribeTime;
    }
    
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Date getExpiretime() {
        return this.expiretime;
    }
    
    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
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
}


