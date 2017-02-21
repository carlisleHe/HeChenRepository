package com.cib.alipayserver.parse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.intensoft.formater.TextAlign;
/**
 * 
 * @describer 复合文件解析模板:
 * <p>1、复合的文件其实每行的数据都有一个特定的业务涵义，可以理解为每行数据都有一个delimiterValue，根据这个可以实现数据与model的对应 </p>
 * <p>2、为了兼容传统的单一对象映射解析,当处理的文件为单一对象时统一配置为:<tt>delimiterPosition=-1&&mappedModelName={"":modelName}</tt></p>
 * @author xzz
 * @date 2013-6-4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CompoundParsePattern {
	/**
	 * 解析文件类型，目前支持普通文本
	 * @return
	 */
	ParseFileType fileType() default ParseFileType.PLAIN;
	/**
	 * 文件编码
	 * @return
	 */
	String charset() default "GBK";
	/**
	 * 定长报文时的对齐方式
	 * @return
	 */
	TextAlign textAlign() default TextAlign.LEFT;
	 /**
	  * 标识，true表示为定长报文
	  * @return
	  */
	 boolean isFixLength() default false;
	 /**
	  * 区分值长度，适用于定长报文解析时
	  * 此时，isFixLength = true
	  * @return
	  */
	 int delimiterValueLength() default 0;
	
	 /**
	 * 区分值在文件行里的位置<br>
	 * <h3> >=<tt>0</tt>时:</h3>
	 * <p>1、当为定长报文时为区分值在行里的偏移量</p>
	 * <p>2、为分隔报文时为区分值在的分隔符位置</p>
	 * <h3> ==<tt>-1</tt>时:</h3>
	 * <p>表示单一对象映射解析,此时对应的mappedModelNames必须为{"":modelname}</p>
	 * @return
	 */
	 int delimiterPosition();
	 /**
	  * 区分符，用于每个数据项的分隔，定长报文时忽略
	  * @return
	  */
	 char delimiterChar() default '|';
	 /**
	  * 区分值对应的model映射,格式:<tt>{区分值:modelname}</tt>
	  * @return
	  */
	 String[] mappedModelNames() default{};
	
	 /**
	  * 正则，用于解析时判断是否跳过本行
	  * @return
	  */
	 String skipRegex() default "";
	 /**
	  * 追加，用于生成文件时的文件头
	  */
	 String addHead() default "";
	 /**
	  * 追加，用于生成文件时的文件尾
	  */
	 String addTail() default "";
	

}
