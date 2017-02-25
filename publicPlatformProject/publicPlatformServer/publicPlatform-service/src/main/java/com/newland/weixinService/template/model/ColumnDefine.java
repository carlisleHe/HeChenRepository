package com.newland.weixinService.template.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
/**
 * 模板字段定义类
 * @author Shizn
 *
 */
public class ColumnDefine implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 字段Key
	 */
	private String key;
	/**
	 * 字段值模板
	 */
	private String value = "{0}";
	/**
	 * 需要的属性名称
	 */
	private String[] propertyNames;
	/**
	 * 文字颜色
	 */
	private String color;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String[] getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	

	
	public static void main(String[] args){
		String content = "{0}{1}{2,date,yyyyMMdd}";
		MessageFormat mf = new MessageFormat(content);
		System.err.print(mf.format(new Object[]{"shiz", "zhenng", new Date()}));
	}

	@Override
	public int hashCode() {
		int pre = 99;
		if (StringUtils.isBlank(key)) return pre;
		return pre + key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColumnDefine){
			ColumnDefine other = (ColumnDefine)obj;
			if (StringUtils.isBlank(other.key)){
				if (StringUtils.isBlank(key)) return true;
				return false;
			}else if (StringUtils.isBlank(key)){
				return false;
			}else{
				return other.key.equals(key);
			}
		}
		return false;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
