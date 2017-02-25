package com.newland.base.util;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类<br>
 * 1. 提供常用的有通用性的正则表达式，带业务规则的，请勿放入，确保其独立性。<br>
 * 2. 提供Pattern的功能扩展，普通的正则匹配请直接使用Pattern。
 * 
 * @author yelm
 * @since 2011-04-14
 */
public class RegexUtil {
	/** 匹配email地址，如：011351@cib.com.cn */
	public static final String REGEX_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	/** 匹配URL地址，如：http://www.cib.com.cn:8080/index.html?login=true */
	public static final String REGEX_URL = "^\\w+://([^/:]+)(:\\d*)?([^#\\s]*)$";
	/** 匹配整数 */
	public static final String REGEX_INT = "^-?[1-9]\\d*$";
	/**匹配数字*/
	public static final String REGEX_NUMBER = "^[0-9]+$";
	/** 匹配正整数，包括0 */
	public static final String REGEX_POS_INT = "^([1-9]\\d*|0)$";
	/** 匹配负整数，包括-0 */
	public static final String REGEX_NEG_INT = "^-([1-9]\\d*|0)$";
	/** 匹配浮点数 */
	public static final String REGEX_FLOAT = "^-?([1-9]\\d*|0)(\\.\\d+)?$";
	/** 匹配正浮点数，包括0 */
	public static final String REGEX_POS_FLOAT = "^([1-9]\\d*|0)(\\.\\d+)?$";
	/** 匹配负浮点数，包括-0 */
	public static final String REGEX_NEG_FLOAT = "^-([1-9]\\d*|0)(\\.\\d+)?$";
	/** 匹配常规字符，数字、英文字母以及下划线 */
	public static final String REGEX_CHARACTERS = "^\\w+$";
	/** 匹配金额 */
	public static final String REGEX_AMOUNT = "^([1-9]\\d*|0)(\\.\\d{1,2})?$";
	/** 过滤特殊字符 */
	public static final String REGEX_SPECIAL = "^[^&=?<>]*$";
	/** 匹配订单规则 */
	public static final String REGEX_ORDER_NO = "^[0-9a-zA-Z]+$";
	/** 匹配手续费规则 */
	public static final String REGEX_FEE = "^(0\\.\\d{1,4})?$";
	/** 匹配通讯密钥 */
	public static final String REGEX_COMMKEY = "^[0-9a-zA-Z]{8,32}$";
	/**匹配XML*/
	public static final String REGEX_XML = "^[^<>'\"&]*$";

	/**
	 * 验证url是否符合urlPatterns指定的样式，使用的是完全匹配
	 * 
	 * @param url
	 *            待验证的url地址
	 * @param urlPatterns
	 *            要进行匹配的url样式，以分号隔开，如"*.jsp;*.do;*identity/*.do"
	 * @return true表示匹配成功
	 */
	public static boolean matchUrl(String url, String urlPatterns) {
		try {
			String regex;
			String[] patterns = urlPatterns.split(";");
			for (int i = 0; i < patterns.length; i++) {
				// 将配置文本转换为正则表达式，效果如下：
				// *.jsp -> .*\\.jsp$
				// */identity/*.do -> .*/identity/.*\\.do$
				// http://*.cib.com.cn/* -> http://.*\\.cib\\.com\\.cn/.*$
				regex = patterns[i].replace(".", "\\.").replace("*", ".*")
						+ "$";
				if (Pattern.matches(regex, url))
					return true;
			}
			return false;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证url是否符合patterns指定的样式，使用的是完全匹配
	 * 
	 * @param url
	 *            待验证的url地址
	 * @param patterns
	 *            要进行匹配的url样式列表
	 * @return true表示匹配成功
	 */
	public static boolean matchUrl(String url, Iterator<String> patterns) {
		try {
			String regex;
			while (patterns.hasNext()) {
				// 将配置文本转换为正则表达式，效果如下：
				// *.jsp -> .*\\.jsp$
				// */identity/*.do -> .*/identity/.*\\.do$
				// http://*.cib.com.cn/* -> http://.*\\.cib\\.com\\.cn/.*$
				regex = patterns.next().replace(".", "\\.").replace("*", ".*")
						+ "$";
				if (Pattern.matches(regex, url))
					return true;
			}
			return false;
		} catch (Exception e) {
			return false;

		}
	}

}
