package com.newland.alipayService.handle;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.newland.alipay.handle.AlipayReqHandle;
import com.newland.alipay.req.Req;
import com.newland.alipay.resp.Resp;
import com.newland.alipay.resp.TextResp;
import com.newland.alipay.resp.VerifyActiveResp;
import com.newland.alipayService.dispatch.Dispatcher;
import com.newland.alipayService.session.AlipaySession;
import com.newland.alipayService.session.SessionManager;

/**
 * 微信消息处理类
 * 
 * @author dvlp
 * 
 */
@Service("alipayHandle")
public class AlipayHandleService implements AlipayReqHandle {
	@Resource(name = "sessionManager")
	private SessionManager sessionManager;

	@Resource(name = "dispatcher")
	private Dispatcher dispatcher;
	@Resource(name = "executor")
	private TaskExecutor executor;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Resp handleAlipayReq(Req req) throws Exception {
		
		Resp resp = null;
		try {
			AlipaySession sess = this.sessionManager.getSession(req, false);
			resp = dispatcher.dispatch(req, sess);
			logger.info("生成返回报文成功,消息内容[" + resp.getXMLRespContent() + "],接收用户["+ resp.getToUserName() + "],消息类型["+ resp.getMsgType().toString() + "]");
		} catch (AppBizException e) {
			resp = defaultResp(req, e.getMessage());
			logger.error("出错了", e);
		} catch (AppRTException e1) {
			resp = defaultResp(req, e1.getMessage());
			logger.error("出错了", e1);
		} catch (Throwable e2) {
			resp = defaultResp(req, "系统异常。");
			logger.error("出错了", e2);
		}
		//激活模式无需再异步发起消息响应
		if(resp instanceof VerifyActiveResp){
			return resp;
		}
		//发起异步的消息发送
		executor.execute(new AsyncSendMessageThread(resp));
		return resp;
	}

	/**
	 * 默认响应
	 * 
	 * @param req
	 * @return
	 */
	private TextResp defaultResp(Req req, String content) {
		TextResp resp = new TextResp();
		resp.setToUserName(req.getFromUserName());
		resp.setFromUserName(req.getToUserName());
		resp.getText().setContent(content);
		return resp;
	}

}
