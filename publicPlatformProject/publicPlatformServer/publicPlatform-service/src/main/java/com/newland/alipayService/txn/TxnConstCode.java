package com.newland.alipayService.txn;

/**
 * 定义交易产量表
 * @author dvlp
 *
 */
public class TxnConstCode {
	/**
	 * 交易代码(0-成功)
	 */
    public static int MSGID = 0;
    
    /**
     * 重复次数(报文中存在重复)
     */
    public static int CFCS = 0;
    
    /**
     * 柜员流水号
     */
    public static  String GYLS="00000001";

    /**
     * 交易跟踪号
     */
    public static String JYGZH = "0000000000000001";
	
    /**
	 * 监听器发送错误(不是业务错误号)
	 */
    public static int ERR_ID = 88888;
	
    /**
	 * 错误信息
	 */
    public static String ERR_MSG="服务发生故障,请您联系300800";
}
