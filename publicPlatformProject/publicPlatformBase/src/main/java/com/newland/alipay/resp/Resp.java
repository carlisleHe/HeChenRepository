package com.newland.alipay.resp;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

import com.newland.alipay.parser.MsgType;
public  class Resp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private MsgType msgType;
	/**
	 *
	 */
	private String toUserName;
	/**
	 *
	 */
	private String fromUserName;
	
	/**
	 * 
	 */
	private long createTime = System.currentTimeMillis();
	

	@JSON(name = "msgType")
	public String getMsgType() {
		if(MsgType.imageText==msgType){
			return "image-text";
		}
		else if(MsgType.imageHtable==msgType){
			return "image-htable";
		}
		else{
			return msgType.name();
		}
	}
	@JSON(name = "msgType")
	public void setMsgType(String msgType) {
		if("image-text".equals(msgType)){
			this.msgType = MsgType.valueOf("imageText");
		}
		else if(msgType.equals("image-htable")){
			this.msgType = MsgType.valueOf("imageHtable");
		}
		else{
			this.msgType = MsgType.valueOf(msgType);
		}
	}
	@JSON(name = "toUserId")
	public String getToUserName() {
		return toUserName==null?"":toUserName;
	}
	@JSON(name = "toUserId")
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	@JSON(serialize=false,deserialize=false)
	public String getFromUserName() {
		return fromUserName==null?"":fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	/**
	 * 生成应答消息
	 * @return
	 */
	@JSON(serialize=false,deserialize=false)
	public String getXMLRespContent(){
		StringBuilder sb = new StringBuilder();
		sb.append("<XML>");
		sb.append("<ToUserId>").append("<![CDATA["+getToUserName()+"]]>").append("</ToUserId>");
		sb.append("<AppId>").append("<![CDATA["+getFromUserName()+"]]>").append("</AppId>");
		sb.append("<CreateTime>").append("<![CDATA["+getCreateTime()+"]]>").append("</CreateTime>");
		sb.append("<MsgType>").append("<![CDATA[ack]]>").append("</MsgType>");
		sb.append("</XML>");
		return sb.toString();
	}
	
}

