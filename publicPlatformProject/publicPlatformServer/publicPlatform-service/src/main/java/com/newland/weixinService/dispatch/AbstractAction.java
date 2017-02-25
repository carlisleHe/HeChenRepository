package com.newland.weixinService.dispatch;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.newland.wechat.parser.MsgType;
import com.newland.wechat.req.Req;
import com.newland.wechat.req.TextReq;
import com.newland.wechat.resp.Article;
import com.newland.wechat.resp.ArticleItem;
import com.newland.wechat.resp.ArticleResp;
import com.newland.wechat.resp.JNewsResp;
import com.newland.wechat.resp.JTextResp;
import com.newland.wechat.resp.Text;
import com.newland.wechat.resp.TextResp;
import com.newland.weixinService.session.WeixinSession;

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
	public abstract ActionResult execute(Req req, WeixinSession sess)
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
		// resp.setMsgId(req.getMsgId());
		resp.setFromUserName(req.getToUserName());
		resp.setContent(msg);
		return new DefaultResult(resp);
	}

	// 空消息可以让微信不再重发消息。
	protected ActionResult generalEmptyResp(Req req) {
		return generalTextResponse(req, null);
	}

	protected ActionResult generalArticleResp(Req req,
			List<ArticleItem> articles) {
		ArticleResp resp = new ArticleResp();
		resp.setFromUserName(req.getToUserName());
		resp.setToUserName(req.getFromUserName());
		// resp.setMsgId(req.getMsgId());
		resp.setArticles(articles);
		resp.setArticleCount(articles.size());
		return new DefaultResult(resp);
	}

	/**
	 * 响应消息转为JSON格式便于发送异步消息。
	 * 
	 * @param res
	 * @param content
	 * @return
	 */
	protected JTextResp convertToJsonTextResp(TextResp res) {
		JTextResp msg = new JTextResp();
		msg.setFromUserName(res.getFromUserName());// 因上行接口中送openbankId 所以这里直接去取
		msg.setCreateTime(res.getCreateTime());
		msg.setMsgType(MsgType.text);
		msg.setToUserName(res.getToUserName());
		Text text = new Text(res.getContent());
		msg.setText(text);
		return msg;
	}

	/**
	 * 响应消息转为JSON格式便于发送异步消息。
	 * 
	 * @param res
	 * @param content
	 * @return
	 */
	protected JNewsResp convertToJsonNewsResp(ArticleResp res) {
		JNewsResp msg = new JNewsResp();
		msg.setFromUserName(res.getFromUserName());// 因上行接口中送openbankId 所以这里直接去取
		msg.setCreateTime(res.getCreateTime());
		msg.setMsgType(MsgType.news);
		msg.setToUserName(res.getToUserName());
		Article article = new Article();
		article.setArticles(res.getArticles());
		msg.setArticle(article);
		return msg;
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
		// dest.setMsgType(MsgType.text);
		dest.setToUserName(src.getToUserName());
		dest.setContent(content);
		return dest;
	}

	/**
	 * 
	 * 清除无答案计数器，当有回答时清空计数器。 1，有图文指令回复， 2，有聊天内容回复， 3，非无答案指令
	 * 
	 * @param sess
	 */
	protected void clearUserNoAnswerCounter(WeixinSession sess) {
		sess.removeValue(USER_NO_ANSWER_COUNT);
	}

	

}
