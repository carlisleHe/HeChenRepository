package com.cib.alipayserver.crm.service;

import com.cib.alipayserver.crm.model.CRMPrompt;
/**
 * 
 * @Description:客户营销关系service
 * @ClassName:CRMPromptService 
 * @author:xuzz
 * @date:2015-2-9
 */
public interface CRMPromptService {
	/**
	 * 保存客户营销信息
	 * @param crmPrompt
	 * @return
	 */
	public CRMPrompt saveCRMPrompt(CRMPrompt crmPrompt);

}
