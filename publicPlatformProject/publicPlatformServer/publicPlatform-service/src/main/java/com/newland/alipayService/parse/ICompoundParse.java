package com.newland.alipayService.parse;

import java.io.File;
import java.util.Collection;
/**
 * 
 * @describer 用于文件与多对象对应解析，解决目前不支持文件与多对象对应的解析<br>
 * <p>1、支持原有的单对象解析，具体的model映射配置与原有的一致</p>
 * <p>2、本接口要求所有针对多对象文件解析的model必须实现下列接口方法，用于实现对文件->对象与对象->文件解析结果的获取</p>
 * @author xzz
 * @date 2013-6-4
 */
public interface ICompoundParse {
	/**
	 * 实现文件->对象的解析
	 * @param file
	 * @param pattern
	 * @return 正常情况下返回解析集合
	 * 
	 */
	public Collection<? > convertFileToModel(File file,CompoundParsePattern pattern);
	/**
	 * 实现对象->文件的解析
	 * @param collection
	 * @param pattern
	 * @return 正则情况下返回生成的文件
	 */
	public File convertModelToFile(Collection<? > collection,CompoundParsePattern pattern,String fileName);

}
