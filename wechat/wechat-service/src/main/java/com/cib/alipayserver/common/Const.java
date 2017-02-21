package com.cib.alipayserver.common;

import java.util.Date;

import com.intensoft.system.SystemProperty;

/**
 * 全局静态常量
 * 
 * @author yelm
 * @since 2011-01-07
 */
public class Const {
	/** 通讯密钥的DES密钥，不要超过8位，勿修改 */
	public static final String ENC_DES_KEY = "openbank";

	/** 通讯密钥的DES密钥，不要超过8位，勿修改 */
	public static final boolean RUN_TEST = new Boolean(
			System.getProperty("run_test"));
	/**
	 * 微信ID的前缀是写死的
	 */
	public static final String WEIXIN_PREFIX = "WX";
	
	public static final String OPEN_ID = "openId";
	/**
	 * 短信验证码
	 */
	public static final String IDENTIFY = "identify";
	/**授权领红包页面*/
	public static final String SEND_RED_PACKET = "sendRedPacket";
	/** 默认查询页面大小 */
	public static final int DEF_PAGE_SIZE = 30;
	
	/** 重发状态：0-待重发 */
	public static final int RESEND_STATUS_PENDING = 0;
	/** 重发状态：1-已重发（已成功重发） */
	public static final int RESEND_STATUS_SUCCESS = 1;
	/** 重发状态：2-已过期（多次重发失败） */
	public static final int RESEND_STATUS_EXPIRED = 2;

	/** 交易状态：0-待发起 */
	public static final int TRANS_STATUS_WAITING = 0;
	/** 交易状态：1-成功 */
	public static final int TRANS_STATUS_SUCCESS = 1;
	/** 交易状态：2-失败 */
	public static final int TRANS_STATUS_FAIL = 2;
	/** 交易状态：3-未决 */
	public static final int TRANS_STATUS_PENDING = 3;
	/** 交易状态：4-撤销 */
	public static final int TRANS_STATUS_CANCEL = 4;
	/** 交易状态：5-部分成功 */
	public static final int TRANS_STATUS_PARTSUCC = 5;

	public static final String FILE_PATH_TEMP = "temp/";
	
	/** 通知文件路径 */
	public static final String FILE_PATH_CORE_NOTIFY = SystemProperty
			.getInstance().getSystProperty("FILE_PATH_CORE_NOTIFY");

	/** 文件名后缀 */
	public static final String FILE_SUFFIX = ".txt";
	
	/** 全局session常量：账户*/
	public static final String GLOBAL_SESSION_ACCOUNT = "account";
	/** 全局session常量：渠道*/
	public static final String GLOBAL_SESSION_CHANNEL = "channel";
	/** 全局session常量：手机号*/
	public static final String GLOBAL_SESSION_MOBILE = "mobile";
	/** 全局session常量：短信校验码*/
	public static final String GLOBAL_SESSION_MSG = "message_code";
	/** 全局session常量：短信校验码已出错次数*/
	public static final String GLOBAL_SESSION_MSGERRTIMES = "msgNoErrNum";
	/** 全局session常量：凭证代号*/
	public static final String GLOBAL_SESSION_INVOICENO = "invoiceNo";
	/** 全局session常量：商户配置*/
	public static final String GLOBAL_SESSION_MRCHCONFIG = "config";
	/** 全局session常量：商户签约信息*/
	public static final String GLOBAL_SESSION_MRCHCONTRACT = "contracts";
	/** 全局session常量：在线签约Request */
	public static final String GLOBAL_SESSION_REQUEST = "requestParameter";
	/** 全局session常量：在线签约记录 */
	public static final String GLOBAL_SESSION_COLLSIGNLOG = "collSignLog";
	/** 全局session常量：账户列表*/
	public static final String GLOBAL_SESSION_ACCTLIST = "global_acctList";
	/** 全局session常量：网银托收签约账户*/
	public static final String GLOBAL_SESSION_COLLSIGNACCOUNT = "collsign_acct";
	/** 全局session常量：格式化卡号*/
	public static final String GLOBAL_SESSION_MASKACCTNO = "mask_acctNo";
	/** 全局session常量：自主分期配置信息 */

