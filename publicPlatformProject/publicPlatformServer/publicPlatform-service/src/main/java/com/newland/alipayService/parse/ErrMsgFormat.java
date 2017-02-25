package com.newland.alipayService.parse;

import java.util.Locale;

/**
 * 
 * @Description: 异常信息format
 * @ClassName:ErrMsgParse 
 * @author:xuzz
 * @date:2014-6-19
 */
public interface ErrMsgFormat {
//	/**
//	 * to json
//	 * @param e
//	 * @param locale
//	 * @return
//	 * @throws MapperException
//	 */
//	public String formatToJSON(Exception e,Locale locale);
	/**
	 * to xml
	 * @param e
	 * @param locale
	 * @return
	 */
	public String formatToXML(Exception e,Locale locale) throws Exception;

}
