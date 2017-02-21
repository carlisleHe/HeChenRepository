package com.cib.alipay.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.cib.alipay.parser.CDATAAdapter;
import com.cib.alipay.parser.MsgType;
/**
 * 图片请求类
 */
@XmlRootElement(name = "XML")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageReq extends Req implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImageReq(){
		this.setMsgType(MsgType.image);
	}
	
	@XmlElement(name = "Image")
	public Image image;
	
	
	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Image{
		/**
		 *
		 */
		@XmlElement(name = "Format")
		@XmlJavaTypeAdapter(CDATAAdapter.class)
		private String format;
		/**
		 *
		 */
		@XmlElement(name = "MediaId")
		@XmlJavaTypeAdapter(CDATAAdapter.class)
		private String mediaId;
		
		
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
		public String getMediaId() {
			return mediaId;
		}
		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
	}
}