	/****
	 * 统一的外接商户渠道号
	 */
	public static final String MRCH_APPID = "999999";
	/***
	 * 系统的超级管理员
	 */
	public static final String ADMIN_USER = "admin";
	/***
	 * 用户容器
	 */
	public static final String USER_CONTAINER  = "userContainer";
	
	/***
	 * Z02报文流水号头
	 */
	public static final String Z02_SNO_HEAD = "Z00008";
	/***
	 * 币种（人民币）
	 */
	public static final String CUR_CNY = "CNY";

	/**
	 * 所有卡
	 */
	public static final String ACCT_TYPE_ALL = "0";
	/**
	 * 借记卡类型
	 */
	public static final String ACCT_TYPE_DEBIT = "1";
	/**
	 * 信用卡类型
	 */
	public static final String ACCT_TYPE_CREDIT = "2";

	/**
	 * 状态有效
	 */
	public static final String STATUS_VALID = "1";
	/**
	 * 状态无效
	 */
	public static final String STATUS_INVALID = "0";
	/**
	 * 借记卡动户通知
	 */
	public static final String MESSAGE_TYPE_BALANCE = "00";
	/**
	 * 营销通知
	 */
	public static final String MESSAGE_TYPE_MARKETING = "01";
	/**
	 * 信用卡消费通知
	 */
	public static final String MESSAGE_TYPE_CREDIT = "02";

	/**
	 * 待发起
	 */
	public static final String STATUS_WAIT = "0";
	/**
	 * 成功
	 */
	public static final String STATUS_SUCCESS = "1";
	/**
	 * 失败
	 */
	public static final String STATUS_FAIL = "2";
	/**
	 * 未决
	 */
	public static final String STATUS_PEND = "3";
	/**
	 * 绑定授权信息
	 */
	public static final int AUTH_LOG_BIND = 1;
	/**
	 * 解绑授权信息
	 */
	public static final int AUTH_LOG_UNBIND = 0;
	/**
	 * 客户授权
	 */
	public static final String  AUTH_TYPE_CUSTOMER = "1";
	/**
	 * 卡授权
	 */
	public static final String  AUTH_TYPE_CARD = "2";
	/**
	 * 通知授权
	 */
	public static final String  AUTH_TYPE_NOTIFY = "3";
	/** 
	 * 信用卡非正常状态标志 
	 */
	public static final String CC_INVLID_STATUS = "CDHQRV";

	/**
	 * 格林尼治时间 January 1, 1970, 00:00:00 GMT
	 */
	public static final Date ZERO_DATE = new Date(0l);
	
	/**
	 * 操作类型 增加卡
	 */
	public static final String OPER_TYPE_ADD="ADD";
	
	/**
	 * 操作类型 管理卡
	 */
	public static final String OPER_TYPE_MAN="MAN";

	/**
	 * 卡片绑定
	 */
	public static final String CARD_BIND = "1";
	/**
	 * 卡片解绑
	 */
	public static final String CARD_UNBIND = "0";
	/**
	 * 应用配置公钥
	 */
	public static final String CFG_KEY_APP_SECRET = "ckey";
	/**
	 * 应用配置回调地址
	 */
	public static final String CFG_KEY_CB_URL = "cburl";
	/**
	 * 应用配置SCOPE
	 */
	public static final String CFG_KEY_SCOPE = "cscope";
	/**
	 * 应用ID前缀
	 */
	public static final String APP_ID_PREFIX_WEIXIN = "WX";
	/**
	 * 需认证用户才可访问的scope
	 */
	public static final String SCOPE_TYPE_AUTHENTICATION = "1";
	/**
	 * 公开SCOPE
	 */
	public static final String SCOPE_TYPE_OPEN = "0";
	/**
	 * 半公开SCOPE
	 */
	public static final String SCOPE_TYPE_LOW_OPEN = "2";
	
