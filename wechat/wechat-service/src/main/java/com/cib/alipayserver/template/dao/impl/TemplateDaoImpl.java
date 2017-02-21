package com.cib.alipayserver.template.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.template.dao.TemplateDao;
import com.cib.alipayserver.template.model.Template;

@Service("templateDao")
public class TemplateDaoImpl extends AlipayBaseDao<Template, String> implements TemplateDao  {

	@Override
	public List<Template> queryAll() {
		return this.findAll();
	}

}
