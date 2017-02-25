package com.newland.alipayService.template.dao;

import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.alipayService.template.model.Template;

public interface TemplateDao extends GenericDao<Template, String> {
	
	List<Template> queryAll();

}
