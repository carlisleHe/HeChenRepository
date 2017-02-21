/*
 * 功能：异常码处理
 * 
 * 类名：ExceptionDispose.java
 *
 *  Ver     变更日期   	 修改人   	修改说明
 * ─────────────────────────————————————————
 * 	V1.0  	2014-6-6 	chenxb    初版
 *
 * Copyright (c) 2006, 2008 NewlandComputer All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.cib.alipayserver.txn;


import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.common.AppExCode;
import com.cib.alipayserver.common.Const;
import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.intensoft.exception.locale.ThrowableTranslate;
import com.intensoft.file.exception.AppExcCode;

/**
 * 异常码处理
 * 
 * @author chenxb
 * @version Ver 1.0 2014-6-6
 * @since ExceptionDispose.java Ver 1.0
 */
@Service("openbankExceptionDispose")
public class ExceptionDispose {
	@Resource(name = "errTrans")
	private ThrowableTranslate throwableTranslate;
	public  void exceptionTrans(AppBizException e) throws AppBizException {
		StringBuffer errCode = new StringBuffer(e.getCode().replaceAll("[a-zA-Z]", ""));
		replenishErrCode(errCode);
		//做一下国际化
		String message = throwableTranslate.getDescription(e, Locale.CHINA);
		if(message==null){
			message = e.getMessage();
		}
		AppBizException e1 = new AppBizException(errCode.toString(), message,e);
		throw e1;
	}

	public  void exceptionTrans(AppRTException e) throws AppBizException {
		StringBuffer errCode;
		if(e.getCode().equals(AppExcCode.FILEPARSER_DEFAULT_ERR)){
			errCode =new StringBuffer(AppExCode.FILE_NOT_FOUND);
		}else{
		    errCode = new StringBuffer(e.getCode().replaceAll("[a-zA-Z]", ""));
		}
		replenishErrCode(errCode);
		AppRTException e1 = new AppRTException(errCode.toString(), e.getMessage(),e);
		throw e1;
	}
	/**
	 * 
	 * 功能：填充errCode到5位
	 * @param errCode
	 */
	private static void replenishErrCode(StringBuffer errCode) {
		if(errCode.length()<=Const.ERR_CODE_LENGTH-Const.ERR_INCOMING_CODE_PREFIX.length()){
			int mustAddlength = Const.ERR_CODE_LENGTH-Const.ERR_INCOMING_CODE_PREFIX.length()-errCode.length();
			for (int i = 0; i < mustAddlength; i++) {
				errCode.insert(0, "0");
			}
			errCode.insert(0, Const.ERR_INCOMING_CODE_PREFIX);
		}
	}
	public static void main(String[] args) throws AppBizException {
		//ExceptionDispose.exceptionTrans(new AppRTException("d0011", "111111111"));
	}
}
