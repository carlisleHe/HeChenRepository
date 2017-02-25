package com.newland.base.resp;

import com.newland.base.parser.MsgType;
/**
 * 用于json响应的文本消息类
 * @author dvlp
 *
 */
public class JTextResp extends Resp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JTextResp(){
		this.setMsgType(MsgType.text);
	}	/**
	 * 文本对象定义
	 */
	private Text text;

//	@JSON(name = "text")
	public Text getText() {
		return text;
	}


	public void setText(Text text) {
		this.text = text;
	}

}
