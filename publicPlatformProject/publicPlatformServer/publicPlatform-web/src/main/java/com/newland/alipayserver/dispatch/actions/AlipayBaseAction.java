package com.newland.alipayserver.dispatch.actions;

import org.springframework.beans.factory.annotation.Value;

import com.newland.alipay.req.Req;
import com.newland.alipayService.dispatch.AbstractAction;
import com.newland.alipayService.dispatch.ActionResult;

public abstract class AlipayBaseAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4668890002593290648L;
	
	@Value("@[alipay_bind_card_url]")
	private String bindCardUrl;
	
	@Value("@[mb_index_url]")
	public String mbIndexUrl;
	
	@Value("@[mb_trans_url]")
	public String transUrl;

	public  ActionResult authorizationActionResult(Req req,String operationType,String beforeMsg,String urlMsg,String afterMsg) throws Exception{
		
		String content= beforeMsg+"<a href=\""+bindCardUrl+"\">"+urlMsg+"</a>"+afterMsg;
		return generalTextResponse(req, content);
	}

}
