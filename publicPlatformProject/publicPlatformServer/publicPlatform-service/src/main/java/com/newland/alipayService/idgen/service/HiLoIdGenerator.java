package com.newland.alipayService.idgen.service;


/**
 * 基于高低位算法的流水号生成器
 * <p>
 * 算法说明:<br>
 * 1. 高位为hi，低位为lo，seq=hi*max_lo+lo <br>
 * 2. hi存储于数据表中max_val字段，数据库中此字段值加1保存 <br>
 * 3. lo在 0到maxLo-1 之间循环<br>
 * 4. maxLo配置多大合适？交由具体实现类去决定，如果希望减少数据表的读取，可以设大一些<br>
 * 但应注意一次服务的重启，就会消耗掉一个hi，产生一定的跳号，过大也并不适宜<br>
 * 
 * <p>
 * 示例：<br>
 * hi初始为1，lo初始为0，max_lo为100，取值依次是：<br>
 * 锁定然后读取hi为1，并将SnoConfig.maxValue加1，然后回数据库为2。<br>
 * seq=hi*max_lo+lo=1*100+0=100<br>
 * seq=hi*max_lo+lo=1*100+1=101<br>
 * ………………<br>
 * seq=hi*max_lo+lo=1*100+99=199<br>
 * lo=100，lo>=maxLo，所以再锁定取hi为2，加1后存回数据库为3，lo归0。<br>
 * seq=hi*max_lo+lo=2*100+0=200<br>
 * 
 * @author yelm
 * @since 2013-01-07
 */
public abstract class HiLoIdGenerator extends AbstractIdGenerator {
	/** 高位最大值 */
	protected int maxHi;
	/** 低位最大值 */
	protected int maxLo;
	/** 顺序号长度 */
	protected int seqLength;

	/** 当前高位值 */
	private int hi;
	/** 当前低位值 */
	private int lo;

	/**
	 * 构造函数。因为顺序号是int类型，所以总的流水号长度不能大于10。<br>
	 * 每次应用重新启动，多台服务器的hi都和数据库中的max_val相同（启动时是select）。<br>
	 * 所以对于HiLo算法的流水号来说，初始化必须将lo越界，以强制其第一次进行跳号。 <br>
	 * 确保多台服务器有不同的hi值， 所以反复重启应用会使这类流水号产生浪费。<br>
	 * 
	 * @param hiDigits
	 *            高位位数
	 * @param loDigits
	 *            低位位数
	 */
	protected HiLoIdGenerator(int hiDigits, int loDigits) {
		if (hiDigits <= 0 || loDigits <= 0 || (hiDigits + loDigits) > 10) {
			throw new IllegalArgumentException(
					"高低位流水号生成器，高位和低位的位数必须大于0且总位数必须小于10。");
		}
		// 为了便于计算，所以maxLo没有减1，使用时应注意
		this.maxHi = (int) Math.pow(10, hiDigits) - 1;
		this.maxLo = (int) Math.pow(10, loDigits);
		this.seqLength = hiDigits + loDigits;
		// 初始化时强制lo值溢出
		this.lo = this.maxLo + 1;
	}

	@Override
	protected int getMaxValueLimit() { 
		return this.maxHi;
	}

	@Override
	protected int getSeqLength() {
		return this.seqLength;
	}

	@Override
	protected int generateSeq() {
		// 当低位用尽，才增长一次MaxValue，减少数据库读写操作
		synchronized (this) {
			if (lo >= maxLo) {
				lo = 0;
				hi = this.getMaxValueAndInc();
			}
			int seq = hi * maxLo + lo;
			lo++;
			return seq;
		}
	}
}
