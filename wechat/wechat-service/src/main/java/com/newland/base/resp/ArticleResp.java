package com.newland.base.resp;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.newland.base.parser.MsgType;

/**
 * 新闻响应类
 * @author dvlp
 *
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleResp extends Resp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArticleResp(){
		this.setMsgType(MsgType.news);
	}
	@XmlElement(name = "ArticleCount")
	private int articleCount;
	
	@XmlElementWrapper(name = "Articles")
	@XmlElement(name = "item")
	private List<ArticleItem> articles;


	public List<ArticleItem> getArticles() {
		return articles;
	}



	public void setArticles(List<ArticleItem> articles) {
		this.articles = articles;
	}



	public int getArticleCount() {
		return articleCount;
	}



	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	
	

}
