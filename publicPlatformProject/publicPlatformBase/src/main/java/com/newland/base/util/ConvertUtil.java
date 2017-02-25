package com.newland.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 数据类型转换工具类
 * </p>
 * 
 * 不论是HTTP OpenAPI或Socket交易，输入参数以String为主。<br>
 * ConvertUtil为各类参数提供了转换功能，以减少随处可见的try..catch。<br>
 * 其数据转换带有一定的项目风格，例如，你有可能全面使用"1"/"0"来表示boolean。
 * 
 * @author yelm
 * @since 2011-04-12
 */
public class ConvertUtil {
	/** 十六进制组 */
	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * String转换为boolean，输入不区分大小写
	 * 
	 * <pre>
	 * ConvertUtil.stringToBool("true") = true
	 * ConvertUtil.stringToBool("false") = true
	 * ConvertUtil.stringToBool("1") = true
	 * ConvertUtil.stringToBool("0") = false
	 * ConvertUtil.stringToBool(null) = false
	 * </pre>
	 */
	public static boolean stringToBool(String str) {
		if ("1".equals(str))
			return true;
		else if ("0".equals(str))
			return false;
		else
			return Boolean.parseBoolean(str);
	}

	/**
	 * boolean转换为String<br>
	 * true转为"1"，false转为"0"
	 */
	public static String boolToString(boolean bool) {
		if (bool)
			return "1";
		else
			return "0";
	}

	/**
	 * 将单个字符转换为16进制数字
	 * 
	 * @param c 等待转换的字符，必需为0-9 a-f A-F，不能有数字符号
	 * @return 16进制数字
	 */
	public static int charToHex(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'f') {
			return c - 'a' + 0xa;
		} else if ('A' <= c && c <= 'F') {
			return c - 'A' + 0xa;
		} else {
			throw new IllegalArgumentException("不符合格式的16进制字符: " + c);
		}
	}

	/**
	 * 将byte数组转换为16进制格式的字符串
	 * 
	 * @param bytes 待转换数组
	 * @return 16进制格式的字符串
	 */
	public static String bytesToHexStr(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexChar[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 将一个16进制格式的字符串转换为一个byte数组，它的长度必需为偶数，A-F部分大小不敏感。
	 * 
	 * @param s 待转换的字符串，必需符合16进制格式，不含正负号。
	 * @return 字节数组
	 */
	public static byte[] hexStrToBytes(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException("待转换的字符串长度必需为偶数。");
		}
		byte[] b = new byte[stringLength / 2];

		for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
			int high = charToHex(s.charAt(i));
			int low = charToHex(s.charAt(i + 1));
			b[j] = (byte) ((high << 4) | low);
		}
		return b;
	}

	/**
	 * 日期类型转换为字符串，不考虑时区
	 * 
	 * @param date 日期对象
	 * @param format 格式化，例如yyyyMMdd
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 字符串转换为日期，不考虑时区
	 * 
	 * @param dateStr 日期字符串
	 * @param format 格式化，例如yyyyMMdd
	 */
	public static Date stringToDate(String dateStr, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}

	/**
	 * 金额对齐
	 * <p>
	 * 根据核心规范，金额一率精度为小数点后两位，四舍五入
	 * <p>
	 * 请在确认金额满足格式后传入，该方法并不校验金额是否符合规范 若为3位，则自动将被四舍五入
	 * <p>
	 * 
	 * @author lance
	 * @param amt
	 * @return
	 */
	public static BigDecimal toAmtScale(BigDecimal amt) {
		return amt.setScale(2, RoundingMode.HALF_UP);
	}
	/**
	 * 去掉交易信息里9995(9995)类似这种字段内容
	 * 长度要求为1-5位且为数字
	 * @param msg
	 * @return
	 */
	public static String convertTxnMsg(String msg){
		String regex = "([0-9]{1,5})\\(([0-9]{1,5})\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(msg);
		if(matcher.find()){
			String msg1 = matcher.group(1);
			String msg2 = matcher.group(2);
			if(msg2.endsWith(msg1)){
				return msg.substring((msg1+msg2).length()+2);
			}
		}
		return msg;
		
	}
}
