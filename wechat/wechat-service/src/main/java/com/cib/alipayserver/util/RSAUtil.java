package com.cib.alipayserver.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.StringTokenizer;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/***
 * 
 *  文件名：RSAUtil.java<br><br>
 * 
 *  类名：RSA密码工具类<br><br>
 *
 *	ver	       变更日期           修改人       修改说明<br>
 *  V1.00  2011-6-10   liw    初版<br>
 *  ──────────────────────────────────<br>
 *  Copyright (c) 2006,2010 CIB All Rights Reserved. <br>
 *  LICENSE INFORMATION
 */
public class RSAUtil {
	private static Logger logger = Logger.getLogger(RSAUtil.class);

	public static KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
				new BouncyCastleProvider());
		int KEY_SIZE = 1024;
		keyPairGen.initialize(KEY_SIZE, new SecureRandom());
		KeyPair keyPair = keyPairGen.genKeyPair();
		return keyPair;
	}

	public static KeyPair generateKeyPair(int keySize) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
				new BouncyCastleProvider());
		keyPairGen.initialize(keySize, new SecureRandom());
		KeyPair keyPair = keyPairGen.genKeyPair();
		return keyPair;
	}

	
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey readX509PublicKey(String path) throws Exception {
		FileInputStream public_file_in = new FileInputStream(path);
		byte pub_key_bs[] = new byte[1024];
		public_file_in.read(pub_key_bs);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(pub_key_bs);
		KeyFactory kfac = KeyFactory.getInstance("RSA");
		RSAPublicKey public_key = (RSAPublicKey) kfac.generatePublic(spec);
		public_file_in.close();
		return public_key;
	}

	/**
	 * @param path
	 * @param pubKey
	 * @throws Exception
	 */
	public static void saveX509PublicKey(String path, RSAPublicKey pubKey)
			throws Exception {
		FileOutputStream public_file_out = new FileOutputStream(path);
		byte pub_key_bs[] = pubKey.getEncoded();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(pub_key_bs);
		public_file_out.write(spec.getEncoded());
		public_file_out.close();
	}

	/**
	 * 
	 * @param path
	 * @param priKey
	 * @throws Exception
	 */
	public static void savePKCS8PrivateKey(String path, RSAPrivateKey priKey)
			throws Exception {
		FileOutputStream private_file_out = new FileOutputStream(path);
		byte pri_key_bs[] = priKey.getEncoded();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pri_key_bs);
		private_file_out.write(spec.getEncoded());
		private_file_out.close();
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey readPKCS8PrivateKey(InputStream is)
			throws Exception {
		byte pri_key_bs[] = new byte[is.available()];
		for(int i = 0;is.available()>0;i++){
			pri_key_bs[i] = (byte)is.read();
		}
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pri_key_bs);
		KeyFactory kfac = KeyFactory.getInstance("RSA");
		RSAPrivateKey private_key = (RSAPrivateKey) kfac.generatePrivate(spec);
		is.close();
		return private_key;
	}

	/**
	 * @param key
	 * @param raw 
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key, byte raw[]) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
		cipher.init(2, key);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		for (int j = 0; raw.length - j * blockSize > 0; j++)
			bout.write(cipher.doFinal(raw, j * blockSize, blockSize));

		return bout.toByteArray();
	}

	/**
	 * @param key 
	 * @param data
	 * @return 
	 * @throws Exception
	 */
    public static byte[] encrypt(Key key, byte data[])
        throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
        cipher.init(1, key);
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(data.length);
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize == 0 ? data.length / blockSize : data.length / blockSize + 1;
        byte raw[] = new byte[outputSize * blocksSize];
        for(int i = 0; data.length - i * blockSize > 0; i++)
            if(data.length - i * blockSize > blockSize)
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
            else
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);

        return raw;
    }
    
    public static  byte[] getSHA1(String src) throws NoSuchAlgorithmException{
		   MessageDigest sha = MessageDigest.getInstance("SHA-1");
		   sha.update(src.getBytes());  
		   return  sha.digest();
    } 
    /**
	 * 此方法将给出的字符串source使用delim划分为单词数组。
	 * 
	 * @param source
	 *            需要进行划分的原字符串
	 * @param delim
	 *            单词的分隔字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组，
	 *         如果delim为null则使用逗号作为分隔字符串。
	 * 
	 * @since 0.1
	 */
	public static String[] split(String source, String delim) {
		String[] wordLists;
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		if (delim == null) {
			delim = ",";
		}
		StringTokenizer st = new StringTokenizer(source, delim);
		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}
}

