/*
 * Project: PersonalBank3
 * FileName EncryMacClientService.java
 */
package com.cib.alipayserver.common.encrypt;

/**
 * 网银加密服务接口
 * @since 2011-01-27
 * @see
 */
public interface EncryMacClientService {
    
	public String PkEncryptAPin(String RSAPK,String APin);
	
	
	public String PkEncryptEPin(String RSAPK,String EPin, String AccNo);
}
