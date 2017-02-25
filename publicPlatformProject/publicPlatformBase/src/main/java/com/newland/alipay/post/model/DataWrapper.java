package com.newland.alipay.post.model;

import java.util.Map;

/**
 * 数据包装接口
 * @author shizn
 *
 */
public interface DataWrapper {
	/**
	 * 将对象转成json字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	Map<Object, Object> toMap() throws Exception;

}
