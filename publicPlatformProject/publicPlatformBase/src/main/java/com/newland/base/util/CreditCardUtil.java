package com.newland.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 信用卡工具类
 * <p>
 * 
 * @author liw
 * @author lance
 */
public class CreditCardUtil {
	/**
	 * 取核心交易日期+发卡记账日期，得出核心入账日期 <br/>
	 * 2013-12-31 卡系统提前日切爆出bug，记账日期错误设定为2013-1-1
	 * 
	 * @param jyrq
	 *            核心交易日期
	 * @param fkjzrq
	 *            发卡记账日期，只有MMdd
	 * @return 入账日期
	 * @throws ParseException
	 */
	public static Date toFKJZRQDate(Date jyrq, String fkjzrq)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (fkjzrq == null)
			throw new ParseException("输入的fkjzrq为空！", 0);
		// 取核心交易日期的年份 + 发卡记账日期
		String hxrq = sdf.format(jyrq);
		String fkrq = hxrq.substring(0, 4) + fkjzrq;
		Date date = sdf.parse(fkrq);
		// ①核心×××3-12-31 发卡 ×××4-1-1，结果是×××3-1-1
		// ②核心×××4-1-1 发卡 ×××3-12-31，结果是×××4-12-31
		// ③核心 ×××4-1-2发卡 ×××4-1-1，结果是×××4-1-1
		// ④核心 ×××4-1-2发卡 ×××4-1-3，结果是×××4-1-3
		if (DateUtils.addMonths(date, 6).before(jyrq)) {
			// 发生了①
			date = DateUtils.addYears(date, 1);
		} else if (DateUtils.addMonths(date, -6).after(jyrq)) {
			// 发生了②
			date = DateUtils.addYears(date, -1);
		}
		// ③④
		return date;
	}

	/**
	 * 取核心交易日期+发卡传输时间，得出交易时间 <br/>
	 * 2013-12-31 卡系统提前日切爆出bug，记账日期错误设定为2013-1-1
	 * 
	 * @param jyrq
	 *            核心交易日期
	 * @param csrqsj
	 *            发卡传输时间，只有MMddhhmmss
	 * @return 入账时间
	 * @throws ParseException
	 */
	public static Date toCSRQSJDate(Date jyrq, String csrqsj)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		if (csrqsj == null)
			throw new ParseException("输入的csrqsj为空！", 0);
		Date date = toFKJZRQDate(jyrq, csrqsj.substring(0, 4));
		String fksj = sdf.format(date) + csrqsj.substring(4);
		return sdf2.parse(fksj);
	}

	/**
	 * 根据入账日期反向取卡前置适用的发卡记账日期
	 * 
	 * @param acctDate
	 *            入账日期
	 * @return 发卡记账日期MMdd
	 */
	public static String toFKJZRQStr(Date acctDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fkjzrq = sdf.format(acctDate).substring(4);
		return fkjzrq;
	}

	/**
	 * 根据入账时间反向取卡前置适用的传输时间
	 * 
	 * @param acctTime
	 *            入账时间
	 * @return 传输时间
	 */
	public static String toCSRQSJStr(Date acctTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String cssj = sdf.format(acctTime).substring(4);
		return cssj;
	}
}
