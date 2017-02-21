package com.cib.alipayserver.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cib.alipay.parser.MsgType;
import com.cib.alipay.post.AlipayPostUtils;
import com.cib.alipay.resp.TextResp;
import com.cib.alipayserver.event.SessionTimeoutEvent;
import com.cib.alipayserver.session.AlipaySession;

@Component
public class CustomerListener implements ApplicationListener<ApplicationEvent> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		if (event instanceof SessionTimeoutEvent) {
			SessionTimeoutEvent sessionEvent = (SessionTimeoutEvent) event;
			AlipaySession session = sessionEvent.getSession();
			logger.debug("超时用户openId:【" + session.getFromUserId() + "]");
			Boolean isCustomerService = session.isCustomServiceFlag();
			if (isCustomerService) {
				try {
					AlipayPostUtils
							.postCustomerMessageByPool(convertToJsonTextResp(session));

				} catch (Exception e) {
					logger.error("客服消息接收异常", e);
				}
			}
			session.setCustomServiceFlag(false);
		}
	}

	protected TextResp convertToJsonTextResp(AlipaySession session) {
		TextResp msg = new TextResp();
		msg.setFromUserName(session.getToUserId());
		msg.setCreateTime(System.currentTimeMillis());
		msg.setMsgType(MsgType.text.name());
		msg.setToUserName(session.getFromUserId());
		msg.getText().setContent("由于您长时间没有上行信息，已退出人工服务，接下来由小兴继续为您服务");
		return msg;
	}

}
