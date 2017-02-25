package com.newland.alipayService.template.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.newland.alipayService.base.AlipayBaseDao;
import com.newland.alipayService.template.dao.TemplateDao;
import com.newland.alipayService.template.model.Template;

@Service("templateDao")
public class TemplateDaoImpl extends AlipayBaseDao<Template, String> implements TemplateDao  {

	@Override
	public List<Template> queryAll() {
		return this.findAll();
	}

}
