package com.newland.weixinService.notify;

import java.util.Map;

import com.intensoft.exception.AppBizException;
/**
 * 动户通知接口
 * @author dvlp
 *
 */
public interface CustNotifyService {

	/**
	 * 动户通知
	 * @param key 消息模板
	 * @param obj 数据源对象
	 * @throws AppBizException TODO
	 */
	public void notify(String openId, String templateId,
			Map<String, Object> source) throws AppBizException;
	/**
	 * 动户通知
	 * @param openId
	 * @param templateId
	 * @param alias 特殊模板
	 * @param source
	 * @throws AppBizException
	 */
	public void notify(String openId, String templateId,String alias,
			Map<String, Object> source) throws AppBizException;
}
