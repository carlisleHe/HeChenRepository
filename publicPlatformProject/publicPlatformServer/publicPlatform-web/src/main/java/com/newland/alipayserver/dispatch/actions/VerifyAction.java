package com.newland.alipayserver.dispatch.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.alipay.req.Req;
import com.newland.alipayService.dispatch.AbstractAction;
import com.newland.alipayService.dispatch.ActionResult;
import com.newland.alipayService.session.AlipaySession;
/**
 * 激活校验
 * @author wot_xuzhenzhou
 *
 */
public class VerifyAction extends AbstractAction{
	Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;

	@Override
	public ActionResult execute(Req req, AlipaySession sess) throws Exception {
		return generalActiveResponse();

	}
}
