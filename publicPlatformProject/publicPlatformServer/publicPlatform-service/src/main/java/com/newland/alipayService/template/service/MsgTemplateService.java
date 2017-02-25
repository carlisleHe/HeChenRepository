package com.newland.alipayService.template.service;

import com.newland.alipayService.template.model.MsgTemplate;

public interface MsgTemplateService {
	
	MsgTemplate findById(String id);

	MsgTemplate findById(String templateId, String alias);

}
