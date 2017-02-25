package com.newland.alipayService.env;

public class MobileBankEnviroment {

	public String PARAM_SEARCHTYPE = "searchtype";

	public String PARAM_LATITUDE = "latitude";

	public String PARAM_LANGITUDE = "longitude";

	public String SEARCH_TYPE_NODE = "1";
	
	public String SEARCH_TYPE_ATM = "2";

	public String SEARCH_TYPE_MRCH = "3";

	/**
	 * 手机银行提供最近网点查询功能的URI地址
	 */
	private String mobileBankLocaitonURI;


	public String getMobileBankLocaitonURI() {
		return mobileBankLocaitonURI;
	}

	public void setMobileBankLocaitonURI(String mobileBankLocaitonURI) {
		this.mobileBankLocaitonURI = mobileBankLocaitonURI;
	}

}
