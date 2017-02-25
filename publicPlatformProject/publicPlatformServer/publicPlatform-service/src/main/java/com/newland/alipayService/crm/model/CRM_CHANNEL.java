package com.newland.alipayService.crm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description: CRM渠道
 * @ClassName:CRM_CHANNEL 
 * @author:xuzz
 * @date:2015-2-10
 */
public enum CRM_CHANNEL {
	/**
	 * 个人网银
	 */
	CHANNEL_PSBANK("个人网银","050"),
	/**
	 * 手机银行
	 */
	CHANNEL_MBANK("手机银行","040"),
	/**
	 * 电话银行
	 */
	CHANNEL_PBANK("电话银行","060"),
	/**
	 * 直销银行
	 */
	CHANNEL_SBANK("直销银行","080"),

	/**
	 * 微信银行
	 */
	CHANNEL_WBANK("微信银行","090");
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 编号
	 */
	private String value;
	/**
	 * 对应操作类型
	 */
	private List<String> channelTypes;
	private CRM_CHANNEL(String desc,String value){
		this.desc = desc;
		this.value = value;
		
		if("050".equals(value)){
			channelTypes = new ArrayList<String>();
			//签约
			channelTypes.add("020301");
			//短信口令
			channelTypes.add("020303");
			//信用卡绑定还款
			channelTypes.add("0204");
			//代扣代缴
			channelTypes.add("0205");
			//贵金属买卖
			channelTypes.add("0206");
			//超级网银
			channelTypes.add("0207");
			//跨行通
			channelTypes.add("0208");
			//新开借记卡
			channelTypes.add("0301");
			//新开存折
			channelTypes.add("0302");
			//新开账户-定期存款
			channelTypes.add("0401");
			//新开账户-凭证式国债
			channelTypes.add("0402");
			//新开账户-电子式国债
			channelTypes.add("0403");
			//新开账户-贷款
			channelTypes.add("0404");
			//基金
			channelTypes.add("050201");
			//券商集合计划
			channelTypes.add("050202");
			//信托
			channelTypes.add("050203");
			//定投
			channelTypes.add("050204");
			//理财
			channelTypes.add("0501");
			//保险
			channelTypes.add("0503");
			//结售汇
			channelTypes.add("0504");
			//实物贵金属
			channelTypes.add("0505");
		}
		else if("040".equals(value)){
			channelTypes = new ArrayList<String>();
			//签约
			channelTypes.add("020302");
			//短信口令
			channelTypes.add("020303");
			//信用卡绑定还款
			channelTypes.add("0204");
			//代扣代缴
			channelTypes.add("0205");
			//贵金属买卖
			channelTypes.add("0206");
			//超级网银
			channelTypes.add("0207");
			//跨行通
			channelTypes.add("0208");
			//新开借记卡
			channelTypes.add("0301");
			//新开存折
			channelTypes.add("0302");
			//新开账户-定期存款
			channelTypes.add("0401");
			//新开账户-凭证式国债
			channelTypes.add("0402");
			//新开账户-电子式国债
			channelTypes.add("0403");
			//新开账户-贷款
			channelTypes.add("0404");
			//基金
			channelTypes.add("050201");
			//券商集合计划
			channelTypes.add("050202");
			//信托
			channelTypes.add("050203");
			//定投
			channelTypes.add("050204");
			//理财
			channelTypes.add("0501");
			//保险
			channelTypes.add("0503");
			//结售汇
			channelTypes.add("0504");
			//实物贵金属
			channelTypes.add("0505");
			/* add by sunliulin  20150819  HCE配套改造 */
			channelTypes.add("020307");
		}
		else if("090".equals(value)){
			channelTypes = new ArrayList<String>();
			//签约
			channelTypes.add("020304");
		}
	}                                                                                                               
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public boolean isValid(String channelType){
		return channelTypes.contains(channelType);
	}
	public static CRM_CHANNEL getByValue(String value){
		if("050".equals(value)){
			return CHANNEL_PSBANK;
		}
		else if("040".equals(value)){
			return CHANNEL_MBANK;
		}
		else if("060".equals(value)){
			return CHANNEL_PBANK;
		}
		else if("080".equals(value)){
			return CHANNEL_SBANK;
		}
		else if("090".equals(value)){
			return CHANNEL_WBANK;
		}
		else{
			return null;
		}
	}
}
