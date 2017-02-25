// Date: 2002/11/12
// Author: Wolfgang Wang

package com.newland.alipayService.common.encrypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

public class UnionIBWebServerMDL
{
	String 		ipOfHsm;
	int			portOfHsm;
	Socket 			sckConn;
	DataInputStream 	dataRecvFromSck;
	DataOutputStream 	dataSendToSck;
	String			pvk = "0000000000000000";
	String			pvkBMKIndex;
	String			vkIndex;
	String			pvkIndex;
	
	public UnionIBWebServerMDL(String userDefinedIPOfHsm, int portOfHsm)
	{
		ipOfHsm = userDefinedIPOfHsm;
		this.portOfHsm = portOfHsm;
		pvkBMKIndex = "001";
		pvkIndex = "001";
		vkIndex = "01";
		
		/*
		System.out.println(ipOfHsm);
		System.out.println(String.valueOf(portOfHsm));
		System.out.println(pvkBMKIndex);
		System.out.println(pvk);
		System.out.println(vkIndex);
		*/
		
	}
	
	// 建立与HSM的连接
	public boolean connectHsm()
	{
		try
		{
			sckConn = new Socket(ipOfHsm, portOfHsm);
			dataRecvFromSck = new DataInputStream(sckConn.getInputStream());
			dataSendToSck = new DataOutputStream(sckConn.getOutputStream());
		}
		catch(IOException _ex)
		{
			return false;
		}
		return true;
	}

	// 关闭与HSM的连接
	public void closeHsm()
	{
		try
		{
			dataSendToSck.close();
			dataRecvFromSck.close();
			sckConn.close();
			return;
		}
		catch(IOException _ex)
		{
			System.out.println("socket close error");
		}
	}

	// 测试密码机
	public boolean testHsm()
	{
		String hsmCmdBuf = "01";
		byte abyte10[] = hsmCmdBuf.getBytes();

		String hsmResBuf = commWithHsm(abyte10);
		
		return hsmResBuf == null? false:true;
	}

	public String genRSA()
	{
		String hsmCmdBuf = "341024" + vkIndex;

		byte abyte10[] = hsmCmdBuf.getBytes();
		
		String pk = commWithHsmFor34Cmd(abyte10);

		return	pk;
		
	}
	


	// 生成公钥, 返回PVK的值
	public String getPVK()
	{
		String hsmCmdBuf = "32" + pvkBMKIndex + pvkIndex;

		byte abyte10[] = hsmCmdBuf.getBytes();
		
		String s = commWithHsm(abyte10);
		if (!s.regionMatches(0,"3300",0,4)){
			return null;
		}
		return	s.substring(4).trim();
		
	}
	

	// 验证EPin
	public boolean authEPin(String EPinProByPK, String EPinStoredInDB)
	{
		String s3 = transEPin(EPinProByPK);
		if(s3 == null)
			return false;
		String s4 = EPinStoredInDB;
		return s3.compareTo(EPinStoredInDB) == 0;
	}
	
	// 对EPin进行签名转换,转换结果为可以存储到库中的密文
	public String transEPin(String EPinProByPK)
	{
		if (EPinProByPK == null)
			return null;
		if ((pvk = getPVK()) == null){			
			return null;
		}

		String hsmCmdBuf = "40" + vkIndex + pvkBMKIndex + pvk;
		int i = hsmCmdBuf.length();
		byte abyte1[] = hsmCmdBuf.getBytes();

		byte abyte0[] = transBcdHexToBin(EPinProByPK);
		int j = abyte0.length;
		byte abyte2[] = new byte[i + j];
		for(int k = 0; k < i; k++)
			abyte2[k] = abyte1[k];
		for(int l = 0; l < j; l++)
			abyte2[l + i] = abyte0[l];

		String s3 = commWithHsm(abyte2);

		if (!s3.regionMatches(0,"4100",0,4))
			return null;
		return s3.substring(4).trim();
	}
	
	// 对EPin进行签名
	public String signEPin(String RSAPK,String EPin,String AccNo)
	{
		String s = PkEncryptEPin(RSAPK,EPin,AccNo);
		return transEPin(s);
	}


