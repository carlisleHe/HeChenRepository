package com.cib.alipay.resp;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;

import com.cib.alipay.parser.MsgType;
/**
 * 文本消息回复
 * @author wot_xuzhenzhou
 *
 */
public class TextResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TextResp(){
		this.setMsgType(MsgType.text.name());
		text = new Text();
	}
	private Text text;
	@JSON(name = "text")
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public static class Text{
		
		private String content;
		@JSON(name = "content")
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		
	}
}
