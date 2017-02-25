package com.newland.alipayService.idgen.service;

/**
 * 基于顺序累加计算的序列号生成器
 * <p>
 * 1. max_val就是它的最大顺序值，每次加一都会存取一次数据库<br>
 * 2. 优点是这个生成器产生的流水号不会跳号<br>
 * 3. 缺点是数据库存取频繁，只适用于并发量不大的流水需求场景<br>
 * 
 * @author yelm
 * @since 2013-01-07
 */
public abstract class SeqIdGenerator extends AbstractIdGenerator {
	/** 顺序号最大值 */
	protected int maxValueLimit;
	/** 顺序号长度 */
	protected int seqLength;

	/**
	 * 构造函数。因为顺序号是int类型，所以总的流水号长度不能大于10。<br>
	 * 每次应用重新启动，多台服务器的hi都和数据库中的max_val相同（启动时是select）。<br>
	 * 所以对于HiLo算法的流水号来说，初始化必须将lo越界，以强制其第一次进行跳号。 <br>
	 * 确保多台服务器有不同的hi值， 所以反复重启应用会使这类流水号产生浪费。<br>
	 * 
	 * @param seqLen
	 */
	protected SeqIdGenerator(int seqLen) {
		if (seqLen <= 0 || seqLen > 10) {
			throw new IllegalArgumentException("顺序流水号生成器，位数必须大于0且小于10。");
		}
		// 初始化最大顺序号
		this.maxValueLimit = (int) Math.pow(10, seqLen) - 1;
		this.seqLength = seqLen;
	}

	@Override
	protected int getMaxValueLimit() {
		return this.maxValueLimit;
	}
	
	@Override
	protected int getSeqLength() {
		return this.seqLength;
	}

	@Override
	protected int generateSeq() {
		// 每一次增长都读写一次数据库，确保多服务器之间号码连续
		int seq = this.getMaxValueAndInc();
		return seq;
	}
}
