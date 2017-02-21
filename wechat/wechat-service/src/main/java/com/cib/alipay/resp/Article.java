package com.cib.alipay.resp;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;
/**
 * 新闻类
 * @author dvlp
 *
 */
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ArticleItem> articles;

	@JSON(name = "articles")
	public List<ArticleItem> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleItem> articles) {
		this.articles = articles;
	}

}
