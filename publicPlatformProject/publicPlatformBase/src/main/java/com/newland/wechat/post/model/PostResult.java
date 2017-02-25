package com.newland.wechat.post.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;
/**
 * 请求微信接口返回类
 * @author Shizn
 *
 */
public class PostResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误码
	 */
	private int errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 返回的媒体类型
	 */
	private MediaType type;
	/**
	 * 返回的媒体id
	 */
	private String mediaId;
	/**
	 * 创建的时间戳
	 */
	private int createAt;
	/**
	 * 访问接口的token值
	 */
	private String accessToken;
	/**
	 * 过期时间
	 */
	private int expireIn;
	/**
	 * 临时票据
	 */
	private String apiTicket;
	
	@JSON(name = "errcode")
	public int getErrCode() {
		return errCode;
	}
	@JSON(name = "errcode", deserialize = false)
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	@JSON(name = "errmsg")
	public String getErrMsg() {
		return errMsg;
	}
	@JSON(name = "errmsg", deserialize = false)
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@JSON(name = "type")
	public MediaType getType() {
		return type;
	}
	@JSON(name = "type", deserialize = false)
	public void setType(MediaType type) {
		this.type = type;
	}

	@JSON(name = "media_id")
	public String getMediaId() {
		return mediaId;
	}
	@JSON(name = "media_id", deserialize = false)
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@JSON(name = "created_at")
	public int getCreateAt() {
		return createAt;
	}
	@JSON(name = "created_at", deserialize = false)
	public void setCreateAt(int createAt) {
		this.createAt = createAt;
	}
	public String getAccessToken() {
		return accessToken;
	}
	
	@JSON(name = "access_token", deserialize = false)
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpireIn() {
		return expireIn;
	}
	
	@JSON(name = "expires_in", deserialize = false)
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
	public String getApiTicket() {
		return apiTicket;
	}
	@JSON(name = "ticket", deserialize = false)
	public void setApiTicket(String apiTicket) {
		this.apiTicket = apiTicket;
	}

}
