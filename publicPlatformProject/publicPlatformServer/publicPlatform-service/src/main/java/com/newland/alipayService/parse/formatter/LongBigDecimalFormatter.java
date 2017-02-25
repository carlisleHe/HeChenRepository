package com.newland.alipayService.parse.formatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.intensoft.formater.BigDecimalFormatter;
import com.intensoft.formater.NumberFormatter;
import com.intensoft.formater.exception.FormatterException;
/**
 * 
 * @describer <h3>不受长度限制的bigdecimalformatter，实现是基于下面2点:</h3>
 * <p>1、因为原有的<tt>BigDecimalFormatter</tt>在format超出double限制的数据时会发生精度丢失的问题，考虑到这点，这里重载了 format与unformat方法</p>
 * <p>2、考虑到voluns是所有系统共用的基础性框架，所以和老章商量后决定e家财富率先试用，测试没问题后考虑向voluns合并，直至推广至整个网银项目组</p>
 * @see com.intensoft.formater.BigDecimalFormatter
 * @author xzz
 * @date 2013-6-8
 */
public class LongBigDecimalFormatter extends NumberFormatter{
	private String pattern = "0.00";
	public LongBigDecimalFormatter(String name) {
        super(name);
        
    }
    
    public LongBigDecimalFormatter() {
        super("LongBigDecimalFormatter");
    }
    /**
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        if (aObject == null) return null;
        DecimalFormat df = new DecimalFormat(pattern);
        if (aObject instanceof String) {
        	try {
        		aObject = unformat((String)aObject, "#0.00");
        	} catch (Exception e) {}
        }
        if (aObject instanceof Number) {
            try {
            	return df.format(aObject);
            }  catch (Exception e) {
                throw new FormatterException(this, "格式化器异常", e);
            }
        }
        throw new FormatterException(this, "不支持数据类型:"+aObject.getClass().getName());
    }

    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
    	if (aString == null || "".equals(aString))
    		return null;
    	aString=aString.trim();
    	BigDecimal bd = null;
    	try {
    		bd = new BigDecimal(aString);
    	} catch (Exception e) {
    	}
    	if (bd != null) 
    		return bd;
        Object result = super.unformat(aString);
        if (result == null) return null;
        //bigdecimal直接返回
        if(result instanceof BigDecimal)
        	return result;
        BigDecimal bigDec = new BigDecimal(((Number)result).doubleValue());
        return bigDec;
    }

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
    public static void main(String[] args) {
    	BigDecimalFormatter a=new BigDecimalFormatter();
    	System.out.println(a.unformat("50000.1        "));
    	
	}

}