	/**
	 * 用户位置信息
	 */
	public static final String USER_LOCATION = "LOCATION";
	/**
	 * 设置信用卡密码
	 */
	public static final String SET_CC_PASSWORD = "setccpassword";
	/**
	 * 默认的消息模板。
	 */
	public static final String DEFAULT_MSG_TEMPLATE="default";
	
	
	/**
	 * 微信银行错误码前缀
	 */
	public final static String ERR_CODE_PREFIX ="WX";
	/**
	 * 微信银行incoming交易错误码前缀
	 */
	public final static String ERR_INCOMING_CODE_PREFIX = "2";
	
	/**
	 * 微信银行incoming交易错误码位数
	 */
	public final static int ERR_CODE_LENGTH = 5;
	/**
	 * 用于开放互联app体系中识别本系统
	 */
	public final static String APPID_SELF = "_self";
	/**
	 * 黑名单类型;oauth登录认证
	 */
	public final static String BLACK_LIST_OAUTH_LOGIN = "02"; 
	
	public static final String DES_KEY = "opkey";
	/**
	 * 零售系统异常信息前缀
	 */
	public static final String SALE_TXN_ERR_PREFIX = "9";
	/**
	 * 开发环境
	 */
	public static final String RUN_ENV_DEV = "dev";
	/**
	 * 集成环境
	 */
	public static final String RUN_ENV_INTE = "inte";
	/**
	 * 生产环境
	 */
	public static final String RUN_ENV_PROD = "prod";
	
	/**
	 * 营销消息状态
	 * 状态：待处理
	 */
	public static final String Mkt_Msg_STATUS = "03";

	/**
	 * 验证码
	 */
	public static final String GLOBAL_SESSION_NEEDCAPTCHA = "needCaptcha";

	/**
	 * 信用卡默认地区号
	 */
	public static final String CC_DEFAULT_BRANCH = "06";

	/**
	 * 信用卡默认机构号
	 */
	public static final String CC_DEFAULT_SUBBRANCH = "101";
	
	/**
	 * 产品代码
	 */
	public static final String PROD_CODE = "000000621";
	
	/**
	 * 大成的基金代码
	 */
	public static final String DC_FUND_CODE = "090022";
	
	/**
	 * 兴业的基金代码
	 */
	public static final String XY_FUND_CODE = "000721";
	
	/**
	 * 前台系统编号
	 * 个人网银：Z00008
	 * 微信:Z00062
	 */
	public static final String QTXTBH = "Z00062";
	/**
	 * 身份证正面
	 */
	public static final String PIC_TYPE_FRONT = "A01";
	/**
	 * 身份证反面
	 */
	public static final String PIC_TYPE_BACK = "A02";
	/**
	 * 身份证头像
	 */
	public static final String PIC_TYPE_HEAD = "A03";
	/**
	 * 申请状态-新建
	 */
	public static final String APPLY_STATUS_NEW = "1";
	/**
	 * 申请状态-审核中
	 */
	public static final String APPLY_STATUS_CHECK = "2";
	/**
	 * 申请状态-修订中
	 */
	public static final String APPLY_STATUS_MODIFY = "3";
	/**
	 * 申请状态-开户中
	 */
	public static final String APPLY_STATUS_APPLY = "4";
	/**
	 * 申请状态-等待回访
	 */
	public static final String APPLY_STATUS_WAIT_VISIT = "5";
	/**
	 * 申请状态-回访结束
	 */
	public static final String APPLY_STATUS_VISIT_END = "6";
	/**
	 * 申请状态-失败
	 */
	public static final String APPLY_STATUS_FAIL = "7";
	/**
	 * 申请状态-成功
	 */
	public static final String APPLY_STATUS_SUCCESS = "8";
}
