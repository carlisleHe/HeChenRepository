package com.newland.wechat.post;

import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import com.newland.base.post.JSONPopulator;

/**
 * 微信JSON解析工具类
 * @author Shizn
 *
 */
public class WeixinJsonUtils {

	/**
	 * 对象转json
	 * @param rootObject
	 * @param excludeNullProperties
	 * @return
	 * @throws Exception
	 */
	public static String convertToJson(Object rootObject, boolean excludeNullProperties) throws Exception {
		String json = JSONUtil.serialize(rootObject, null, null, false, excludeNullProperties);
		return json;
	}

	/**
	 * json 转对象
	 * @param <T>
	 * @param json
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static <T> T convertFromJson(String json, Class<T> c) throws Exception {
		Object obj = JSONUtil.deserialize(json);
		T result = c.newInstance();
		if (obj instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map map = (Map) obj;
			JSONPopulator populator = new JSONPopulator();
			populator.populateObject(result, map);
		} else {
			throw new Exception("不支持的JSON");
		}
		return result;
	}
}
