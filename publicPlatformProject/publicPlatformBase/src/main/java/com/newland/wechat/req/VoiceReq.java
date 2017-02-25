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
 * 语音请求类
 * @author Shiznn
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceReq extends Req implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VoiceReq(){
		this.setMsgType(MsgType.voice);
	}
	/**
	 * 
	 */
	@XmlElement(name = "MediaId")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String mediaId;
	/**
	 * 
	 */
	@XmlElement(name = "Format")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String format;
	
	@XmlElement(name = "Recognition")
	@XmlJavaTypeAdapter(CDATAAdapter.class)
	private String recognition;
	
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

}
