package com.newland.wechatServer.util;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intensoft.exception.AppBizException;

/**
 * 证书工具类
 * 
 * @author zhangzhaoxing 2009-12-31
 */
public final class CAUtil {
	private final static Logger log = LoggerFactory.getLogger(CAUtil.class);

	// 序列码、证书表中参考号长度32位
	final static int SERIALNUMLEN = 32;

	/**
	 * 从request X509证书DN号
	 * 
	 * @param request
	 *            HttpServletRequest实例
	 * @return 证书dn
	 */
	public static String getX509DN(HttpServletRequest request)
			throws AppBizException {
		X509Certificate x509Cert = getX509Certificate(request);

		if (x509Cert == null) {
			log.info("未透传X509证书X509Certificate");
		}

		return getX509DN(x509Cert);
	}

	/**
	 * X509Certificate对象中获取证书dnIso
	 * 
	 * @param x509Cert
	 *            X509Certificate对象
	 * @return 证书dnIso
	 * @throws AppBizException
	 */
	public static String getX509DN(X509Certificate x509Cert) {
		try {
			if (x509Cert == null) {
				return null;
			}

			x509Cert.checkValidity(new Date());
			return formatDN(x509Cert.getSubjectX500Principal().getName());
		} catch (Throwable e) {
			log.error("获取证书错误", e);
			return null;
		}
	}

	/**
	 * 获取证书序列号
	 * 
	 * @param request
	 *            HttpServletRequest实例
	 * @return 证书序列号
	 * @throws AppBizException
	 */
	public static String getCertSerialNum(HttpServletRequest request) {
		X509Certificate x509Cert = getX509Certificate(request);
		return getCertSerialNum(x509Cert);
	}

	/**
	 * X509Certificate对象中获取证书序列号
	 * 
	 * @param x509Cert
	 * @return
	 */
	public static String getCertSerialNum(X509Certificate x509Cert) {
		String serialNum = null;

		if (x509Cert != null) {
			serialNum = x509Cert.getSerialNumber().toString(16);
			if (serialNum.length() < SERIALNUMLEN) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < SERIALNUMLEN - serialNum.length(); i++) {
					sb.append("0");
				}
				sb.append(serialNum);
				serialNum = sb.toString();
			} else {
				serialNum = serialNum.substring(0, SERIALNUMLEN);
			}
		}

		return serialNum;
	}

	/**
	 * 功能：证书dnIso提取到证书CN
	 * 
	 * @param dnIso
	 *            证书dnIso
	 * @return 证书CN
	 */
	public static String getCNFromDnISO(String dnIso) {
		if (dnIso == null)
			return null;
		int index = dnIso.indexOf(",");
		if (index <= 0)
			return null;

		String strTemp = dnIso.substring(0, index);
		index = strTemp.indexOf("=");
		if (index <= 0)
			return null;

		return strTemp.substring(index + 1);
	}

	/**
	 * 从HttpServletRequest中获取透传证书X509Certificate对象
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 证书X509Certificate对象
	 */
	public static X509Certificate getX509Certificate(HttpServletRequest request) {
		X509Certificate[] x509Certs = (X509Certificate[]) request
				.getAttribute("javax.servlet.request.X509Certificate");
		if (x509Certs != null) {
			if (x509Certs.length > 0) {
				X509Certificate x509Cert = x509Certs[0];
				return x509Cert;
			}
		}

		return null;
	}

	/**
	 * 按照格式("="之前小写格式)输出
	 * 
	 * @param dn
	 *            证书DN
	 * @return
	 */
	public static String formatDN(String dn) {
		// 原始值：CN=041@132323@操作员@00000002, OU=Employees, OU=Local RA, O=CFCA
		// TEST CA, C=CN
		// 转化为：cn=041@132323@操作员@00000002, ou=Employees, ou=Local RA, o=CFCA
		// TEST CA, c=CN
		StringBuffer buff = new StringBuffer();
		StringTokenizer stoken = new StringTokenizer(dn, ",");

		int count = stoken.countTokens();
		for (int i = 0; i < count; i++) {
			String info = stoken.nextToken();

			int index = info.indexOf("=");
			String low = info.substring(0, index);
			buff.append(low.toLowerCase());
			buff.append(info.substring(index, info.length()));

			if (i < count - 1) {
				buff.append(", ");
			}
		}

		return buff.toString();
	}

	// public static void main(String[] args) {
	// String
	// dnIso="CN=041@132323@操作员@00000002, OU=Employees, OU=Local RA, O=CFCA TEST CA, C=CN";
	// System.err.println(CAUtil.formatDN(dnIso));
	// }
}
