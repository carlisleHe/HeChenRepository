package com.newland.base.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.EventType;
import com.newland.base.parser.EventTypeAdapter;
import com.newland.base.parser.MsgType;
/**
 * 事件请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "xml")
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
	@XmlElement(name = "Event")
	@XmlJavaTypeAdapter(EventTypeAdapter.class)
	private EventType event;
	/**
	 *ֵ
	 */
	@XmlElement(name = "EventKey")
	private String eventKey;
	/**
	 */
	@XmlElement(name = "Ticket")
	private String ticket;

	@XmlElement(name = "Latitude")
	private double latitude;
	
	@XmlElement(name = "Longitude")
	private double longitude;
	
	@XmlElement(name = "Precision")
	private double precision; 
	
	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
}
