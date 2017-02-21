package com.cib.alipay.resp;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 用于json响 应的新闻类
 * @author dvlp
 *
 */
public class JNewsResp extends Resp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JNewsResp(){
		this.setMsgType("image-text");
	}


	private List<ArticleItem> articles;

	@JSON(name = "articles")
	public List<ArticleItem> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleItem> articles) {
		this.articles = articles;
	}

}
