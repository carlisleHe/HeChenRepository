package com.newland.alipayService.dispatch.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newland.alipay.req.EventReq;
import com.newland.alipay.req.Req;
import com.newland.alipay.req.TextReq;
import com.newland.alipay.resp.Resp;
import com.newland.alipay.resp.TextResp;
import com.newland.alipayService.dispatch.AbstractAction;
import com.newland.alipayService.dispatch.Action;
import com.newland.alipayService.dispatch.ActionManager;
import com.newland.alipayService.dispatch.ActionResult;
import com.newland.alipayService.dispatch.DefaultResult;
import com.newland.alipayService.dispatch.Dispatcher;
import com.newland.alipayService.dispatch.ForwardResult;
import com.newland.alipayService.session.AlipaySession;

/**
 * 微信消息分发类
 * 调度原则：1，每个消息类型都有处理类，
 * 2，如果类型处理未定义则转默认处理
 * 3，默认处理暂定为提示“不能识别”
 * 4，文本消息默认都由小I处理，（除非客服已接管）
 * 5，文本消息可以是指令，也可以是聊天内容，如果是指令（以COMMAND:开头）则优先执行指令。
 * 6，菜单事件内容可能是指令，与可以是文本内容（以TEXT:开头）需要小I解析。
 * 7，最小转发次数，如果能返回结果就不转发到其它类处理。
 *  
 * @author dvlp
 */
@Service("dispatcher")
public class DispatcherImpl implements Dispatcher {	
	private static final String COMMAND_PREFIX = "command:";
	private static final String TEXT_PREFIX = "text:";
	@Resource(name = "actionManager")
	private ActionManager manager;

	/**
	 * 空消息处理类。
	 */
	public static final String EMPTY_ACTION_KEY = "empty";
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 分发消息
	 */
	@Override
	public Resp dispatch(Req req, AlipaySession ses) throws Exception {		
		Action	action =null;
		switch (req.getMsgType()) {
		case image:
			return generalTextResponse(req, "亲，小兴看不懂图片，请给我发文字或语音吧。");
		case text:
			logger.info("收到文本请求！类型:[" + req.getMsgType() + "] 消息id:["
					+ req.getMsgId() + "] fromUser: [" + req.getFromUserName()
					+ "]");
			//如果是指令优先处理			
			TextReq textReq = (TextReq) req;			
			String content = textReq.getText().getContent();
			
			if (StringUtils.isNotBlank(content)&&content.startsWith(COMMAND_PREFIX)) {
				String command = content.substring(COMMAND_PREFIX
						.length());
				action = getAction(command);
				
			} else {	
				// 文本型消息都先由XIAOI进行处理。包括聊天
				//将XIAOI_ACTION_KEY改为TEXT_ACTION_KEY   luwn 2014.10.30
				action = this.getAction(AbstractAction.TEXT_ACTION_KEY);
			}
			
			
			break;
		case event:
			EventReq event = (EventReq) req;
			
			logger.info("收到事件请求！类型:[" + req.getMsgType() + "] 消息id:["
					+ req.getMsgId() + "] fromUser: [" + req.getFromUserName()
					+ "] event:[" + event.getEvent() + "] eventKey:["
					+ event.getActionParam() + "]");

			//测试时发现事件可能为空。未知事件，不处理。
			if(event.getEvent()==null){
				action=getAction(EMPTY_ACTION_KEY);
				break;
			}
			/**
			 * 处理事件类型 如果是菜单点击事件 actionKey 由click.{eventKey}组成 其它事件actionKey
			 * 为事件类型，如关注为(subscribe)
			 */

			switch (event.getEvent()) {
			case click:
				String eventKey = StringUtils.lowerCase(event.getActionParam());
				// 如果CLICK且菜单KEY是以COMMAND:前缀的，则直接执行命令。

				if (StringUtils.isNotBlank(eventKey)) {
					action = this.getAction(eventKey);
				}

				break;
			case verifygw:
				action = this.getAction(AbstractAction.ACTIVE_ACTION_KEY);
				break;

			default:
					
			}
			break;
		default:
			
		}

		// 没有找到，则默认处理。
		if (action == null) {
			action = getAction(AbstractAction.DEFAULT_ACTION_KEY);
		}
		ActionResult result = action.execute(req, ses);
		return transToResp(result, req, ses);

	}



	private Action getAction(String key) {
		Action action = manager.getAction(StringUtils.lowerCase(key));
		if (action == null)
			logger.warn("未找到对应Action, key:[" + key + "]");
		return action;
	}

	

	/**
	 * 转换为响应对象
	 * 
	 * @param result
	 * @param req
	 * @param ses
	 * @return
	 * @throws Exception
	 */
	private Resp transToResp(ActionResult result, Req req, AlipaySession ses)
			throws Exception {
		String actionKey = "";
		Action action = null;
		switch (result.getType()) {
		case DEFAULT:
			/**
			 * 默认返回类型，直接返回响应对象
			 */
			return ((DefaultResult) result).getResp();
		case FORWARD:
			/**
			 * 转发类型 取前一Action指定Action执行并返回
			 */
			ForwardResult forwardResult=(ForwardResult) result;
			actionKey = forwardResult.getActionKey();
			action = this.getAction(actionKey);
			if (action == null) {
				
				//不知转发后，却没有KEY，不处理。前面已处理一次了不再处理。
				//return defaultResp(req) ;		
				return null;
			}
			//重写仅限于不同消息类型转换，如果同类型请用请求附加属性的方法。
			Req overrideReq = forwardResult.getOverrideReq();
			if (overrideReq != null) {
				result = action.execute(overrideReq, ses);
			} else {
				result = action.execute(req, ses);
			}
			return this.transToResp(result, req, ses);
		case CONTEXT:
			
			//目前没有场景用到重定，暂不用。			
			/**
			 * 重定向类型 类似于web重定向 由前一Action指定重定向的Action,存入session
			 * 下次请求将由session中保存的Action处理
			 */
			//TODO
		}
		return null;
	}

	protected TextResp generalTextResponse(Req req, String msg) {
		TextResp resp = new TextResp();
		resp.setToUserName(req.getFromUserName());
		resp.setFromUserName(req.getToUserName());
		resp.getText().setContent(msg);
		return resp;
		
	}
}
