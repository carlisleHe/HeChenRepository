package com.newland.wechatServer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;

import com.cib.alipayserver.common.Const;

/**
 * Base64 Encoding (根据URL编码需要对最后两位字符做了改进)
 * 
 */
public class URLBase64 {
	private static final char[] S_BASE64CHAR = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '.', '_' };
	private static final char S_BASE64PAD = '*';
	private static final byte[] S_DECODETABLE = { -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, 62, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
			-1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1,
			-1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
			40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };

	private static int decode0(char[] ibuf, byte[] obuf, int wp) {
		int outlen = 3;
		if (ibuf[3] == S_BASE64PAD)
			outlen = 2;
		if (ibuf[2] == S_BASE64PAD)
			outlen = 1;
		int b0 = S_DECODETABLE[ibuf[0]];
		int b1 = S_DECODETABLE[ibuf[1]];
		int b2 = S_DECODETABLE[ibuf[2]];
		int b3 = S_DECODETABLE[ibuf[3]];
		switch (outlen) {
		case 1:
			obuf[wp] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			return 1;
		case 2:
			obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			obuf[wp] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
			return 2;
		case 3:
			obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			obuf[wp++] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
			obuf[wp] = (byte) (b2 << 6 & 0xc0 | b3 & 0x3f);
			return 3;
		default:
			throw new RuntimeException("Internal Errror");
		}
	}

	/**
     *
     */
	public static byte[] decode(char[] data, int off, int len) {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[len / 4 * 3 + 3];
		int obufcount = 0;
		for (int i = off; i < off + len; i++) {
			char ch = data[i];
			if (ch == S_BASE64PAD || ch < S_DECODETABLE.length
					&& S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					obufcount += decode0(ibuf, obuf, obufcount);
				}
			}
		}
		if (obufcount == obuf.length)
			return obuf;
		byte[] ret = new byte[obufcount];
		System.arraycopy(obuf, 0, ret, 0, obufcount);
		return ret;
	}

	/**
     *
     */
	public static byte[] decode(String data) {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[data.length() / 4 * 3 + 3];
		int obufcount = 0;
		for (int i = 0; i < data.length(); i++) {
			char ch = data.charAt(i);
			if (ch == S_BASE64PAD || ch < S_DECODETABLE.length
					&& S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					obufcount += decode0(ibuf, obuf, obufcount);
				}
			}
		}
		if (obufcount == obuf.length)
			return obuf;
		byte[] ret = new byte[obufcount];
		System.arraycopy(obuf, 0, ret, 0, obufcount);
		return ret;
	}

	/**
     *
     */
	public static void decode(char[] data, int off, int len,
			OutputStream ostream) throws IOException {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[3];
		for (int i = off; i < off + len; i++) {
			char ch = data[i];
			if (ch == S_BASE64PAD || ch < S_DECODETABLE.length
					&& S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					int obufcount = decode0(ibuf, obuf, 0);
					ostream.write(obuf, 0, obufcount);
				}
			}
		}
	}

	/**
     *
     */
	public static void decode(String data, OutputStream ostream)
			throws IOException {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[3];
		for (int i = 0; i < data.length(); i++) {
			char ch = data.charAt(i);
			if (ch == S_BASE64PAD || ch < S_DECODETABLE.length
					&& S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					int obufcount = decode0(ibuf, obuf, 0);
					ostream.write(obuf, 0, obufcount);
				}
			}
		}
	}

	/**
	 * Returns base64 representation of specified byte array.
	 */
	public static String encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * Returns base64 representation of specified byte array.
	 */
	public static String encode(byte[] data, int off, int len) {
		if (len <= 0)
			return "";
		char[] out = new char[len / 3 * 4 + 4];
		int rindex = off;
		int windex = 0;
		int rest = len - off;
		while (rest >= 3) {
			int i = ((data[rindex] & 0xff) << 16)
					+ ((data[rindex + 1] & 0xff) << 8)
					+ (data[rindex + 2] & 0xff);
			out[windex++] = S_BASE64CHAR[i >> 18];
			out[windex++] = S_BASE64CHAR[(i >> 12) & 0x3f];
			out[windex++] = S_BASE64CHAR[(i >> 6) & 0x3f];
			out[windex++] = S_BASE64CHAR[i & 0x3f];
			rindex += 3;
			rest -= 3;
		}
		if (rest == 1) {
			int i = data[rindex] & 0xff;
			out[windex++] = S_BASE64CHAR[i >> 2];
			out[windex++] = S_BASE64CHAR[(i << 4) & 0x3f];
			out[windex++] = S_BASE64PAD;
			out[windex++] = S_BASE64PAD;
		} else if (rest == 2) {
			int i = ((data[rindex] & 0xff) << 8) + (data[rindex + 1] & 0xff);
			out[windex++] = S_BASE64CHAR[i >> 10];
			out[windex++] = S_BASE64CHAR[(i >> 4) & 0x3f];
			out[windex++] = S_BASE64CHAR[(i << 2) & 0x3f];
			out[windex++] = S_BASE64PAD;
		}
		return new String(out, 0, windex);
	}

	/**
	 * Outputs base64 representation of the specified byte array to a byte
	 * stream.
	 */
	public static void encode(byte[] data, int off, int len,
			OutputStream ostream) throws IOException {
		if (len <= 0)
			return;
		byte[] out = new byte[4];
		int rindex = off;
		int rest = len - off;
		while (rest >= 3) {
			int i = ((data[rindex] & 0xff) << 16)
					+ ((data[rindex + 1] & 0xff) << 8)
					+ (data[rindex + 2] & 0xff);
			out[0] = (byte) S_BASE64CHAR[i >> 18];
			out[1] = (byte) S_BASE64CHAR[(i >> 12) & 0x3f];
			out[2] = (byte) S_BASE64CHAR[(i >> 6) & 0x3f];
			out[3] = (byte) S_BASE64CHAR[i & 0x3f];
			ostream.write(out, 0, 4);
			rindex += 3;
			rest -= 3;
		}
		if (rest == 1) {
			int i = data[rindex] & 0xff;
			out[0] = (byte) S_BASE64CHAR[i >> 2];
			out[1] = (byte) S_BASE64CHAR[(i << 4) & 0x3f];
			out[2] = (byte) S_BASE64PAD;
			out[3] = (byte) S_BASE64PAD;
			ostream.write(out, 0, 4);
		} else if (rest == 2) {
			int i = ((data[rindex] & 0xff) << 8) + (data[rindex + 1] & 0xff);
			out[0] = (byte) S_BASE64CHAR[i >> 10];
			out[1] = (byte) S_BASE64CHAR[(i >> 4) & 0x3f];
			out[2] = (byte) S_BASE64CHAR[(i << 2) & 0x3f];
			out[3] = (byte) S_BASE64PAD;
			ostream.write(out, 0, 4);
		}
	}

	/**
	 * Outputs base64 representation of the specified byte array to a character
	 * stream.
	 */
	public static void encode(byte[] data, int off, int len, Writer writer)
			throws IOException {
		if (len <= 0)
			return;
		char[] out = new char[4];
		int rindex = off;
		int rest = len - off;
		int output = 0;
		while (rest >= 3) {
			int i = ((data[rindex] & 0xff) << 16)
					+ ((data[rindex + 1] & 0xff) << 8)
					+ (data[rindex + 2] & 0xff);
			out[0] = S_BASE64CHAR[i >> 18];
			out[1] = S_BASE64CHAR[(i >> 12) & 0x3f];
			out[2] = S_BASE64CHAR[(i >> 6) & 0x3f];
			out[3] = S_BASE64CHAR[i & 0x3f];
			writer.write(out, 0, 4);
			rindex += 3;
			rest -= 3;
			output += 4;
			if (output % 76 == 0)
				writer.write("\n");
		}
		if (rest == 1) {
			int i = data[rindex] & 0xff;
			out[0] = S_BASE64CHAR[i >> 2];
			out[1] = S_BASE64CHAR[(i << 4) & 0x3f];
			out[2] = S_BASE64PAD;
			out[3] = S_BASE64PAD;
			writer.write(out, 0, 4);
		} else if (rest == 2) {
			int i = ((data[rindex] & 0xff) << 8) + (data[rindex + 1] & 0xff);
			out[0] = S_BASE64CHAR[i >> 10];
			out[1] = S_BASE64CHAR[(i >> 4) & 0x3f];
			out[2] = S_BASE64CHAR[(i << 2) & 0x3f];
			out[3] = S_BASE64PAD;
			writer.write(out, 0, 4);
		}
	}

	public static String encodeStr(String str, String encode) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		try {
			return encode(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String decodeStr(String str, String encode) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		try {
			return new String(decode(str), encode);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static void decodeUrlParams(Map session, String str) {
		String parameters = decodeStr(str, HTTP.UTF_8);
		String params[] = parameters.split("&");
		for (String param : params) {
			int i = param.indexOf('=');
			if (i > 0 && i < param.length()) {
				session.put(param.substring(0, i), param.substring(i + 1));
			}
		}
	}


	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "acct_no=622909216987458926&cert_type=1&cert_no=123456&cust_name=张三";
		System.out.println(s);
		//decodeStr
		String temp = encodeStr(s, HTTP.UTF_8);
		System.out.println(temp);
		s =  decodeStr(temp, HTTP.UTF_8);
		System.out.println(s);
		Map test = new HashMap<String, String>();
		decodeUrlParams(test, temp);
		
		StringBuilder sb = new StringBuilder();
		String result = sb.append("openId=").append("wxe26eb3240c365a5c")
				.append("&").append("appId=").append(Const.WEIXIN_PREFIX)
				.append("&").append("OperationType=")
				.append(Const.OPER_TYPE_MAN).toString();
		
		System.out.println(encodeStr(result,HTTP.UTF_8));
	}
}
