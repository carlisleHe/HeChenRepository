package com.newland.weixinService.handle;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.newland.wechat.env.WeixinApplication;
import com.newland.wechat.handle.WeixinReqHandle;
import com.newland.wechat.parser.MsgType;
import com.newland.wechat.req.Req;
import com.newland.wechat.req.TextReq;
import com.newland.wechat.req.VoiceReq;
import com.newland.wechat.resp.Resp;
import com.newland.wechat.resp.TextResp;
import com.newland.weixinService.dispatch.Dispatcher;
import com.newland.weixinService.session.SessionManager;
import com.newland.weixinService.session.WeixinSession;

/**
 * 微信消息处理类
 * 
 * @author dvlp
 * 
 */
@Service("weixinHandle")
public class WeixinHandleService implements WeixinReqHandle {

	public static final String WEIXIN_TOKEN = "Token";

	@Resource(name = "sessionManager")
	private SessionManager sessionManager;

	@Resource(name = "dispatcher")
	private Dispatcher dispatcher;
	
	
	private static final int MAX_LENGTH=500;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Resp handleWeixinReq(Req req) throws Exception {
		
		Resp resp = null;
		try {
			WeixinSession sess = this.sessionManager.getSession(req, false);
			
			if(req instanceof TextReq){
				TextReq textReq = (TextReq) req;			
				String content = textReq.getContent();	
				//过滤请求中文字不超过500汉字。
				if(StringUtils.isNotBlank(content)&&content.length()>MAX_LENGTH){				
					content=content.substring(0,MAX_LENGTH)+"[超限"+MAX_LENGTH+"汉字]";
					textReq.setContent(content);
				}
			}		
			
			
			resp = dispatcher.dispatch(req, sess);
			
			//处理响应超过500汉字。
			
			if(resp instanceof TextResp){
				TextResp textResp = (TextResp) resp;	
					if(req instanceof VoiceReq && !sess.isCustomServiceFlag()){
						VoiceReq voiceReq = (VoiceReq)req;
						if(voiceReq.getMsgType().equals(MsgType.voice) && voiceReq.getFormat().equals("amr")){
							textResp.setContent("小兴听到您在说："+
									voiceReq.getRecognition()+"。\n\n"+textResp.getContent());
						}
					}
				String content = textResp.getContent();	
				//过滤请求中文字不超过500汉字。
				if(StringUtils.isNotBlank(content)&&content.length()>MAX_LENGTH){				
					content=content.substring(0,MAX_LENGTH)+"[超限"+MAX_LENGTH+"汉字]";
					textResp.setContent(content);
				}
			}				
			
			logger.info("生成返回报文成功,消息内容[" + resp + "],接收用户["
					+ resp.getToUserName() + "],消息类型["
					+ resp.getMsgType().toString() + "]");
		} catch (AppBizException e) {
			// 业务要求 抛出异常信息
			resp = defaultResp(req, e.getMessage());
			logger.error("出错了", e);
		} catch (AppRTException e1) {
			resp = defaultResp(req, e1.getMessage());
			logger.error("出错了", e1);
		} catch (Throwable e2) {
			resp = defaultResp(req, "系统异常。");
			logger.error("出错了", e2);
		}
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
		// resp.setMsgId(req.getMsgId());
		resp.setFromUserName(req.getToUserName());
		resp.setContent(content);
		return resp;
	}

	@Override
	public String getToken() {
		String token = WeixinApplication.getToken();
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("微信通信密钥未配置! ");
		}
		return token;
	}

}
