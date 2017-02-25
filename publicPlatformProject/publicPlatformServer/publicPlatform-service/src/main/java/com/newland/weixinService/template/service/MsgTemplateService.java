package com.newland.weixinService.template.service;

import com.newland.weixinService.template.model.MsgTemplate;

public interface MsgTemplateService {
	
	MsgTemplate findById(String id);

	MsgTemplate findById(String templateId, String alias);

}
