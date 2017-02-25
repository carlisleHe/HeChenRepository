package com.newland.alipayService.parse.impl;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.intensoft.exception.locale.ThrowableTranslate;
import com.newland.alipayService.parse.ErrMsgFormat;
import com.newland.alipayService.parse.ErrMsgParseModel;
import com.newland.base.common.AppExCode;
import com.newland.base.parser.JAXBUtils;
/**
 * 
 * @Description: 异常信息format 实现
 * @ClassName:ErrMsgFormatImpl 
 * @author:xuzz
 * @date:2014-6-19
 */
@Service("errMsgFormat")
public class ErrMsgFormatImpl implements ErrMsgFormat{
	@Resource(name = "errTrans")
	private ThrowableTranslate errTrans;

//	@Override
//	public String formatToJSON(Exception e,Locale locale) throws MapperException {
//		ErrMsgParseModel err = new ErrMsgParseModel();
//		if(e instanceof AppRTException){
//			err.setErrCode(((AppRTException)e).getCode());
//			err.setErrMsg(errTrans.getDescription(e, locale));
//		}
//		else if(e instanceof AppBizException){
//			err.setErrCode(((AppBizException)e).getCode());
//			err.setErrMsg(errTrans.getDescription(e, locale));
//		}
//		else{
//			err.setErrCode(AppExCode.UNKNOWN);
//			err.setErrMsg(errTrans.getDescription(e, locale));
//		}
//		return JSONMapper.toJSON(err).render(false);
//	}

	@Override
	public String formatToXML(Exception e,Locale locale) throws Exception {
		ErrMsgParseModel err = new ErrMsgParseModel();
		if(e instanceof AppRTException){
			err.setErrCode(((AppRTException)e).getCode());
			err.setErrMsg(errTrans.getDescription(e, locale));
		}
		else if(e instanceof AppBizException){
			err.setErrCode(((AppBizException)e).getCode());
			err.setErrMsg(errTrans.getDescription(e, locale));
		}
		else{
			err.setErrCode(AppExCode.UNKNOWN);
			err.setErrMsg(errTrans.getDescription(e, locale));
		}
		return JAXBUtils.convertToXml(err);
	}

}
