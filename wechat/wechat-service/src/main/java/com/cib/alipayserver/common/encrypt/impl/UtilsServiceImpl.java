package com.cib.alipayserver.common.encrypt.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.common.encrypt.HsmAdapter;
import com.cib.alipayserver.common.encrypt.UtilsService;
import com.intensoft.exception.AppBizException;

@Service("utilsService")
public class UtilsServiceImpl implements UtilsService {
	
	@Resource (name = "hsmAdapter")
	private HsmAdapter hsmAdapter;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public String encriptAccountPwd(String signPwd) throws AppBizException {
		if (StringUtils.isBlank(signPwd)){
			return "";
		}else{
			if (signPwd.length() < 8){
				throw new AppBizException("","密码控件未完全下载");
			}
			String pwd_tmp = null;
			try{
				pwd_tmp = hsmAdapter.transAcctPwd(signPwd, "");
			}catch(Throwable e){
				logger.error("加密机错误：", e);
			}
			if (StringUtils.isBlank(pwd_tmp)){
				logger.error("密码加密失败, 加密串:[" + signPwd + "]");
				throw new AppBizException("", "系统忙，请稍后再试");
			}
			return pwd_tmp;
		}
	}

}
