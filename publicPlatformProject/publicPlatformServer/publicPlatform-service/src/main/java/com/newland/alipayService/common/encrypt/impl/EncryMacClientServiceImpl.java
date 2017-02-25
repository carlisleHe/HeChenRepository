package com.newland.alipayService.common.encrypt.impl;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import com.newland.alipayService.common.encrypt.EncryMacClientService;
/**
 * 网银加密服务类
 * @since 2010-01-27
 * @see
 */
@Service("encryMacClientService")
public class EncryMacClientServiceImpl implements EncryMacClientService {
	BigInteger e = new BigInteger("65537");
	BigInteger n;
	BigInteger in;
	int pinMaxLen = 128;
	byte pinBlock[];
	
	public EncryMacClientServiceImpl()
	{
		e = new BigInteger("65537");
		pinMaxLen = 128;
	}
	
	private String genRandom(int i)
	{
		byte abyte0[] = new byte[i];
		for(int j = 0; j < i; j++)
		{
			Integer integer = new Integer((int)((float)Math.random() * 57F) + 65);
			abyte0[j] = integer.byteValue();
		}

		return new String(abyte0);
	}

	private BigInteger mod(BigInteger biginteger, BigInteger biginteger1, BigInteger biginteger2)
	{
		BigInteger biginteger3 = null;
		try
		{
			biginteger3 = biginteger.modPow(biginteger1, biginteger2);
		}
		catch(ArithmeticException _ex)
		{
			return null;
		}
		return biginteger3;
	}
	
	
	public String PkEncryptAPin(String RSAPK,String APin)
	{
	    if (APin.length()<6) {
	        int apinLen = APin.length();
	        for (int i=0;i<6-apinLen;i++){
	            APin += " ";
	        }
	    }
	    	
		int i = APin.length();
		if(i >= pinMaxLen - 2)
			return null;
		Integer integer = new Integer(i / 10 + 48);
		Integer integer1 = new Integer(i % 10 + 48);
		byte abyte0[] = {
			integer.byteValue(), integer1.byteValue()
		};
		String APin2 = new String(abyte0) + APin + genRandom(pinMaxLen - i - 2);
		pinBlock = APin2.getBytes();
		in = new BigInteger(pinBlock);
		BigInteger biginteger = new BigInteger(RSAPK, 16);
		String APin3 = mod(in, e, biginteger).toString(16);
		int j = APin3.length();
		if(j < 256)
		{
			int k = 256 - j;
			String APin4 = "";
			for(int l = 0; l < k; l++)
				APin4 = APin4 + "0";

			return APin4 + APin3;
		} 
		else
		{
			return APin3;
		}
	}

	// ���ܴ���ƴ�����£�Data1 = (2λEPin���� + EPin + 2λAccNo���� + AccNo)
	// (2λData1���� + Data1 + �����)�����˼������
	public String PkEncryptEPin(String RSAPK,String EPin, String AccNo)
	{
		int i = 2 + EPin.length() + 2 + AccNo.length();
		if(i >= pinMaxLen - 2)
			return null;
		Integer integer = new Integer(i/256);
		Integer integer1 = new Integer(i%256);
		byte abyte0[] = {
			integer.byteValue(), integer1.byteValue()
		};
		int EPinLen = EPin.length();
		Integer integer2 = new Integer(EPinLen / 10 + 48);
		Integer integer3 = new Integer(EPinLen % 10 + 48);
		byte abyte1[] = {
			integer2.byteValue(), integer3.byteValue()
		};
		int AccNoLen = AccNo.length();
		Integer integer4 = new Integer(AccNoLen / 10 + 48);
		Integer integer5 = new Integer(AccNoLen % 10 + 48);
		byte abyte2[] = {
			integer4.byteValue(), integer5.byteValue()
		}; 

		String fillPin = new String(abyte0) + new String(abyte1) + EPin + new String(abyte2) + AccNo + genRandom(pinMaxLen - i - 2);
		pinBlock = fillPin.getBytes();
		in = new BigInteger(pinBlock);
		BigInteger biginteger = new BigInteger(RSAPK, 16);
		String EPin3 = mod(in, e, biginteger).toString(16);
		int j = EPin3.length();
		if(j < 256)
		{
			int k = 256 - j;
			String EPin4 = "";
			for(int l = 0; l < k; l++)
				EPin4 = EPin4 + "0";

			return EPin4 + EPin3;
		}
		else
		{
			return EPin3;
		}
	}
}
