package com.cib.alipayserver.template.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
/**
 * 动户通知消息模板
 * @author dvlp
 *
 */
public class MsgTemplate implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String templateId;
	
	private String topcolor;
	
	private String url;
	
	private List<ColumnDefine> columns;
	
	private Map<String, Object> addition = new HashMap<String, Object>();
	
	public void putAddition(String keyword, String content){
		this.addition.put(keyword, content);
	}
	
	public Map<String, Object> getAddition(){
		return this.addition;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ColumnDefine> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefine> columns) {
		this.columns = columns;
	}
	
	public void addColumn(ColumnDefine define){
		if (CollectionUtils.isEmpty(columns)) columns = new ArrayList<ColumnDefine>();
		columns.add(define);
	}

	public void setAddition(Map<String, Object> addition) {
		this.addition = addition;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
