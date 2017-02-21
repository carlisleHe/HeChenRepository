package com.newland.wechatServer.util;

import java.util.ArrayList;
import java.util.List;

import com.cib.alipayserver.common.AppExCode;
import com.intensoft.exception.AppRTException;

/**
 * 货币代码工具类<br>
 * <p>
 * 备注：<br>
 * <b>转换表为什么不继续使用数据库表存储？</b><br>
 * 目前只需要货币代码转换。
 * 且经验表明，增加一个货币种类的支持，往往涉及多套系统同时改造，并非插入一条数据即可解决。
 * 另，映射关系非常稳定，即使使用数据库存储，从效率方面考虑，也是在启动时一次性加载到内存后使用。
 * 
 * @author yelm
 * @since 2011-03-11
 */
public class CurrencyUtil {
	/** ISO货币代码列表 */
	private static List<String> isoCode = new ArrayList<String>();
	/** 兴业货币代码列表 */
	private static List<String> cibCode =  new ArrayList<String>();
	
	// 类初始化时建立货币代码列表，注意顺序需要一一对应。
	// 双列表为支持双向查找，仅限核心系统支持的范围
	static {
		isoCode.add("CNY");	cibCode.add("01");	//人民币
		isoCode.add("EUR"); cibCode.add("11");	// 欧元
		isoCode.add("GBP"); cibCode.add("12");	// 英镑
		isoCode.add("HKD"); cibCode.add("13");	// 港币
		isoCode.add("USD"); cibCode.add("14");	// 美元		
		isoCode.add("CHF"); cibCode.add("15");	// 瑞士法郎
		isoCode.add("SEK"); cibCode.add("16");	// 瑞典克朗
		isoCode.add("DKK"); cibCode.add("22");	// 丹麦克朗
		isoCode.add("NOK"); cibCode.add("23");	// 挪威克朗
		isoCode.add("JPY"); cibCode.add("27");	// 日元
		isoCode.add("CAD"); cibCode.add("28");	// 加拿大元
		isoCode.add("AUD"); cibCode.add("29");	// 澳大利亚元
		isoCode.add("MYR"); cibCode.add("32");	// 马来西亚令吉
		isoCode.add("SGD"); cibCode.add("43");	// 新加坡元
		isoCode.add("MOP"); cibCode.add("81");	// 澳门元
		isoCode.add("THB"); cibCode.add("84");	// 泰国铢
		isoCode.add("NZD"); cibCode.add("87");	// 新西兰元
		isoCode.add("TWD"); cibCode.add("88");	// 新台币
	}
	
	/**
	 * 将ISO货币代码转换为兴业货币代码
	 * @param code ISO4217规定的货币代码，三位英文字母组成
	 * @return 兴业货币代码
	 */
	public static String iso2cib(String code) {
		int i = isoCode.indexOf(code);
		if (i > -1)
			return cibCode.get(i);
		else
			throw new AppRTException(AppExCode.ERR_INPUT,"无效的货币代码");
	}
	
	/**
	 * 将兴业货币代码转换为ISO货币代码
	 * @param code 兴业货币代码
	 * @return ISO4217货币代码
	 */
	public static String cib2iso(String code) {
		int i = cibCode.indexOf(code);
		if (i > -1)
			return isoCode.get(i);
		else
			throw new AppRTException(AppExCode.ERR_INPUT,"无效的货币代码");
	}
}
