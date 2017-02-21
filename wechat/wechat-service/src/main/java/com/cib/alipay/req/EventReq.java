package com.cib.alipay.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cib.alipay.parser.EventType;
import com.cib.alipay.parser.EventTypeAdapter;
import com.cib.alipay.parser.MsgType;
/**
 * 事件请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "XML")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EventReq(){
		this.setMsgType(MsgType.event);
	}

	/**
	 *
	 */
	@XmlElement(name = "EventType")
	@XmlJavaTypeAdapter(EventTypeAdapter.class)
	private EventType event;
	/**
	 *ֵ
	 */
	@XmlElement(name = "ActionParam")
	private String actionParam;
	/**
	 */
	@XmlElement(name = "AgreementId")
	private String agreementId;

	@XmlElement(name = "AccountNo")
	private double accountNo;
	
	@XmlElement(name = "UserInfo")
	private double userInfo;

	public EventType getEvent() {
		return event;
	}

	public void setEvent(EventType event) {
		this.event = event;
	}

	public String getActionParam() {
		return actionParam;
	}

	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public double getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(double accountNo) {
		this.accountNo = accountNo;
	}

	public double getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(double userInfo) {
		this.userInfo = userInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
