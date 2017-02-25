package com.newland.base.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.crypto.Cipher;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.intensoft.codec.Base64;
/***
 * 
 *  文件名：UnicomProxy.java<br><br>
 * 
 *  类名：联通运营商透传加密类<br><br>
 *
 *	ver	       变更日期           修改人       修改说明<br>
 *  V1.00  2011-6-10   liw    初版<br>
 *  ──────────────────────────────────<br>
 *  Copyright (c) 2006,2010 CIB All Rights Reserved. <br>
 *  LICENSE INFORMATION
 */
public class UnicomProxy {

//	public static String encryptUserInfo(String pubPath, String userInfo)
//			throws Exception {
//
//		RSAPublicKey recoveryPubKey = RSAUtil.readX509PublicKey(pubPath);
//		byte[] raw = userInfo.getBytes();
//		raw = RSAUtil.encrypt(recoveryPubKey, raw);
//		Base64 base64 = new Base64();
//		String base64data = base64.encode(raw).toString();
//		return base64data;
//	}
	/**
	 * 获取手机号码
	 * 
	 * @return 手机号码
	 */
	public static String getReqPhoneNum(String mobilePhone) {
		if (mobilePhone == null)
			return "";
		if (mobilePhone.startsWith("86"))
			mobilePhone = mobilePhone.substring(2, mobilePhone.length());
		else if (mobilePhone.startsWith("+86"))
			mobilePhone = mobilePhone.substring(3, mobilePhone.length());
		else if (mobilePhone.trim().length() != 11)
			mobilePhone = "";
		else {
			try {
				Integer.parseInt(mobilePhone.substring(0, 4));
			} catch (Exception ex) {
				mobilePhone = "";
			}
		}
		return mobilePhone;
	}

	/***************************
	 * 解密--联通公钥解密(银行调用) pubPath 银行私钥 sign 手机信息密文数据 return 手机信息明文数据
	 ***************************/
//	public static String decryptUserInfo(InputStream is, String userInfo)
//			throws Exception {
//		RSAPrivateKey recoveryPriKey = RSAUtil.readPKCS8PrivateKey(is);
//		Base64 base64 = new Base64();
//		byte[] raw = base64.decode(userInfo.getBytes());
//		raw = decrypt(recoveryPriKey, raw);
//		return new String(raw, "UTF-8");
//	}

	/**
	 * 按照对应的算法--解密
	 * 
	 * @param key
	 * @param raw
	 * @return
	 * @throws Exception
	 */
//	public static byte[] decrypt(Key key, byte raw[]) throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
//				new BouncyCastleProvider());
//		cipher.init(Cipher.DECRYPT_MODE, key);
//		int blockSize = cipher.getBlockSize();
//		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
//		for (int j = 0; raw.length - j * blockSize > 0; j++)
//			bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
//		return bout.toByteArray();
//	}
	public static void main(String[] a) throws Exception{
		/*String userInfo_src = "13811111111|20080821000|NOKIA3230";
		System.out.println("明文为"+userInfo_src);
		String BankPubPath = "C:\\pub\\IBWapBank_public.pfx.cer";		
		String userInfo = encryptUserInfo(BankPubPath,userInfo_src);
		System.out.println("加密后："+userInfo);
		
		String temp = URLEncoder.encode(userInfo,"UTF-8");
		System.out.println("encode之后:"+temp);*/
		String userInfo = "rSafV4QCVXNFPGvAlTIQeenRCtgf3xRerpkjpTYLCLOsTwz2SQ2REf8XvKD8kLzABJeKYlgLFvQ0LGalX7V6yyQeRgd6k0iG86C6ZIZrKou3kgSnTjub5DT9ptHzvaOwg4eQ3xY+txoFqTj4xC328zt+QQLm/onk9VNEK0R22FEOZVFDKdBmyt/tPHy5yoznry44PCuqjuJXpgB/R1oiTkUgv0FsE3vAPbB3b1Lk/AcF64iqKnaAnz3WpiCk5oJqUCUnJEYlnS4vsHh6FERJzOCSFa8+Edk/Ph6vszPz8jZZuI+0uQe6ULQjr0ofS0DAp2nqWWddYcYhSv3G90zTkg==";
		String temp= URLDecoder.decode(userInfo,"UTF-8");
		temp = temp.replaceAll(" ", "+");		
		JarFile jarFile = new JarFile("C:\\a.jar");
		JarEntry entry = jarFile.getJarEntry("IBCibBank.der");
		
		InputStream is = jarFile.getInputStream(entry);
//		temp = decryptUserInfo(is,temp);
		System.out.println(temp);
	}
}
