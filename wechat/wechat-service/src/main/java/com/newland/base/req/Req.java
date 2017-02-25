package com.newland.base.req;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newland.base.parser.CDATAAdapter;
import com.newland.base.parser.MsgType;
import com.newland.base.parser.MsgTypeAdapter;
/**
 *
 * @author Shiznn
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Req implements Serializable {

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
	@XmlElement(name = "MsgId")
	private Long msgId;
	/**
	 * 
	 */
	@XmlElement(name = "CreateTime")
	private long createTime;
	
	
	/**
	 * 在处理类中附加的属性，方便后续处理。键名规则：处理类名+下划线+自定义名称。
	 */
	private Map<String,Object> attributes=new HashMap<String,Object>();
	


	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void addAttribute(String key,Object value) {
		attributes.put(key, value);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
