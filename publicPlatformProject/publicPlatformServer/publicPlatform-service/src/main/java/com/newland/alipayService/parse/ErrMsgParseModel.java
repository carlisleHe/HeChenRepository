package com.newland.alipayService.parse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.newland.base.parser.CDATAAdapter;

/**
 * 
 * @Description: 异常信息转换模板（json、xml同适用）
 * @ClassName:ErrMsgParseModel 
 * @author:xuzz
 * @date:2014-6-18
 */
@XmlRootElement(name = "err")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrMsgParseModel {
	/**
	 * 异常码
	 */
	@XmlElement(name = "err_code")
	private String errCode;
	@XmlElement(name = "err_msg")
	@XmlJavaTypeAdapter(value=CDATAAdapter.class)
	private String errMsg;
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
