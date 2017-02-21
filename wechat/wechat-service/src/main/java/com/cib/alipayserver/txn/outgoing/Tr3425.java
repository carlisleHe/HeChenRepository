package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡账户查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3425")
@OutgoingService
	(
		trCode = "3425" 
		,fixTxnHeader = false
	)
public class Tr3425
		extends
		TxnOutgoingService<Tr3425.Tr3425Request, Tr3425.Tr3425Response> {

	public Tr3425() {
		super(Tr3425Request.class, Tr3425Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3425Request extends TxnRequest {

		public Tr3425Request() {
			super("3425");
		}
        /**
         * 交易头版本号
         * Length: 3
         */
		@TxnColumn (column = "JYTBBH" )
		public java.lang.String jytbbh;		
        /**
         * 交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM" )
		public java.lang.String jydm;		
        /**
         * 地区代号
         * Length: 2
         */
		@TxnColumn (column = "DQDH" )
		public java.lang.String dqdh;		
        /**
         * 机构代号
         * Length: 3
         */
		@TxnColumn (column = "JGDH" )
		public java.lang.String jgdh;		
        /**
         * 交易柜员
         * Length: 4
         */
		@TxnColumn (column = "JYGY" )
		public java.lang.String jygy;		
        /**
         * 终端代号
         * Length: 8
         */
		@TxnColumn (column = "ZDDH" )
		public java.lang.String zddh;		
        /**
         * 前台交易流水号
         * Length: 30
         */
		@TxnColumn (column = "QTJYLSH" )
		public java.lang.String qtjylsh;		
        /**
         * 前台用户代号
         * Length: 10
         */
		@TxnColumn (column = "QTYHDH" )
		public java.lang.String qtyhdh;		
        /**
         * 前台交易编码
         * Length: 8
         */
		@TxnColumn (column = "QTJYBM" )
		public java.lang.String qtjybm;		
        /**
         * 前台业务套号
         * Length: 16
         */
		@TxnColumn (column = "QTYWTH" )
		public java.lang.String qtywth;		
        /**
         * 统一授权柜员
         * Length: 4
         */
		@TxnColumn (column = "TYSQGY" )
		public java.lang.String tysqgy;		
        /**
         * 统一授权密码
         * Length: 16
         */
		@TxnColumn (column = "TYSQMM" )
		public java.lang.String tysqmm;		
        /**
         * 附加渠道种类
         * Length: 3
         */
		@TxnColumn (column = "FJQDZL" )
		public java.lang.String fjqdzl;		
        /**
         * 上传重复次数
         * Length: 4
         */
		@TxnColumn (column = "SCCFCS" )
		public java.lang.String sccfcs;		
        /**
         * 上传文件标志
         * Length: 1
         */
		@TxnColumn (column = "SCWJBZ" )
		public java.lang.String scwjbz;		
        /**
         * 上传文件名
         * Length: 128
         */
		@TxnColumn (column = "SCWJM" )
		public java.lang.String scwjm;		
        /**
         * 文件ID
         * Length: 8
         */
		@TxnColumn (column = "WJID" )
		public java.lang.String wjid;		
        /**
         * 接口版本
         * Length: 2
         */
		@TxnColumn (column = "JKBB" )
		public java.lang.String jkbb;		
        /**
         * 收费相关信息
         * Length: 24
         */
		@TxnColumn (column = "SFXGXX" )
		public java.lang.String sfxgxx;		
        /**
         * 客户密码加密方式
         * Length: 1
         */
		@TxnColumn (column = "KHMMJMFS" )
		public java.lang.String khmmjmfs;		
        /**
         * 交易保留头
         * Length: 20
         */
		@TxnColumn (column = "JYBLT" )
		public java.lang.String jyblt;		
        /**
         * 
         * Length: 4
         */
		@TxnColumn (column = "JYDM1" )
		public java.lang.String jydm1;		
        /**
         * 
         * Length: 2
         */
		@TxnColumn (column = "DQDH1" )
		public java.lang.String dqdh1;		
        /**
         * 
         * Length: 3
         */
		@TxnColumn (column = "JGDH1" )
		public java.lang.String jgdh1;		
        /**
         * 
         * Length: 4
         */
		@TxnColumn (column = "JYGY1" )
		public java.lang.String jygy1;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "ZDDH1" )
		public java.lang.String zddh1;		
        /**
         * 交易来源
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYKKH" )
		public java.lang.String xykkh;		
        /**
         * 是否校验密码 密码 0：否1：查询密码2:取款密码
         * Length: 1
         */
		@TxnColumn (column = "SFJYMM" )
		public java.lang.String sfjymm;		
        /**
         * 货币种类 01－人民币 14－美元
         * Length: 2
         */
		@TxnColumn (column = "HBZL" )
		public java.lang.String hbzl;		
        /**
         * 查询/取现密码
         * Length: 6
         */
		@TxnColumn (column = "XYKMM" )
		public java.lang.String xykmm;		
        /**
         * 账单年月
         * Length: 6
         */
		@TxnColumn (column = "QSNY" )
		public java.lang.String qsny;		
        /**
         * 是否打印 0－不打印(接口传) 1－打印(文件传)
         * Length: 1
         */
		@TxnColumn (column = "SFDY" )
		public java.lang.String sfdy;		
	}

	@SuppressWarnings("serial")
	public static class Tr3425Response extends TxnResponse {

        /**
         * 
         * Length: 3
         */
		@TxnColumn (column = "JYTBBH" )
		public java.lang.String jytbbh;		
        /**
         * 交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM" )
		public java.lang.String jydm;		
        /**
         * 交易日期
         * Type: DATE
         */
		@TxnColumn (column = "JYRQ" )
		public java.util.Date jyrq;		
        /**
         * 交易时间
         * Type: TIME
         */
		@TxnColumn (column = "JYSJ" )
		public java.util.Date jysj;		
        /**
         * 信息标识
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "MSGID" )
		public java.lang.Integer msgid;		
        /**
         * 下传重复次数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "CFCS" )
		public java.lang.Integer cfcs;		
        /**
         * 核心交易流水编号
         * Length: 30
         */
		@TxnColumn (column = "HXJYLSBH" )
		public java.lang.String hxjylsbh;		
        /**
         * 柜员流水
         * Length: 8
         */
		@TxnColumn (column = "GYLSH" )
		public java.lang.String gylsh;		
        /**
         * 下传文件标识
         * Length: 1
         */
		@TxnColumn (column = "XCWJBZ" )
		public java.lang.String xcwjbz;		
        /**
         * 下传文件名
         * Length: 128
         */
		@TxnColumn (column = "XCWJM" )
		public java.lang.String xcwjm;		
        /**
         * 文件ID
         * Length: 8
         */
		@TxnColumn (column = "WJID" )
		public java.lang.String wjid;		
        /**
         * 交易保留头
         * Length: 20
         */
		@TxnColumn (column = "JYBLT" )
		public java.lang.String jyblt;		
        /**
         * 本期应还款
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JYJE" )
		public java.math.BigDecimal jyje;		
        /**
         * 上期账单金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE" )
		public java.math.BigDecimal je;		
        /**
         * 上期账单金额符号
         * Length: 1
         */
		@TxnColumn (column = "JEFH" )
		public java.lang.String jefh;		
        /**
         * 已还金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE1" )
		public java.math.BigDecimal je1;		
        /**
         * 本期账单金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE2" )
		public java.math.BigDecimal je2;		
        /**
         * 本期账单金额符号
         * Length: 1
         */
		@TxnColumn (column = "JEFH1" )
		public java.lang.String jefh1;		
        /**
         * 循环利息
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "LX" )
		public java.math.BigDecimal lx;		
        /**
         * 罚金
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE3" )
		public java.math.BigDecimal je3;		
        /**
         * 其他费用
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE4" )
		public java.math.BigDecimal je4;		
        /**
         * 预借现金金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE5" )
		public java.math.BigDecimal je5;		
        /**
         * 消费金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JYJE1" )
		public java.math.BigDecimal jyje1;		
        /**
         * 卡片费用
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ" )
		public java.math.BigDecimal jesz;		
        /**
         * 调整金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ1" )
		public java.math.BigDecimal jesz1;		
        /**
         * 调整金额符号
         * Length: 1
         */
		@TxnColumn (column = "JEFH2" )
		public java.lang.String jefh2;		
        /**
         * 最小还款额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ2" )
		public java.math.BigDecimal jesz2;		
        /**
         * 还款截止日期
         * Type: DATE
         */
		@TxnColumn (column = "ZZRQ" )
		public java.util.Date zzrq;		
        /**
         * 账单日期
         * Type: DATE
         */
		@TxnColumn (column = "RQ" )
		public java.util.Date rq;		
        /**
         * 账户姓名1
         * Length: 30
         */
		@TxnColumn (column = "ZWXM" )
		public java.lang.String zwxm;		
        /**
         * 账户姓名2
         * Length: 30
         */
		@TxnColumn (column = "YWXM" )
		public java.lang.String ywxm;		
        /**
         * 自扣还款
         * Length: 19
         */
		@TxnColumn (column = "ZKZH" )
		public java.lang.String zkzh;		
        /**
         * 自扣还款方式
         * Length: 1
         */
		@TxnColumn (column = "KKFS" )
		public java.lang.String kkfs;		
	}
}
