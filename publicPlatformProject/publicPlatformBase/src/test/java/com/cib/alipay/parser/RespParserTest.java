package com.cib.alipay.parser;

import java.util.ArrayList;
import java.util.List;

import com.alipay.api.internal.util.AlipaySignature;
import com.newland.alipay.env.AlipayApplication;
import com.newland.alipay.parser.MsgType;
import com.newland.alipay.post.AlipayJsonUtils;
import com.newland.alipay.resp.ArticleItem;
import com.newland.alipay.resp.JNewsResp;

public class RespParserTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		JNewsResp resp = new JNewsResp();
		ArticleItem item = new ArticleItem();
		resp.setCreateTime(28347);
		resp.setFromUserName("329949328");
		// resp.setMsgId(new Long(238473));
		resp.setMsgType(MsgType.imageText.name());
		resp.setToUserName("23984378");
		item.setDesc("3843837");
		item.setImageUrl("tdiufiuewr");
		item.setTitle("shidfhj");
		item.setUrl("sfhidhf");
		List<ArticleItem> list = new ArrayList<ArticleItem>();
		list.add(item);
		item = new ArticleItem();
		item.setDesc("3843837");
		item.setImageUrl("tdiufiuewr");
		item.setTitle("shidfhj");
		item.setUrl("sfhidhf");
		list.add(item);
		resp.setArticles(list);
		String content = AlipayJsonUtils.convertToJson(resp, false);
		System.out.println(content);
		String sign = AlipaySignature.rsaSign(resp.getXMLRespContent(), AlipayApplication.getCusPrivateKey(), "utf-8");
		System.out.println(AlipaySignature.rsaCheckContent(resp.getXMLRespContent(), sign, AlipayApplication.getAlipayPublicKey(), "utf-8"));
	}

}
