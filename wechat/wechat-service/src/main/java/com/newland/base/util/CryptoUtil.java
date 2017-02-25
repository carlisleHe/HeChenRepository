package com.newland.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.base.common.Const;

/**
 * 加密算法工具类
 * 
 * @author yelm
 * @since 2007-05-12
 */
public class CryptoUtil {
	private static final Logger log = LoggerFactory.getLogger(CryptoUtil.class);

	/**
	 * MD5摘要算法，输入内容将被UTF-8编码
	 * 
	 * @param content
	 *            输入明文
	 * @return 内容摘要，32位16进制字符串
	 */
	public static String encryptByMD5(String content) {
		if (content == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] output = md.digest(content.getBytes("UTF-8"));
			return ConvertUtil.bytesToHexStr(output);
		} catch (NoSuchAlgorithmException e) {
			log.error("无法加载MD5算法", e);
		} catch (UnsupportedEncodingException e) {
			log.error("无法将输入字符串转换到utf-8编码。", e);
		}
		return null;
	}

	/**
	 * SHA摘要算法，输入内容将被UTF-8编码
	 * 
	 * @param content
	 *            输入明文
	 * @return 内容摘要，40位16进制字符串
	 */
	public static String encryptBySHA(String content) {
		if (content == null)
			return null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] output = md.digest(content.getBytes("UTF-8"));
			return ConvertUtil.bytesToHexStr(output);
		} catch (NoSuchAlgorithmException e) {
			log.error("无法加载SHA算法。", e);
		} catch (UnsupportedEncodingException e) {
			log.error("无法将输入字符串转换到utf-8编码。", e);
		}
		return null;
	}

	/**
	 * DES加密，输入内容将被UTF-8编码后进行加密，密钥长度不要大于8位
	 * 
	 * @param key
	 *            密钥
	 * @param content
	 *            明文
	 * @param encoding
	 *            编码
	 * @return 密文
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptByDES(String key, String content,
			String encoding) throws UnsupportedEncodingException {
		if ((key == null) || (content == null))
			return null;

		// 生成密钥，密钥长度限定为8位，如果超出8位取前8位
		byte[] tmpBytes = key.getBytes(encoding);
		byte[] keyBytes = new byte[8];
		for (int i = 0; i < tmpBytes.length && i < keyBytes.length; i++) {
			keyBytes[i] = tmpBytes[i];
		}
		// DES加密成为密文
		try {
			Key k = new SecretKeySpec(keyBytes, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] output = cipher.doFinal(content.getBytes(encoding));
			return ConvertUtil.bytesToHexStr(output);
		} catch (Exception e) {
			log.error("DES加密失败。", e);
		}
		return null;
	}

	/**
	 * DES解密，输入内容是密文，密钥长度不要大于8位
	 * 
	 * @param key
	 *            密钥
	 * @param cipherText
	 *            密文
	 * @param encoding
	 *            编码
	 * @return 明文
	 * @throws UnsupportedEncodingException
	 */
	public static String decryptByDES(String key, String cipherText,
			String encoding) throws UnsupportedEncodingException {
		if ((key == null) || (cipherText == null))
			return null;

		// 生成密钥，密钥长度限定为8位，如果超出8位取前8位
		byte[] tmpBytes = key.getBytes(encoding);
		byte[] keyBytes = new byte[8];
		for (int i = 0; i < tmpBytes.length && i < keyBytes.length; i++) {
			keyBytes[i] = tmpBytes[i];
		}
		// DES解密成为明文
		try {
			Key k = new SecretKeySpec(keyBytes, "DES");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, k);
			byte[] output = cipher.doFinal(ConvertUtil
					.hexStrToBytes(cipherText));
			return new String(output, encoding);
		} catch (Exception e) {
			log.error("DES解密失败。", e);
		}
		return null;
	}

	/**
	 * MD5加密文件
	 * 
	 * @param file
	 *            加密文件
	 * @return 文件的MD5值
	 */
	public static String encryptFileByMD5(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			int length = 0;
			byte[] bytes = new byte[4096];
			while ((length = in.read(bytes)) > 0) {
				md.update(bytes, 0, length);
			}
			byte[] output = md.digest();
			String md5 = ConvertUtil.bytesToHexStr(output);
			return md5;
		} catch (NoSuchAlgorithmException e) {
			log.error("无法加载MD5算法", e);
		} catch (FileNotFoundException e) {
			log.error("文件无法找到", e);
		} catch (IOException e) {
			log.error("md5加密失败", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void decryptUrlParamsByDES(Map session, String content,
			String key) {
		try {
			String parameters = decryptByDES(key, content, HTTP.UTF_8);
			String params[] = parameters.split("&");
			for (String param : params) {
				int i = param.indexOf('=');
				if (i > 0 && i < param.length()) {
					session.put(param.substring(0, i), param.substring(i + 1));
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(encryptByDES(Const.DES_KEY, "a1b2c3d4e5", "UTF-8"))	;
		System.out.println(decryptByDES(Const.DES_KEY, "411564E18D7752AB998E10C680A0B766", "UTF-8"));
	}
}
