package com.newland.alipayService.common.encrypt;

import com.intensoft.exception.AppBizException;

public interface UtilsService {
	


	/**
	 * 账户密码加密
	 * @param pwd
	 * @return
	 * @throws AppBizException
	 */
	String encriptAccountPwd(String pwd) throws AppBizException;
	

	
}
