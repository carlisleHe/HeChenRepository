package com.newland.wechatServer.dispatch;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.cib.alipayserver.session.AlipaySession;
import com.newland.wechat.req.Req;
import com.newland.wechat.resp.ArticleItem;
import com.newland.wechat.resp.JNewsResp;
import com.newland.wechat.resp.TextResp;
import com.newland.wechat.resp.VerifyActiveResp;

/**
 * 微信操作接口
 * 
 * @author Shizn
 */
public abstract class AbstractAction implements Action, Serializable,
		InitializingBean {	

	/**
	 * 特殊指令和消息
	 */
	public static final String COMMAND_PREFIX = "command:";	
	
	public static final String GET_LOCATION = "TEXT:click.location";
	/**
	 * 小I无答案计数器
	 */
	public static final String USER_NO_ANSWER_COUNT = "USER_NO_ANSWER_COUNT";
	
	// ACTION 类的ID

	/**
	 * 默认的ACTION处理类。
	 */
	public static final String DEFAULT_ACTION_KEY = "default";
	/**
	 * 激活处理类。
	 */
	public static final String ACTIVE_ACTION_KEY = "active";

	/**
	 * 小I的ACTION处理类。
	 */
	public static final String XIAOI_ACTION_KEY = "xiaoi";

	
	/**
	 * 转客服处理ACTION
	 * luwn
	 */
	public static final String CUSTOMER_SERVICE_ACTION_KEY = "customerService";
	
	/**
	 * 进入人工客服判断ACTION
	 * luwn
	 */
	public static final String TEXT_ACTION_KEY = "text";

	// 附加属性信息键
	public static final String XIAO_DEFAULT_ANSWER_KEY = "AbstractAction_xiaoDefaultAnswer";

	private static final long serialVersionUID = 1L;

	private String actionKey;

	public Logger logger = LoggerFactory.getLogger(getClass());

	


	/**
	 * 执行操作
	 * 
	 * @param req
	 * @param sess
	 * @return
	 * @throws Exception
	 */
	public abstract ActionResult execute(Req req, AlipaySession sess)
			throws Exception;

	public AbstractAction() {

	}

	/**
	 * 获取Action Key值
	 * 
	 * @return
	 */
	public void setActionKey(String actionKey) {
		this.actionKey = actionKey;
	}

	public String getActionKey() {
		return actionKey;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.debug("actionKey:[" + getActionKey() + "] HashCode: ["
				+ this.hashCode() + "]");

	}

	protected ActionResult generalTextResponse(Req req, String msg) {
		TextResp resp = new TextResp();
		resp.setToUserName(req.getFromUserName());
		resp.setFromUserName(req.getToUserName());
		resp.getText().setContent(msg);
		return new DefaultResult(resp);
	}
	
	protected ActionResult generalActiveResponse() {
		return new DefaultResult(new VerifyActiveResp());
	}

	protected ActionResult generalArticleResp(Req req,List<ArticleItem> articles) {
		JNewsResp resp = new JNewsResp();
		resp.setFromUserName(req.getToUserName());
		resp.setToUserName(req.getFromUserName());
		resp.setArticles(articles);
		return new DefaultResult(resp);
	}
	
	
	

	/**
	 * 更换消息类型。目前仅用于声音转文本。其它 都用请求添加属性方法传递消息。
	 * 
	 * @param src
	 * @param content
	 * @return
	 */
	protected TextReq generalTextReq(Req src, String content) {
		TextReq dest = new TextReq();
		dest.setCreateTime(src.getCreateTime());
		dest.setFromUserName(src.getFromUserName());
		dest.setMsgId(src.getMsgId());
		dest.setToUserName(src.getToUserName());
		dest.getText().setContent(content);
		return dest;
	}
}
