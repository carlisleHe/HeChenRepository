package com.newland.alipayService.common.encrypt.mac;

import java.io.UnsupportedEncodingException;

import com.intensoft.encryption.digest.Digest;
import com.intensoft.encryption.digest.SimpleDigest;
import com.intensoft.encryption.mac.SimpleMsgAuthenticate;

/**
 * 消息校验码静态工厂
 * <p>
 * 
 * @author lance
 */
public class MsgAuthenticateFactory {
	
	private static MsgAuthenticate oldMac = new OldMacImpl();
	private static MsgAuthenticate newMac = new NewMacImpl();
	
	/**
	 * 获取旧的消息校验码算法
	 * @return
	 */
	public static MsgAuthenticate getOldMac(){
		return oldMac;
	}
	/**
	 * 获取新的消息校验码算法
	 * @return
	 */
	public static MsgAuthenticate getNewMac(){
		return newMac;
	}

	private static class OldMacImpl implements MsgAuthenticate{
		private final String charset = "GBK";
		private final com.intensoft.encryption.mac.MsgAuthenticate macGenerator = new SimpleMsgAuthenticate() ;
		@Override
		public boolean checkMac(String seed, String content, String mac) throws UnsupportedEncodingException {
			byte[] seedBytes = seed.getBytes(charset);
			byte[] contentBytes = content.getBytes(charset);
			
			return macGenerator.checkMac(seedBytes, contentBytes, mac);
		}
		@Override
		public String genMac(String seed, String content) throws UnsupportedEncodingException {
			byte[] seedBytes = seed.getBytes(charset);
			byte[] contentBytes = content.getBytes(charset);
			
			return macGenerator.genMac(seedBytes, contentBytes);
		}
	}
	
	private static class NewMacImpl implements MsgAuthenticate{
		
		private final String algorithm = "MD5";
		
		private final Digest digest = new SimpleDigest();
		
		private final String charset = "UTF-8";	
		
		@Override
		public boolean checkMac(String seed, String content, String mac) throws UnsupportedEncodingException {
			byte[] bytes = (content+seed).getBytes(charset);
			String result = digest.doDigest(bytes, algorithm);
			return result.equals(mac);
		}
		@Override
		public String genMac(String seed, String content) throws UnsupportedEncodingException {
			byte[] bytes = (content+seed).getBytes(charset);
			
			return digest.doDigest(bytes, algorithm);
		}
	}
	
}