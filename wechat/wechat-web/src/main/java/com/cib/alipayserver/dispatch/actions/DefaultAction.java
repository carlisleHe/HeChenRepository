package com.cib.alipayserver.dispatch.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cib.alipay.req.Req;
import com.newland.wechatServer.dispatch.AbstractAction;
import com.newland.wechatServer.dispatch.ActionResult;
import com.newland.wechatServer.session.AlipaySession;

/**
 * 默认处理类
 * 
 * @author dvlp
 * 
 */
public class DefaultAction extends AbstractAction {
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 微信不能识别默认的回复
	 */
	public static final String DEFAULT_RESPONSE_CONTENT = "亲，您输入的信息暂无法识别。";
	private static final long serialVersionUID = 1L;

	@Override
	public ActionResult execute(Req req, AlipaySession sess) throws Exception {
		return generalTextResponse(req, DEFAULT_RESPONSE_CONTENT);

	}

}