	// 解密EPin
	public String decryptEPin(String EPinProByPK)
	{
		byte abyte0[] = transBcdHexToBin(EPinProByPK);
		String hsmCmdBuf = "42" + vkIndex;
		int i = hsmCmdBuf.length();
		byte abyte1[] = hsmCmdBuf.getBytes();
		int j = abyte0.length;
		byte abyte2[] = new byte[i + j];
		for(int k = 0; k < i; k++)
			abyte2[k] = abyte1[k];

		for(int l = 0; l < j; l++)
			abyte2[l + i] = abyte0[l];

		String s3 = commWithHsm(abyte2);
		
		if (s3.length() < 8)
			return(null);
			
		String pinLenStr = s3.substring(6,8);
		Integer lenOfEPin = Integer.valueOf(pinLenStr);
		
		if (lenOfEPin.intValue() + 8 > s3.length())
			return null;
	
		return(s3.substring(8,lenOfEPin.intValue()+8));
	}

	// APIN 转换，
	public String transAPin(String APinProByPK,String AccNo)
	{
		if (APinProByPK == null)
			return null;

		String hsmCmdBuf = "79" + vkIndex;
		int i = hsmCmdBuf.length();
		byte abyte1[] = hsmCmdBuf.getBytes();

		byte abyte0[] = transBcdHexToBin(APinProByPK);
		int j = abyte0.length;
		byte abyte2[] = new byte[i + j];
		for(int k = 0; k < i; k++)
			abyte2[k] = abyte1[k];
		for(int l = 0; l < j; l++)
			abyte2[l + i] = abyte0[l];

		String s3 = commWithHsm(abyte2);

		if (!s3.regionMatches(0,"7:00",0,4))
			return null;
		String bcdString = s3.substring(4).trim();
		return fromBCDtoString(bcdString);
	}

	private String commWithHsm(byte abyte0[])
	{
		byte abyte1[] = new byte[1024];

		boolean flag = false;
		int j = abyte0.length;
		int lenHsmReturned;
		Integer integer = new Integer(j / 256);
		Integer integer1 = new Integer(j % 256);

		byte abyte2[] = new byte[j + 2];
		abyte2[0] = integer.byteValue();
		abyte2[1] = integer1.byteValue();
		for(int k = 0; k < j; k++)
			abyte2[k + 2] = abyte0[k];

		if (!connectHsm())
			return null;
		try
		{
			dataSendToSck.write(abyte2);
			dataSendToSck.flush();
		}
		catch(IOException _ex)
		{
			System.out.println("write socket error");
		}
		try
		{
			byte	len1;
			byte	len2;
			len1 = dataRecvFromSck.readByte();
			len2 = dataRecvFromSck.readByte();
			lenHsmReturned = dataRecvFromSck.read(abyte1);
		}
		catch(IOException _ex)
		{
			closeHsm();
			System.out.println("read socket error");
			_ex.printStackTrace();
		}
		
		closeHsm();
		
		String s1 = new String(abyte1);		
		return s1.trim();
	}

	private String commWithHsmFor34Cmd(byte abyte0[])
	{
		byte abyte1[] = new byte[1024];

		boolean flag = false;
		int j = abyte0.length;
		int lenHsmReturned = 0;
		Integer integer = new Integer(j / 256);
		Integer integer1 = new Integer(j % 256);

		byte abyte2[] = new byte[j + 2];
		abyte2[0] = integer.byteValue();
		abyte2[1] = integer1.byteValue();
		for(int k = 0; k < j; k++)
			abyte2[k + 2] = abyte0[k];

		if (!connectHsm())
			return null;
		
		try
		{
			dataSendToSck.write(abyte2);
			dataSendToSck.flush();
		}
		catch(IOException _ex)
		{
			System.out.println("write socket error");
		}
		try
		{
			byte	len1;
			byte	len2;
			len1 = dataRecvFromSck.readByte();
			len2 = dataRecvFromSck.readByte();
			lenHsmReturned = dataRecvFromSck.read(abyte1);
			//Integer tmpInt = new Integer(lenHsmReturned);
			//System.out.println("lenHsmReturned = [" + tmpInt.toString() + "]");
		}
		catch(IOException _ex)
		{
			closeHsm();
			System.out.println("read socket error");
			_ex.printStackTrace();
		}
		closeHsm();
		
		String hsmResBuf = new String(abyte1);

		if (!hsmResBuf.regionMatches(0,"3500",0,4))
		{
			System.out.println("head not = 3500!");
			return null;
		}
		
		String tmpBuf = hsmResBuf.substring(4,8);
		int vkLen = Integer.valueOf(tmpBuf).intValue();
		
		if (vkLen + 8 + 6 + 128 > lenHsmReturned) // PK不足1024位
		{
			System.out.println("not enough length!");
			return null;
		}
		
		char tmpStrBuf[] = new char[256];
		int  tmp; 
		int i = vkLen + 8 + 6;	
		for (int k = 0; k < 128;k++)
		{
			tmp = ((abyte1[i+k] >> 4) & 0x0F);
			tmpStrBuf[k*2] = formBcdChar(tmp);
			tmp = abyte1[i+k] & 0x0F;
			tmpStrBuf[k*2+1] = formBcdChar(tmp);
		}
		String pk = new String(tmpStrBuf);
		return	pk;
	}

