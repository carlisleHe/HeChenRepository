package com.newland.alipay.post.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

/**
 * 微信用户
 * @author Shizn
 *
 */
public class WeixinUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否本公众号用户
	 */
	private int subscribe;
	/**
	 * 微信号
	 */
	private String openId;
	/**
	 * 用户的昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private String sex;
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;
	/**
	 * 用户所在城市
	 */
	private String city;
	/**
	 * 用户所在省份
	 */
	private String province;
	/**
	 * 用户所在国家
	 */
	private String country;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	 */
	private String headimgurl;
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private int subscribeTime;

	public int getSubscribe() {
		return subscribe;
	}

	@JSON(name = "subscribe", deserialize = false)
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	@JSON(name = "openid", deserialize = false)
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	@JSON(name = "nickname", deserialize = false)
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}
	@JSON(name = "sex", deserialize = false)
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}
	@JSON(name = "language", deserialize = false)
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}
	@JSON(name = "city", deserialize = false)
	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}
	@JSON(name = "province", deserialize = false)
	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}
	@JSON(name = "country", deserialize = false)
	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}
	@JSON(name = "headimgurl", deserialize = false)
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getSubscribeTime() {
		return subscribeTime;
	}
	@JSON(name = "subscribe_time", deserialize = false)
	public void setSubscribeTime(int subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	
}
