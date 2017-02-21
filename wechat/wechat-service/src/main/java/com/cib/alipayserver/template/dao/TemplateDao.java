package com.cib.alipayserver.template.dao;

import java.util.List;

import com.cib.alipayserver.template.model.Template;
import com.intensoft.dao.GenericDao;

public interface TemplateDao extends GenericDao<Template, String> {
	
	List<Template> queryAll();

}
