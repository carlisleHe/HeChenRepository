package com.newland.alipayService.notify.model;

import java.io.Serializable;
import java.util.List;

import com.intensoft.dao.hibernate.HibernateGenericDao;

/**
 * 查询结果大分页对象
 * 
 * @author fangsq
 * @since 2014-06-08
 * @param <T> 持久化类型
 * @see HibernateGenericDao
 */
public class BigPage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 默认分页大小 */
	public final static int DEF_PAGESIZE = 30;

	/**
	 * 当前页面起始记录的索引号。<br>
	 * 例如以100条记录按30条/页进行划分，可以分4页，页面的起始索引分别为：0,30,60,90。
	 */
	private int startIndex = 0;

	/** 页面大小，当前页面所包含的记录条数 */
	private int pageSize = DEF_PAGESIZE;

	/** 本次查询结果的记录总数 */
	private int totalCount;

	/** 当前页面容器所包含的对象列表 */
	private List<T> items;

	/**
	 * 使用默认页面大小的构造函数<br>
	 * 属性设置必须按setPageSize()→setTotalCount()→setStartIndex()的顺序
	 */
	public BigPage(List<T> items, int totalCount, int startIndex) {
		// pageSize必须第一个被设置
		setPageSize(DEF_PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	/**
	 * 自定义页面大小的构造函数<br>
	 * 属性设置必须按setPageSize()→setTotalCount()→setStartIndex()的顺序
	 */
	public BigPage(List<T> items, int totalCount, int startIndex, int pageSize) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面记录数，发生于setTotalCount()和setStartIndex()之前。<br>
	 * 相对于Page类，取消了大小限制
	 * 
	 * @param pageSize 页面记录数
	 */
	private void setPageSize(int pageSize) {
			this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置总记录数，发生于在setPageSize()之后。
	 * 
	 * @param totalCount 查询结果的总记录数
	 */
	private void setTotalCount(int totalCount) {
		// 容错：totalCount不得为负数
		if (totalCount > 0) {
			this.totalCount = totalCount;
		} else {
			this.totalCount = 0;
		}
	}

	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * 设置当前页起始索引，发生于在setTotalCount()之后。
	 * 
	 * @param startIndex 起始记录
	 */
	private void setStartIndex(int startIndex) {
		// 容错：startIndex值过小，纠正为第一页，过大则纠正为最后一页
		if ((totalCount <= 0) || (startIndex < 0)) {
			this.startIndex = 0;
		} else if (startIndex >= totalCount) {
			int pageCount = totalCount / pageSize;
			if (totalCount % pageSize > 0)
				pageCount++;
			this.startIndex = pageSize * (pageCount - 1);
		} else {
			this.startIndex = startIndex;
		}
	}

	/**
	 * @return 返回下一页索引
	 */
	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount)
			return getStartIndex();
		else
			return nextIndex;
	}

	/**
	 * @return 返回上一页索引
	 */
	public int getPrevIndex() {
		int prevIndex = getStartIndex() - pageSize;
		if (prevIndex < 0)
			return 0;
		else
			return prevIndex;
	}
	
	/**
	 * 返回当前页是否为第一页。<br>
	 * 适用场景：无页面序号，仅使用"上一页"、"下一页"翻页时。
	 * 
	 * @return true表示当前页为第一页
	 */
	public boolean isFirstPage() {
		return (getPrevIndex() == 0);
	}

	/**
	 * 返回当前页是否为最后一页。<br>
	 * 适用场景：无页面序号，仅使用"上一页"、"下一页"翻页时。
	 * 
	 * @return true表示当前页为最后一页
	 */
	public boolean isLastPage() {
		return (getNextIndex() == getStartIndex());
	}

}

