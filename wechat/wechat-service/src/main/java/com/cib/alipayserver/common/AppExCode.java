package com.cib.alipayserver.common;

/**
 * 系统错误码定义<br>
 * 变量命名规则:<br>
 * 1. 格式为"模块_释义" 。<br>
 * 2. 模块名应缩写，且应遵循缩写表。<br>
 * 3. 单词之间以下划线分隔。<br>
 * 
 * @author yelm
 * @since 2011-03-02
 */
public class AppExCode {
	// ==================================================
	// 平台级错误，代码范围10000-19999
	// ==================================================
	/** 未知错误 */
	public static final String UNKNOWN = "10000";
	/** 必输字段不能为空 */
	public static final String NOT_NULL = "10001";
	/** 输入内容不正确 */
	public static final String ERR_INPUT = "10002";
	/**信息格式化错误*/
	public static final String MSG_FORMAT_ERROR = "10003";

	// ==== 交易适配层，代码范围10200-10299 ====
	/** 外围系统交易超时 */
	public static final String TXN_TIME_OUT = "10200";
	/** 外围系统交易异常 */
	public static final String TXN_EXCEPTION = "10201";
	/** 向外围系统发送文件失败 */
	public static final String TXN_PUT_FILE = "10202";
	/** 从外围系统获取文件失败 */
	public static final String TXN_GET_FILE = "10203";
	/***
	 * 发送加密机交易失败
	 */
	public static final String TXN_HSM_ERROR = "10204";

	//=================开放平台使用异常，范围10300-10399=======================
	/**
	 * 通用校验类错误
	 */
	public static final String CHECK_ERROR = "10300";
    
	/**
	* 无权访问系统
	*/
	public static final String DSPCH_NO_PERM="10301";
	
	/**
	 * 信用卡账单不存在
	 */
	public static final String CC_BILL_NOT_FOUND = "10302";
	
	/**
	 * 操作类型错误
	 */
	public static final String OPER_TYPE_ERROP="10303";
	
	/**
	 * 请求超时
	 */
	public static final String REQ_TIME_OUT = "10304";
	/**
	 * 请求参数不合法
	 */
	public static final String REQ_PARAM_ERROR = "10305";
	/**
	 * 借记卡交易明细不存在
	 */
	public static final String DC_DETAILS_NOT_FOUND = "10306";
	
	/**
	 * 信用卡未出账单不存在
	 */
	public static final String CC_UNSETTLE_BILL_NOT_FOUND = "10307";
	/**
	 * 用户授权认证错误
	 */
	public static final String AUTH_ERROR = "10308";
	
	/**
	 * 兴业宝账号不存在
	 */
	public static final String XIN_YE_BAO_NOT_FOUND = "10308";
	
	//===========================动户通知相关==============================//
	/**
	 * 动词通知模板加载失败
	 */
	public static final String TEMPLATE_LOAD_ERR = "10400";
	
	
	 //===================文件下载,代码范围 21001-22000====================
    /**令牌失效**/
    public static final String TOKEN_INVALID = "21001";
    /***文件不存在**/
    public static final String FILE_NOT_FOUND = "21002";
    /***任务已在运行***/
    public static final String JOB_RUNNING_ERROR = "21003";
    /***文件压缩失败***/
    public static final String ZIP_FILE_ERROR = "21004";
}
