package com.newland.alipayService.idgen.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newland.alipayService.idgen.dao.SnoConfigDao;
import com.newland.alipayService.idgen.model.SnoConfig;

/**
 * 流水号生成器抽象类
 * 
 * @author yelm
 * @since 2013-01-07	全模块重写
 */
public abstract class AbstractIdGenerator implements IdentifierGenerator {

	@Resource(name = "snoConfigDao")
	protected SnoConfigDao snoConfigDao;

	/** 流水器配置ID */
	protected Integer snoId = -1;

	@PostConstruct
	public void init() {
		// 初始化，从数据库中寻找配置项，如果没有则创建
		SnoConfig config = snoConfigDao.findBySnoType(getSnoType());
		if (config == null) {
			config = new SnoConfig();
			config.setSnoType(getSnoType());
			config.setMaxValue(1); // 为避免特殊情况，初始从1开始
			config = snoConfigDao.save(config);
		}
		snoId = config.getSnoId();
	}

	@Override
	@Transactional(value = "alipayTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public String generate() {
		// 获取下一个顺序号，由具体类实现
		Integer seq = this.generateSeq();
		// 将顺序号转换为流水号，有具体类实现，一般流水号=[固定编码]+顺序号
		String sno = this.format(seq);
		return sno;
	}

	/**
	 * 获取生成器的当前最大值(maxValue)，并将maxValue+1后回写数据库<br>
	 * 如果maxValue+1>maxValueLimit(溢出)，则maxValue归一（避开0）<br>
	 * 因为有多台应用，maxValue必须存放于数据库，通过数据库锁来控制<br>
	 * <br>
	 * Q:为什么需要使用独立事务？<br>
	 * A:因为获取流水号会被囊括在具体业务层的数据库事务中。<br>
	 * 业务层异常引起的事务回滚，不应触发流水号的回滚，流水号用过就要丢弃。<br>
	 */
	@Transactional(value = "alipayTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public int getMaxValueAndInc() {
		// 排他锁定当前流水号生成器的配置
		snoConfigDao.lock(snoId);
		SnoConfig config = snoConfigDao.findById(snoId);
		int max = config.getMaxValue();
		int next = max + 1;
		if (next > this.getMaxValueLimit())
			next = 1;
		config.setMaxValue(next);
		snoConfigDao.update(config);
		return max;
	}

	/**
	 * 返回生成器的流水号类型，需要与数据库内容匹配
	 */
	protected abstract String getSnoType();

	/**
	 * 获取MaxValue所允许的最大值，当MaxValue超过最大值，需要自动归一
	 */
	protected abstract int getMaxValueLimit();

	/**
	 * 获取顺序号的长度
	 */
	protected abstract int getSeqLength();

	/**
	 * 算法实现， 计算并返回下一个顺序号<br>
	 * Q: 为什么返回的顺序号是int而不是long? <br>
	 * A: 因为我们所依存大环境限定了informix数据库，且长期不变， 所以一般建议采用：固定编码+日期+顺序号。
	 * 以int的上限计算，这样一个流水号类型，可承受每秒2.4万的并发，足够使用。
	 */
	protected abstract int generateSeq();

	/**
	 * 将顺序号格式化，并组装成流水号返回，长度等于getSeqLength()
	 */
	protected String format(int seq) {
		// 顺序号格式化，左补零
		String fmt = "%0"+ this.getSeqLength() +"d";
		String str = String.format(fmt, seq);
		// 如果位数超出，去掉高位（正常情况不会）
		if (str.length() > this.getSeqLength()) {
			int idx = str.length() - this.getSeqLength();
			str = str.substring(idx);
		}
		return str;
	}
}
