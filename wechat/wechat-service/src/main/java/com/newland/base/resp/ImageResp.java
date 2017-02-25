package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.newland.base.parser.MsgType;

/**
 * 图片类响应
 * @author dvlp
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ImageResp(){
		this.setMsgType(MsgType.image);
	}
	/**
	 * 图片对象
	 */
	@XmlElements({@XmlElement(name = "Image")})
	private Image image;

//	@JSON(name = "image")
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
