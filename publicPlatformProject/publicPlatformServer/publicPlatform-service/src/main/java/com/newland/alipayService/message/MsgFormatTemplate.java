package com.newland.alipayService.message;

import java.util.Locale;

public interface MsgFormatTemplate {
	/**
	 * 
	 * @param locale
	 * @param args
	 */

	String format(Locale locale, Object ... args);
}
