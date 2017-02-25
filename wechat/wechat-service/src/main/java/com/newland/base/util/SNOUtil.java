package com.newland.base.util;

import java.util.UUID;

import com.intensoft.util.StringUtils;
import com.newland.base.common.Const;
/**
 * 
 * @ClassName: SnoUtil  
 * @Description: 序列号工具类 
 * @author: xuzz 
 * @date:2012-12-24 下午02:30:50
 */
public class SNOUtil {
	/**
	 * 生成uuid，输出为大写，去掉"-"连接符
	 * @return
	 */
	public static String genUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	/**
	 * 生成微信APPID
	 * @return
	 */
	public static String genWeixinAppId(){
		return Const.APP_ID_PREFIX_WEIXIN+genUUID();
	}
	/**
	 * 生成带有前缀的UUID，长度34位
	 * @param prefix 长度2位
	 * @return
	 */
	public static String genPrefixAppId(String prefix){
		if(StringUtils.isBlank(prefix)||prefix.length()!=2){
			throw new RuntimeException("前缀长度必须为2位！");
		}
		return prefix+genUUID();
	}

}
