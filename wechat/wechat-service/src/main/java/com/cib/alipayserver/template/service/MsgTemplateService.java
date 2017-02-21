package com.cib.alipayserver.template.service;

import com.cib.alipayserver.template.model.MsgTemplate;

public interface MsgTemplateService {
	
	MsgTemplate findById(String id);

	MsgTemplate findById(String templateId, String alias);

}
