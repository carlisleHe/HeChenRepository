package com.cib.alipay.parser;

import com.newland.alipay.parser.AlipayMsgConvert;
import com.newland.alipay.req.Req;
import com.newland.base.parser.JAXBUtils;

public class ParserTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String xml = "<XML><Text><Content><![CDATA[?]]></Content></Text><FromAlipayUserId><![CDATA[2088102286952333]]></FromAlipayUserId><AppId><![CDATA[2016010601068750]]></AppId><MsgType><![CDATA[text]]></MsgType><CreateTime><![CDATA[1452082447533]]></CreateTime><FromUserId><![CDATA[20881039297032193307797420113333]]></FromUserId><MsgId><![CDATA[160106501407000001]]></MsgId><UserInfo><![CDATA[{\"logon_id\":\"xzz***@sina.com\",\"user_name\":\"*??\"}]]></UserInfo></XML>";
		Req msg  = AlipayMsgConvert.convertFrom(xml);
		System.err.println(msg.getMsgType());
		System.err.println(JAXBUtils.convertToXml(msg));

	}

}
