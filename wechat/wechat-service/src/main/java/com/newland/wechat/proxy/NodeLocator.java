package com.newland.wechat.proxy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 通过散列值定位节点接口
 * 
 * @author hongye
 * @since 2014-07-21
 */
public interface NodeLocator {
	
	/**
	   * 取得给定键的定位节点
	   */
	 AlipayNode getPrimary(String k);

	  /**
	   *取得节点的备份位置序列的迭代器
	   */
	  Iterator<AlipayNode> getSequence(String k);

	  /**
	   * 取得所有接点
	   */
	  Collection<AlipayNode> getAll();

	  /**
	   * 创建一个只读NodeLocator副本。
	   */
	  NodeLocator getReadonlyCopy();

	  /**
	   * 状态更新。
	   *
	   * @param nodes New locator nodes.
	   */
	  void updateLocator(final List<AlipayNode> nodes);

}
