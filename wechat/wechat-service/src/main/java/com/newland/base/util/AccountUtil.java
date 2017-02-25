package com.newland.base.util;

import org.apache.commons.lang.StringUtils;

/**
 * 账户工具类<br>
 * 
 * @author yelm
 * @since 2011-03-02
 */
public class AccountUtil {
	/** 兴业借记卡卡BIN */
	private static final String[] debitCardBin = { "622909", "622908",
			"966666", "90592", "438588" };

	/** 兴业信用卡卡BIN */
	private static final String[] creditCardBin = { "483572", "628212",
			"528057", "461982", "622901", "622902", "451289", "451290",
			"520198", "451291", "523036", "524070", "527414", "486493",
			"486494", "622922", "445055", "486861", "521289", "625082",
			"625083", "625084", "625085", "625086", "625087", "625960",
			"625961", "625962", "625963", "548738", "549633", "552398",
			"625353", "625356", "483817", "519932" };  

	/** 兴业账号校验位计算因子 */
	private static final byte[] factor = { 11, 13, 17, 19, 23, 29, 31, 37, 41,
			43, 47, 53, 59, 61, 67, 71, 73, 79 };

	/**
	 * 根据卡号获取借记卡卡bin
	 * 
	 * @param acctNo
	 * @return
	 */
	public static String calcDebitCardBin(String acctNo) {
		if (StringUtils.isEmpty(acctNo) || acctNo.length() == 11) {
			// 11位卡号不校验
			return null;
		}
		// 因为顺通卡(90592)的存在，不能直接取0-6位作为行内帐号，需先去卡BIN
		for (int i = 0; i < debitCardBin.length; i++) {
			if (acctNo.startsWith(debitCardBin[i])) {
				return debitCardBin[i];
			}
		}
		return null;
	}

	/**
	 * 根据输入的银行账号，计算11位行内账号，不校验卡号是否正确<br>
	 * 如果是信用卡或他行卡，将原样返回。
	 * 
	 * @param acctNo
	 *            银行帐号，一般为18位全卡号
	 * @return 11位行内帐号
	 */
	public static String calcCibAcctNo(String acctNo) {
		if (acctNo == null)
			return null;

		// 若长度为11，不论是否符合卡BIN，均会原样返回
		if (acctNo.length() == 11)
			return acctNo;

		String cibAcctNo = null;
		// 因为顺通卡(90592)的存在，不能直接取6-17位作为行内帐号，需先去卡BIN
		for (int i = 0; i < debitCardBin.length; i++) {
			if (acctNo.startsWith(debitCardBin[i])) {
				cibAcctNo = acctNo.substring(debitCardBin[i].length());
				break;
			}
		}
		// 如果符合兴业借记卡BIN
		if (cibAcctNo != null) {
			// 最常见，一般兴业卡号为18位
			if (cibAcctNo.length() > 11)
				return cibAcctNo.substring(0, 11);
			// 缺银联校验位的，去卡BIN后直接返回
			else if (cibAcctNo.length() == 11)
				return cibAcctNo;
			// 去卡BIN后小于11位，可能是他行卡被误判为兴业卡，原样返回
			else
				return acctNo;
		} else {
			// 如果不符合兴业借记卡BIN，原样返回
			return acctNo;
		}
	}

	public static String calcBranchNo(String acctNo) {
		try{
			return StringUtils.substring(calcCibAcctNo(acctNo), 0, 2);
		} catch (Exception e) {
			// 出错了就返回总行地区号
			return "08";
		}
	}

