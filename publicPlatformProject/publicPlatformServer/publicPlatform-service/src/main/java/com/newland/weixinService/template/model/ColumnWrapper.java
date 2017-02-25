package com.newland.weixinService.template.model;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.newland.wechat.post.model.DataWrapper;
/**
 * 模板字段包装器，用于将字段转为json格式
 * @author Shizn
 * 目前只对日期、金额进行格式化处理，默认采用文本格式化器进行处理;
 */
@SuppressWarnings("rawtypes")
public class ColumnWrapper implements DataWrapper {
	/**
	 * 字段列表
	 */
	private List<ColumnDefine> columns;
	/**
	 * 源数据提供类
	 */
	private Object source;
	
	private final Map<String, Object> other;
	
	public ColumnWrapper(List<ColumnDefine> columns, Object source, Map<String, Object> other){
		this.columns = columns;
		this.source = source;
		this.other = other;
	}

	
	public ColumnWrapper(List<ColumnDefine> columns, Object source){
		this(columns, source, new HashMap<String, Object>());
	}

	@Override
	public Map<Object, Object> toMap() throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (CollectionUtils.isEmpty(columns)) return map;
		for (int i = 0 ; i < columns.size() ; i++){
			Map<Object, Object> json = new HashMap<Object, Object>();
			ColumnDefine column = columns.get(i);
			Object[] values = null;
			MessageFormat format = new MessageFormat(column.getValue());
			values = getProperty(source, column.getPropertyNames());

			StringBuilder sb = new StringBuilder();
			sb.append("values: [");
			for (Object obj : values) {
				// 增加非空鉴别，避免出现NullPointer错误
				if (obj != null)
					sb.append(obj.toString()).append(" ");
				else
					sb.append(" ");
			}
			sb.append("] propertyNames: [");
			for (String str : column.getPropertyNames()) {
				sb.append(str).append(" ");
			}
			sb.append("],source:[");
			if (source instanceof HashMap) {
				sb.append(source);
			}
			sb.append("]");

			try {
				json.put("value", ((values == null) ? column.getValue()
						: format.format(values)));
			} catch (Exception e) {
				throw new Exception("格式化内容出错: " + e.getMessage()
						+ sb.toString() + column.getValue(), e);
			}
			json.put("color", column.getColor());
			map.put(column.getKey(), json);
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	private Object[] getProperty(Object source, String[] propertyNames) {
		if (propertyNames == null || propertyNames.length == 0) return new Object[0];
		List values = new ArrayList();
		if (source instanceof Map){
			for (String prop:propertyNames){
				Map map = (Map)source;
				Object obj = map.get(prop);
				if (obj == null){
					obj = this.other.get(prop);
				}
				values.add(obj);
				
			}
		}else{
			try {
				for (String prop:propertyNames){
					Field field = source.getClass().getDeclaredField(prop);
					if (field == null) this.other.get(prop);
					boolean access = field.isAccessible();
					field.setAccessible(true);
					Object obj = field.get(source);
					field.setAccessible(access);
					values.add(obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} 
		}
		return values.toArray(new Object[values.size()]);
	}


	public List<ColumnDefine> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefine> columns) {
		this.columns = columns;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

}
