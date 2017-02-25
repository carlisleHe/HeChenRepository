package com.newland.base.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.newland.base.parser.MsgType;
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MusicResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MusicResp(){
		this.setMsgType(MsgType.voice);
	}
	
	@XmlElements({@XmlElement(name = "Music")})
	private Music music;

//	@JSON(name = "music")
	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

}
