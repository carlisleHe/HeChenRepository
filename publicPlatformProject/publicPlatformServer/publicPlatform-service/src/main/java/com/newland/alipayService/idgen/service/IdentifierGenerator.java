package com.newland.alipayService.idgen.service;

/**
 * 流水号生成器接口
 * <p>
 * 一般包括系统流水号、文件流水号等，不同流水号会有不同的规则，由具体实现去完成。<br>
 * 具体的实现类应自己声明为Service Bean，供上层程序选用组装。<br>
 * 该接口影响面广，属于基础设施，需确保该接口简洁稳定，请谨慎维护<br>
 * <p>
 * 【术语定义】<br>
 * 流水号(Serial No，缩写sno)：指一系列定长的系统自动生成的唯一的编码，String类型。<br>
 * 顺序号(Sequence No. 缩写seq)：是流水号的组成部分，是流水号中顺序增长的因子，int类型。<br>
 * <p>
 * 设计上遵循《JGB2009001兴业银行总行信息系统序号码设计标准》，相关特征约定如下：<br>
 * <ol>
 * <li>序号码唯一性：固定编码(prefix)与顺序号(sequence)组合，固定编码可选，顺序号可循环</li>
 * <li>连续性要求：顺序增长，跳号作废</li>
 * <li>序号循环方式：最大值循环，即日终不做归零处理（因网银类多为7*24小时系统）</li>
 * <li>序号限值：只能用数字</li>
 * <li>序号初始化：赋初值</li>
 * <li>序号步进值：1，不可调整</li>
 * <li>锁机制：记录锁</li>
 * <li>序号分类：系统流水号、文件流水号、商户号</li>
 * </ol>
 * 
 * @author yelm
 * @since 2013-01-07
 */
public interface IdentifierGenerator { 
	/**
	 * 生成一个新的流水号
	 * @return 流水号
	 */
	public String generate();
}
