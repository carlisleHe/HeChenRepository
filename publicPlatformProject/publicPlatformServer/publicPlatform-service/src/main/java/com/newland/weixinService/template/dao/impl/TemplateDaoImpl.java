package com.newland.weixinService.template.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newland.weixinService.base.WeixinBaseDao;
import com.newland.weixinService.template.dao.TemplateDao;
import com.newland.weixinService.template.model.Template;

@Service("templateDao")
public class TemplateDaoImpl extends WeixinBaseDao<Template, String> implements TemplateDao  {

	@Override
	public List<Template> queryAll() {
		return this.findAll();
	}

}
