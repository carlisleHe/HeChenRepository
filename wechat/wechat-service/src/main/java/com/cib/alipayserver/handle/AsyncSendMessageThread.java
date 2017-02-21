package com.cib.alipayserver.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cib.alipay.post.AlipayPostUtils;
import com.cib.alipay.resp.Resp;
/**
 * 异步线程，用于处理消息的发送
 * @author wot_xuzhenzhou
 *
 */
public class AsyncSendMessageThread implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(AsyncSendMessageThread.class);
	private Resp resp;
	public AsyncSendMessageThread(Resp resp){
		this.resp = resp;
	}
	@Override
	public void run() {
		try{
			AlipayPostUtils.postCustomerMessageByPool(resp);
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
		
	}
	

}
