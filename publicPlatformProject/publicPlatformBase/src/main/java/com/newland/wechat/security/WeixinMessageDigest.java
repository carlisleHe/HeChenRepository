package com.newland.wechat.security;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 
 * @author dvlp
 *
 */
public class WeixinMessageDigest {
	
      
    /** 
     * 
     * @return 
     */  
    public static WeixinMessageDigest getInstance(String token) {  
        return new WeixinMessageDigest(token);  
    }  
      
    private MessageDigest digest;  
    
    private String token;
      
    private WeixinMessageDigest(String token) {  
    	this.token = token;
        try {  
            digest = MessageDigest.getInstance("SHA-1");  
        } catch(Exception e) {  
            throw new InternalError("init MessageDigest error:" + e.getMessage());  
        }  
    }  
  
      
  
    /** 
     * 
     * @param b 
     * @return 
     */  
    private static String byte2hex(byte[] b) {  
        StringBuilder sbDes = new StringBuilder();  
        String tmp = null;  
        for (int i = 0; i < b.length; i++) {  
            tmp = (Integer.toHexString(b[i] & 0xFF));  
            if (tmp.length() == 1) {  
                sbDes.append("0");  
            }  
            sbDes.append(tmp);  
        }  
        return sbDes.toString();  
    }  
      
    private String encrypt(String strSrc) {  
        String strDes = null;  
        byte[] bt = strSrc.getBytes();  
        digest.update(bt);  
        strDes = byte2hex(digest.digest());  
        return strDes;  
    }  
  
    /** 
     *
     * 
     * @param signature 
     * @param timestamp 
     * @param nonce 
     * @return 
     */  
    public boolean validate(String signature, String timestamp, String nonce){  

        String token = getToken();  
        String[] arrTmp = { token, timestamp, nonce };  
        Arrays.sort(arrTmp);  
        StringBuffer sb = new StringBuffer();  

        for (int i = 0; i < arrTmp.length; i++) {  
            sb.append(arrTmp[i]);  
        }  
        String expectedSignature = encrypt(sb.toString());  

        if(expectedSignature.equals(signature)){  
            return true;  
        }  
        return false;  
    }  
      
    private String getToken(){  
        return token;  
    }  
  
    public static void main(String[] args) {  
          
        String signature="f86944503c10e7caefe35d6bc19a67e6e8d0e564";
        String timestamp="1371608072";
        String nonce="1372170854";
          
        WeixinMessageDigest wxDigest = WeixinMessageDigest.getInstance("11111");  
        boolean bValid = wxDigest.validate(signature, timestamp, nonce);          
        if (bValid) {  
            System.out.println("token!");  
        }else {  
            System.out.println("token!");  
        }  
    }  

}