	char formBcdChar(int tmp)
	{
		switch (tmp)
		{
			case 0:
				return('0');
			case 1:
				return('1');
			case 2:
				return('2');
			case 3:
				return('3');
			case 4:
				return('4');
			case 5:
				return('5');
			case 6:
				return('6');
			case 7:
				return('7');
			case 8:
				return('8');
			case 9:
				return('9');
			case 10:
				return('A');
			case 11:
				return('B');
			case 12:
				return('C');
			case 13:
				return('D');
			case 14:
				return('E');
			case 15:
				return('F');
		}
		return '\0';
	}
	private int formBin(char c, char c1)
	{
		int i = Character.digit(c, 16);
		int j = Character.digit(c1, 16);
		return i * 16 + j;
	}
	
	private byte[] transBcdHexToBin(String s1)
	{
		int i = s1.length();
		char ac[] = new char[i];
		byte abyte0[] = new byte[i / 2];
		s1.getChars(0, i, ac, 0);
		int j = 0;
		for(int k = 0; k < i; k++)
		{
			Integer integer = new Integer(formBin(ac[k], ac[k + 1]));
			abyte0[j] = integer.byteValue();
			j++;
			k++;
		}

		return abyte0;
	}
	

	/* 以下函数从 UnionBrowserRSAMDL中拷贝 */
	// 这个函数是提供给Browser的函数，要保持与Browser端一致
	// 加密串的拼法如下：Data1 = (2位EPin长度 + EPin + 2位AccNo长度 + AccNo)
	// (2位Data1长度 + Data1 + 随机数)构成了加密数据
	public String PkEncryptEPin(String RSAPK,String EPin, String AccNo)
	{
		BigInteger e;
		BigInteger n;
		BigInteger in;
		int pinMaxLen = 128;
		byte pinBlock[];

		e = new BigInteger("65537");

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

	public String fromBCDtoString(String bcdString){
		byte[] tb = new byte[12];
				for (int i=0;i<bcdString.length()/2 ;i++ ){
					tb[i] = (byte)Integer.parseInt(bcdString.substring(i*2,i*2+2),16);
				}
		String newString = new String (tb);
		return newString;
	}
    
    public String transAPin(String APinProByPK) {
        
        if (APinProByPK == null)
            return null;

        String hsmCmdBuf = "89" + vkIndex;
        int i = hsmCmdBuf.length();
        byte abyte1[] = hsmCmdBuf.getBytes();

        byte abyte0[] = transBcdHexToBin(APinProByPK);
        int j = abyte0.length;
        byte abyte2[] = new byte[i + j];
        for (int k = 0; k < i; k++)
            abyte2[k] = abyte1[k];
        for (int l = 0; l < j; l++)
            abyte2[l + i] = abyte0[l];

        String s3 = commWithHsm(abyte2);
        
        if (!s3.regionMatches(0, "8:00", 0, 4))
            return null;
        String bcdString = s3.substring(4).trim();
        return fromBCDtoString(bcdString);
    }

	/* End of Copy from UnionBrowserRSAMDL */

	public static void main(String[] args) {
		UnionIBWebServerMDL mdl = new UnionIBWebServerMDL("168.3.21.106", 29102);
		System.out.println(mdl.getPVK());
	}
}
