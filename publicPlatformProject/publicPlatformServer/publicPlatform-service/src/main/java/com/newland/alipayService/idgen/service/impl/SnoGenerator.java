package com.newland.alipayService.idgen.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.newland.alipayService.idgen.service.HiLoIdGenerator;

/**
 * 网银交易流水号生成器
 * 
 * @author yelm
 * @since 2013-01-08
 */
@Service(value = "snoGenerator")
public class SnoGenerator extends HiLoIdGenerator {
	/** 流水号类型：网银流水号，总长16位，格式：YYYYMMDD00000000，后8位采用高低位算法 */
	private static final String SNO_TYPE = "WYLS";
	/** 顺序号高位位数 */
	private static final int HI_LEN = 5;
	/** 顺序号低位位数 */
	private static final int LO_LEN = 3;

	/** 日期格式化器 */
	private SimpleDateFormat sdf;

	public SnoGenerator() {
		super(HI_LEN, LO_LEN);
		// 建立日期格式化器，避免重复创建消耗内存
		this.sdf = new SimpleDateFormat("yyyyMMdd");
	}

	@Override
	protected String getSnoType() {
		return SNO_TYPE;
	}

	@Override
	protected String format(int seq) {
		String part = super.format(seq);
		Date date = new Date();
		String prefix = sdf.format(date);
		return prefix + part;
	}
}
