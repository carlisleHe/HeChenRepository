package com.newland.wechat.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;
import com.newland.wechat.parser.MsgType;
/**
 * 定位请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LocationReq(){
		this.setMsgType(MsgType.location);
	}
	/**
	 *
	 */
	@XmlElement(name = "Location_X")
	private double locationX;
	/**
	 *
	 */
	@XmlElement(name = "Location_Y")
	private double locationY;
	/**
	 *
	 */
	@XmlElement(name = "Scale")
	private double scale;
	/**
	 *
	 */
	@XmlElement(name = "Label")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String label;
	
	public double getLocationX() {
		return locationX;
	}
	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}
	public double getLocationY() {
		return locationY;
	}
	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
