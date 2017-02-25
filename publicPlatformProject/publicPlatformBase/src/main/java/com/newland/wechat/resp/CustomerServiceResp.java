package com.newland.wechat.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newland.wechat.parser.MsgType;
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerServiceResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomerServiceResp(){
		this.setMsgType(MsgType.transfer_customer_service);
	}

}
