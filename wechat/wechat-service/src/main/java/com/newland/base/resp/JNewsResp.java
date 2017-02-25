package com.newland.base.resp;

import com.newland.base.parser.MsgType;

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
	
	
	private Article article;
	
	public JNewsResp(){
		this.setMsgType(MsgType.news);
	}


//	@JSON(name = "news")
	public Article getArticle() {
		return article;
	}


	public void setArticle(Article article) {
		this.article = article;
	}

}
