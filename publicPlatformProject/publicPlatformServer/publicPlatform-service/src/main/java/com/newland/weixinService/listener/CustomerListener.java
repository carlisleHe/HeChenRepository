package com.newland.weixinService.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.intensoft.exception.AppBizException;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.parser.MsgType;
import com.newland.wechat.post.WeixinPostUtils;
import com.newland.wechat.resp.JTextResp;
import com.newland.wechat.resp.Text;
import com.newland.weixinService.accessToken.service.AccessTokenService;
import com.newland.weixinService.event.SessionTimeoutEvent;
import com.newland.weixinService.session.WeixinSession;

@Component
public class CustomerListener implements ApplicationListener<ApplicationEvent> {

	@Resource(name = "accessTokenService")
	private AccessTokenService accessTokenService;
	
//	@Resource(name = "customService")
//	public CustomService customService;

	@Value("@[wx_customer_send]")
	private String postUrl;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		 if(event instanceof SessionTimeoutEvent) {
			     SessionTimeoutEvent sessionEvent = (SessionTimeoutEvent)event;
			     WeixinSession session  =   sessionEvent.getSession();
			     logger.debug("超时用户openId:【"+session.getFromUserId()+"]");
//			     logger.info((String) session.getValue(com.cib.weixinserver.dispatch.AbstractAction.CUSTOMER_SERVICE_FLAG));
//			     Boolean isCustomerService=session.getValue(com.cib.weixinserver.dispatch.AbstractAction.CUSTOMER_SERVICE_FLAG);	
			     logger.info(""+session.isCustomServiceFlag());
			     Boolean isCustomerService=session.isCustomServiceFlag();
					if(isCustomerService){
							// 转发微信 postCustomerMessageByPool
							try {
								WeixinPostUtils.postCustomerMessageByPool(postUrl, accessTokenService.getAccessToken(),convertToJsonTextResp(session));
						
						}catch (AppBizException e) {
							if (e.getCode().equals("42001")|| e.getCode().equals("40001")){
								logger.warn("发送客服信息时accessToken过期", e);
								this.accessTokenService.invalidateToken();
								try {
									WeixinPostUtils.postCustomerMessageByPool(postUrl, accessTokenService.getAccessToken(),convertToJsonTextResp(session));
								} catch (Exception e1) {
									logger.error("客服消息接收异常", e1);
									
								}
								
							}
							logger.error("客服消息接收异常", e);
						}
						 catch (Exception e) {
							 logger.error("客服消息接收异常", e);
							}
						}session.setCustomServiceFlag(false);
					}
	}

	protected JTextResp convertToJsonTextResp(WeixinSession session) {
		JTextResp msg = new JTextResp();
		msg.setFromUserName(session.getToUserId());
		msg.setCreateTime(System.currentTimeMillis());
		msg.setMsgType(MsgType.text);
		msg.setToUserName(session.getFromUserId());
		/* 应细武要求，更改退出的话术  mod by sunliulin  20150825 */
		//Text text = new Text("由于您长时间没有上行信息，您已退出和老师的会话，接下来由小兴继续为你服务");
		Text text = new Text("由于您长时间没有上行信息，已退出人工服务，接下来由小兴继续为您服务");
		msg.setText(text);
//		//发送1101交易,通知客服用户退出
//		CustomServiceAcct customServiceAcct=new CustomServiceAcct();
//		customServiceAcct.setAppId(WeixinApplication.getAppId());
//		customServiceAcct.setAppName("微信银行");
//		customServiceAcct.setAppUserId(session.getToUserId());
//		try {
//			customService.userExitNotifyCustom(customServiceAcct);
//		} catch (AppBizException e) {
//			logger.error("通知客服失败:"+e.getMessage());
//		}
		return msg;
	}

}
