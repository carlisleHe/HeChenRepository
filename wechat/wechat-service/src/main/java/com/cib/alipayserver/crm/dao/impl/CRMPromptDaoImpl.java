package com.cib.alipayserver.crm.dao.impl;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.crm.dao.CRMPromptDao;
import com.cib.alipayserver.crm.model.CRMPrompt;
/**
 * 
 * @Description: 客户营销dao
 * @ClassName:CRMPromptDaoImpl 
 * @author:xuzz
 * @date:2015-2-9
 */
@Service("crmPromptDao")
public class CRMPromptDaoImpl extends AlipayBaseDao<CRMPrompt, Integer> implements CRMPromptDao{

}
