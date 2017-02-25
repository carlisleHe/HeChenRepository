package com.newland.alipayService.crm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.alipayService.crm.dao.CRMPromptDao;
import com.newland.alipayService.crm.model.CRMPrompt;
import com.newland.alipayService.crm.service.CRMPromptService;
/**
 * 
 * @Description: 客户营销service
 * @ClassName:CRMPromptServiceImpl 
 * @author:xuzz
 * @date:2015-2-9
 */
@Service("crmPromptService")
public class CRMPromptServiceImpl implements CRMPromptService{
	@Resource(name = "crmPromptDao")
	private CRMPromptDao crmPromptDao;

	@Override
	public CRMPrompt saveCRMPrompt(CRMPrompt crmPrompt) {
		return crmPromptDao.save(crmPrompt);
	}

}
