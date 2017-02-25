package com.newland.weixinService.template.dao;

import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.weixinService.template.model.Template;

public interface TemplateDao extends GenericDao<Template, String> {
	
	List<Template> queryAll();

}
