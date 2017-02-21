package com.cib.alipayserver.dispatch.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cib.alipay.env.AlipayApplication;
import com.cib.alipay.req.Req;
import com.cib.alipay.resp.ArticleItem;
import com.newland.wechatServer.account.model.Total;
import com.newland.wechatServer.card.model.BindCard;
import com.newland.wechatServer.card.service.BindCardService;
import com.newland.wechatServer.common.Const;
import com.newland.wechatServer.customer.model.Customer;
import com.newland.wechatServer.customer.service.CustomerService;
import com.newland.wechatServer.dispatch.ActionResult;
import com.newland.wechatServer.service.QueryBalanceService;
import com.newland.wechatServer.session.AlipaySession;
import com.newland.wechatServer.util.AccountUtil;
import com.newland.wechatServer.util.CryptoUtil;
/**
 * 理财卡余额查询
 * @author wot_xuzhenzhou
 *
 */
public class QueryAcctBalanceAction extends AlipayBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource (name = "queryBalanceService")
	private QueryBalanceService queryService;
	@Resource(name = "bindCardService")
	private BindCardService bindCardService;
	@Resource(name = "customerService")
	private CustomerService customerService;
	public static final String FLOW_TRANS="10040"; //交易明细
	@Override
	public ActionResult execute(Req req, AlipaySession sess) throws Exception {
		String openId=req.getFromUserName();
		Customer authCustomer = customerService.findAuthCustomer(
				AlipayApplication.getAppId(), openId);
		
		if (authCustomer == null){
			return	authorizationActionResult(req, Const.OPER_TYPE_ADD, "亲，虽然您可能存了很多钱，但是，没有绑定理财卡，是看不到存款的啊，一定要先绑定哦！", "点击绑定", "，马上炫富");
		}
		if(authCustomer.getDefaultDCCard()== null){
			return	authorizationActionResult(req, Const.OPER_TYPE_ADD, "亲，虽然您可能存了很多钱，但是，没有绑定理财卡，是看不到存款的啊，一定要先绑定哦！", "点击绑定", "，马上炫富");
		}
		BindCard bindCard = bindCardService.findAuthCardByAcctNo(
				AlipayApplication.getAppId(), openId, authCustomer.getDefaultDCCard());
		if (bindCard == null){
			return	authorizationActionResult(req, Const.OPER_TYPE_ADD, "亲，虽然您可能存了很多钱，但是，没有绑定理财卡，是看不到存款的啊，一定要先绑定哦！", "点击绑定", "，马上炫富");
		}
		List<Total> totals  = this.queryService.queryBalance(bindCard.getAcctNo());
		if (totals == null) {
			logger.error("卡号：[" + bindCard.getAcctNo() + "],查询余额出错！");
			return null;
		}
		String content = "您的理财卡" + AccountUtil.calcMaskAcctNo(bindCard.getAcctNo()) + "存款余额为：\n\n";
		DecimalFormat df = new DecimalFormat("#,##0.00");
		for (int i =0 ;i<totals.size();i++){
			Total in = totals.get(i);
			if(i%4==0){
				if(i>1){
					content += "\n";
				}
				content += in.getTitle() + ": " + df.format(in.getAmount()) + "\n其中:\n";
			}
			else{
				content += in.getTitle() + ": " + df.format(in.getAmount()) + "\n";
			}
		}
		content +="\n\n"+"点击阅读全文查询交易明细";
		StringBuilder sb = new StringBuilder();
		String url = sb.append("flow_trans=").append(FLOW_TRANS).append("&").append("cardno=").append(bindCard.getAcctNo()).append("&reqTime=").append(System.currentTimeMillis()).toString();
		logger.debug("原交易明细URL:"+transUrl+url);
		url = transUrl+CryptoUtil.encryptByDES(Const.ENC_DES_KEY, url, HTTP.UTF_8);
		logger.debug("生成交易明细URL:"+url);
		
		List<ArticleItem> as=  new ArrayList<ArticleItem>();
		ArticleItem item = new ArticleItem();
		item.setDesc(content);
		item.setTitle("理财卡账户信息");
		item.setUrl(url);
		as.add(item);
		return super.generalArticleResp(req,as);
	}

}
