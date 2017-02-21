package com.cib.alipayserver.idgen.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.idgen.service.HiLoIdGenerator;

/**
 * 文件序列号生成器
 * 
 * @author yelm
 * @since 2013-01-08
 */
@Service(value = "fileNoGenerator")
public class FileNoGenerator extends HiLoIdGenerator {
	/** 流水号类型：文件序列号，总长14位，格式：YYYYMMDD000000，后6位采用高低位算法 */
	private static final String SNO_TYPE = "WJBH";
	/** 顺序号高位位数 */
	private static final int HI_LEN = 4;
	/** 顺序号低位位数 */
	private static final int LO_LEN = 2;

	/** 日期格式化器 */
	private SimpleDateFormat sdf;

	public FileNoGenerator() {
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
