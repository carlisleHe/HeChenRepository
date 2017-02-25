package com.newland.weixinService.notify.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.intensoft.exception.AppBizException;
import com.newland.base.common.Const;
import com.newland.wechat.post.WeixinPostUtils;
import com.newland.wechat.post.model.NotifyMessage;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.notify.CustNotifyService;
import com.newland.weixinService.template.model.ColumnWrapper;
import com.newland.weixinService.template.model.MsgTemplate;
import com.newland.weixinService.template.service.MsgTemplateService;

/**
 * 动户通知接口
 * 
 * @author Shizn
 * 
 */
// @Service("custNotifyService")
public class CustNotifyServiceImpl implements CustNotifyService {

	@Value("@[wx_template_msg_send]")
	private String postUrl;
	@Resource(name = "msgTemplateService")
	private MsgTemplateService templateService;
	@Resource(name = "accessTokenService")
	private AccessTokenService accessTokenService;

	@Override
	public void notify(String openId, String templateId,
			Map<String, Object> source) throws AppBizException {
		this.notify(openId, templateId, Const.DEFAULT_MSG_TEMPLATE, source);
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	@Override
	public void notify(String openId, String templateId, String alias,
			Map<String, Object> source) throws AppBizException {
		Assert.notNull(openId, "消息模板不能为空");
		Assert.notNull(templateId, "模板Id不能为空");
		Assert.notNull(source, "数据源对象不能为空");
		Assert.notNull(alias, "模板别名不能为空");
		MsgTemplate template = this.templateService.findById(templateId, alias);
		if (template == null)
			throw new AppBizException("10001", "模板文件不存在");
		ColumnWrapper wrapper = new ColumnWrapper(template.getColumns(), source, template.getAddition());
		NotifyMessage msg = new NotifyMessage();
		msg.setTemplateId(template.getTemplateId());
		msg.setTopcolor(template.getTopcolor());
		msg.setTouser(openId);
		String url = template.getUrl();
		if(StringUtils.isBlank(url)){
			msg.setUrl("");
		}else{
			msg.setUrl(url);
		}
		try {
			WeixinPostUtils.sendNotifyMessage(postUrl,
					accessTokenService.getAccessToken(), msg, wrapper);
		} catch (Exception e) {
			// 对于发送失败的消息自动重发一次
			try {
				WeixinPostUtils.sendNotifyMessage(postUrl,
						accessTokenService.getAccessToken(), msg, wrapper);
			} catch (Exception ee) {
				throw new AppBizException("10002", ee.getMessage());
				// TODO 发送失败的动户消息是否应当进入重发机制，稍后重新发送
			}
		}
	}

}