	/**
	 * 根据输入的银行账号，计算10位核心客户号，不校验卡号是否正确<br>
	 * 如果是信用卡或他行卡，将原样返回。
	 * 
	 * @param acctNo
	 *            银行帐号，一般为18/16位全卡号
	 * @return 10位核心客户号
	 */
	public static String calcCtspNo(String acctNo) {
		if (acctNo == null)
			return null;

		// 若长度为10位，则直接返回
		if (acctNo.length() == 10) {
			return acctNo;
		}

		// 若长度为11，返回前10位
		if (acctNo.length() == 11)
			return acctNo.substring(0, 10);

		String cibAcctNo = null;
		// 因为顺通卡(90592)的存在，不能直接取6-16位作为核心客户号，需先去卡BIN
		for (int i = 0; i < debitCardBin.length; i++) {
			if (acctNo.startsWith(debitCardBin[i])) {
				cibAcctNo = acctNo.substring(debitCardBin[i].length());
				break;
			}
		}
		// 如果符合兴业借记卡BIN
		if (cibAcctNo != null) {
			// 最常见，一般兴业卡号为18位
			if (cibAcctNo.length() >= 11)
				return cibAcctNo.substring(0, 10);
			// 去卡BIN后小于11位，可能是他行卡被误判为兴业卡，原样返回
			else
				return acctNo;
		} else {
			// 如果不符合兴业借记卡BIN，原样返回
			return acctNo;
		}
	}

	/**
	 * 检验卡号是否为兴业全卡号（支持理财卡、信用卡）
	 * 
	 * @param acctNo
	 * @return
	 */
	public static boolean isFullDebitCard(String acctNo) {
		if (acctNo == null || acctNo.length() < 16) {
			return false;
		}
		return isDebitCard(acctNo);
	}


	/**
	 * 判断卡号是否为兴业借记卡
	 * 
	 * @param acctNo
	 *            兴业卡号
	 */
	public static boolean isDebitCard(String acctNo) {
		if (acctNo == null)
			return false;

		String cibAcctNo = calcCibAcctNo(acctNo);
		// 换成行内账号后为11位且通过校验位计算的
		if (cibAcctNo.length() == 11) {
			// 966666开头的老卡因没有算法不做校验
			if (acctNo.startsWith("966666")) {
				return true;
			}
			return verifyCibCheckDigit(cibAcctNo);
		} else {
			return false;
		}
	}

	/**
	 * 判断卡号是否为兴业E卡
	 * 
	 * @param acctNo
	 *            兴业卡号
	 */
	public static boolean isECard(String acctNo) {
		if (acctNo == null)
			return false;

		String cibAcctNo = calcCibAcctNo(acctNo);
		if (cibAcctNo.length() != 11)
			return false;
		if (verifyCibCheckDigit(cibAcctNo) == false)
			return false;
		if (cibAcctNo.charAt(2) == '9')
			return true;
		else
			return false;
	}

	/**
	 * 判断卡号是否为兴业信用卡
	 * 
	 * @param acctNo
	 *            兴业卡号
	 */
	public static boolean isCreditCard(String acctNo) {
		if (acctNo == null)
			return false;

		if (acctNo.length() != 16)
			return false;
		for (int i = 0; i < creditCardBin.length; i++) {
			if (acctNo.startsWith(creditCardBin[i]))
				return true;
		}
		return false;
	}

	/**
	 * 计算兴业客户号校验位。CD = Check Digit 校验位。
	 * 
	 * @param cibAcctNo
	 *            借记卡号前9位 或 对公账户前16位
	 * @return 客户号校验位
	 */
	public static String calcCibCustomerCD(String cibAcctNo) {
		int dig = 10;
		int len = cibAcctNo.length();
		// 如果是借记卡号，客户号校验位(0-9生成第10位)
		// 如果是对公账户，客户号校验位(0-16生成第17位)
		if (len == 9 || len == 16) {
			for (int i = 0; i < len; i++) {
				dig = dig + (cibAcctNo.charAt(i) - '0');
				dig = dig % 10;
				if (dig == 0)
					dig = 10;
				dig = dig * 2;
				dig = dig % (10 + 1);
			}
			if (dig >= 10)
				dig = dig - 10;
			if (dig >= 10)
				dig = dig - 10;
			byte[] b = { (byte) (dig + '0') };
			String cd = new String(b);
			return cd;
		} else {
			return null;
		}
	}

