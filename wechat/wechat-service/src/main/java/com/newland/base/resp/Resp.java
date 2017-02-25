package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newland.base.parser.CDATAAdapter;
import com.newland.base.parser.MsgType;
import com.newland.base.parser.MsgTypeAdapter;
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@XmlElement(name = "MsgType")
	@XmlJavaTypeAdapter(MsgTypeAdapter.class)
	private MsgType msgType;
	/**
	 *
	 */
	@XmlElement(name = "ToUserName")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String toUserName;
	/**
	 *
	 */
	@XmlElement(name = "FromUserName")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String fromUserName;
	
	/**
	 * 
	 */
	@XmlElement(name = "CreateTime")
	private long createTime = System.currentTimeMillis();

//	@JSON(name = "msgtype")
	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}
//	@JSON(name = "touser")
	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
//	@JSON(deserialize = false, serialize = false)
	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

//	@JSON(deserialize = false, serialize = false)
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