	/**
	 * 计算兴业账号校验位。CD = Check Digit 校验位。
	 * 
	 * @param cibAcctNo
	 *            账户前17位，必须为17位
	 * @return 账号校验位
	 */
	public static String calcCibAccountCD(String cibAcctNo) {
		int dig = 10;
		int len = cibAcctNo.length();
		if (len == 17) {
			for (int i = len - 1; i >= 0; i--) {
				dig = dig + (cibAcctNo.charAt(i) - '0') * factor[i];
				dig = dig % 10;
				if (dig == 0)
					dig = 10;
				dig = dig * 2;
				dig = dig % (10 + 1);
			}
			if (dig >= 10)
				dig = dig - 10;
			if (dig >= 10)
				dig = dig - 10;
			byte[] b = { (byte) (dig + '0') };
			String cd = new String(b);
			return cd;
		} else {
			return null;
		}
	}

	/**
	 * 校验兴业卡校验位（行内算法），同时适用于对私、对公账户<br>
	 * 为同时适用于对公对私账户，仅校验客户号校验位
	 * 
	 * @param cibAcctNo
	 *            必须为行内账号(11位、18位)
	 * @return true 表示校验位验证通过
	 */
	public static boolean verifyCibCheckDigit(String cibAcctNo) {
		if (cibAcctNo == null)
			return false;
		int len = cibAcctNo.length();
		if (len == 11){
			return true;
		}
		if (len == 18) {
			String acd = cibAcctNo.substring(len - 2, len - 1);
			String cd = calcCibCustomerCD(cibAcctNo.substring(0, len - 2));
			return acd.equals(cd);
		} else {
			return false;
		}
	}

	/**
	 * 计算银联卡卡号，支持所有银联卡<br>
	 * 例如：输入17位卡号，计算第18位校验位，并返回18位卡号
	 * 
	 * @param acctNo
	 *            不含银联校验位的卡号
	 * @return 完整的银联卡号
	 */
	public static String calcUnionAcctNo(String acctNo) {
		if (acctNo == null)
			return null;

		float floatLenStr = acctNo.length();
		int modLen = new Double(Math.ceil(floatLenStr / 2)).intValue();
		int sum = 0, j = 0;
		String[] modStr = new String[modLen];

		for (int i = acctNo.length(); i > 0; i -= 2) {
			modStr[j] = String.valueOf(Integer.parseInt(acctNo.substring(i - 1,
					i)) * 2);
			int value = Integer.parseInt(modStr[j]);
			if (value >= 10) {
				value -= 9;
			}
			sum += value;

			if (i >= 2) {
				sum += Integer.parseInt(acctNo.substring(i - 2, i - 1));
			}
			j++;
		}
		String sumStr = String.valueOf(sum);
		String tmp = sumStr.substring(sumStr.length() - 1, sumStr.length());

		if (tmp.equals("0")) {
			return acctNo + "0";
		} else {
			return acctNo + String.valueOf(10 - Integer.parseInt(tmp));
		}
	}

	/**
	 * 校验银联卡号校验位，支持各类银联卡
	 * 
	 * @param acctNo
	 *            银联卡完整卡号
	 * @return true 表示校验位验证通过
	 */
	public static boolean verifyUnionCheckDigit(String acctNo) {
		if (acctNo == null)
			return false;
		// 2013-10-31 huangxun 966666开通的卡不校验是否为银联卡
		if (acctNo.startsWith("966666")) {
			return true;
		}
		String acctNo17 = acctNo.substring(0, acctNo.length() - 1);
		String acctNo18 = calcUnionAcctNo(acctNo17);
		return acctNo.equals(acctNo18);
	}

	/**
	 * 计算兴业对公账户账号(16或17位补全为18位)
	 * 
	 * @param cibAcctNo
	 *            不含校验位的对公账户（16位或17位）
	 * @return 18位对公账户
	 */
	public static String calcCibCorpAcctNo(String cibAcctNo) {
		if (cibAcctNo == null)
			return null;

		String acctNo = null;
		String cd = null;
		if (cibAcctNo.length() == 16) {
			// 补客户号校验位
			cd = calcCibCustomerCD(cibAcctNo);
			if (cd == null)
				return null;
			acctNo = cibAcctNo + cd;
			// 补账号校验位
			cd = calcCibAccountCD(acctNo);
			if (cd == null)
				return null;
			acctNo = acctNo + cd;
		} else if (cibAcctNo.length() == 17) {
			// 补账号校验位
			cd = calcCibAccountCD(acctNo);
			if (cd == null)
				return null;
			acctNo = acctNo + cd;
		} else {
			acctNo = null;
		}
		return acctNo;
	}

	/**
	 * 计算屏蔽后的银行账号，取账号后4位
	 * 
	 * @param accountNo
	 *            账号
	 * @return 银行账号后4位
	 */
	public static String calcMaskAcctNo(String accountNo) {
		String mask;
		// 18位的卡号
		if (accountNo.length() == 18) {
			mask = "*"
					+ accountNo.substring(accountNo.length() - 5,
							accountNo.length() - 1) + "*";
		}
		// 11位核心卡号、10位核心客户号或16位信用卡
		else if (accountNo.length() > 4) {
			if (accountNo.startsWith("90592") && accountNo.length() == 16
					|| accountNo.length() == 11 || accountNo.length() == 10) {
				mask = "*"+accountNo.substring(accountNo.length()-4)+ "*";
			}else{
				mask = "*" + accountNo.substring(accountNo.length() - 4);
			}
		} else
			mask = "*" + accountNo;
		return mask;
	}

	/**
	 * 计算屏蔽后的手机号，如133****9999
	 * 
	 * @param mobile
	 *            手机号
	 * @return 屏蔽后的手机号
	 */
	public static String calcMaskMobile(String phone) {
		String mask;
		if (phone.length() != 11) {
			return phone;
		} else {
			mask = phone.substring(0, 3) + "****"
					+ phone.substring(phone.length() - 4);
		}
		return mask;
	}

	/**
	 * 检查是否为简单密码
	 * 
	 * @param password
	 *            密码
	 * @param cardNo
	 *            卡号
	 * @return true 表示是简单密码
	 */
	public static boolean isSimplePassword(String password, String cardNo) {
		if (password == null)
			return true;

		// 密码长度小于6
		if (password.length() < 6) {
			return true;
		}
		// 密码为重复字符 如111111，222222，3333333，aaaaaaa
		if (password.matches(password.substring(0, 1) + "{" + password.length()
				+ "}")) {
			return true;
		}
		// 密码为简单数字升序
		if ("0123456789".indexOf(password) > -1) {
			return true;
		}
		// 密码为简单数字降序
		if ("9876543210".indexOf(password) > -1) {
			return true;
		}
		// 前三位数字一样，后三位数字一样的组合（例：000111,000222，333444等）
		if (password.matches(password.substring(0, 1) + "{3}")
				&& password.matches(password.substring(3, 4) + "{3}")) {
			return true;
		}
		// 与卡号尾数相同
		if (cardNo != null && cardNo.endsWith(password)) {
			return true;
		}
		return false;
	}

	/**
	 * 计算屏蔽后的客户名称
	 * 
	 * @param name
	 * @return
	 */
	public static String calcMaskOwnerName(String name) {
		if (StringUtils.isEmpty(name))
			return "";
		if (name.length() == 1)
			return name;
		if (name.length() == 2)
			return "*" + name.substring(1);
		String newName = name.substring(0, 1);
		for (int i = 1; i < name.length() - 1; i++) {
			newName += "*";
		}
		newName += name.substring(name.length() - 1);
		return newName;
	}
}