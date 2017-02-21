package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 客户理财产品信息查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr5205")
@OutgoingService
	(
		trCode = "5205" 
		,fixTxnHeader = false
	)
public class Tr5205
		extends
		TxnOutgoingService<Tr5205.Tr5205Request, Tr5205.Tr5205Response> {

	public Tr5205() {
		super(Tr5205Request.class, Tr5205Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr5205Request extends TxnRequest {

		public Tr5205Request() {
			super("5205");
		}
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
         * 前台流水号:为前置系统冲正方式保留
         * Length: 16
         */
		@TxnColumn (column = "ZDLSH" )
		public java.lang.String zdlsh;		
        /**
         * 统一授权柜员:为使用统一授权保留 
         * Length: 4
         */
		@TxnColumn (column = "SQGY" )
		public java.lang.String sqgy;		
        /**
         * 统一授权密码:为使用统一授权保留
         * Length: 16
         */
		@TxnColumn (column = "SQMM" )
		public java.lang.String sqmm;		
        /**
         * 附加渠道种类
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 上传重复次数:上传包多笔的笔数 
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "SCCFCS" )
		public java.lang.Integer sccfcs;		
        /**
         * 接口版本:为一个交易使用不同的接口使用,与后台定义的接口中的版本号相对应,其版本从'01'开始递增
         * Length: 2
         */
		@TxnColumn (column = "JKBB" )
		public java.lang.String jkbb;		
        /**
         * 交易头保留:为扩展交易的保留字段,此数据项可以将来用做多个字段使用
         * Length: 20
         */
		@TxnColumn (column = "JYTBL" )
		public java.lang.String jytbl;		
        /**
         * 5202填FP_U,5203填FP_U,5204填FP_I,5205填FP_I,5217填FP_I
         * Length: 15
         */
		@TxnColumn (column = "SVCNM" )
		public java.lang.String svcnm;		
        /**
         * 固定送0
         * Length: 1
         */
		@TxnColumn (column = "HCODE" )
		public java.lang.String hcode;		
        /**
         * 系统跟踪号
         * Length: 23
         */
		@TxnColumn (column = "F4XTGZH" )
		public java.lang.String f4xtgzh;		
        /**
         * 
         * Length: 1
         */
		@TxnColumn (column = "JYZL" )
		public java.lang.String jyzl;		
        /**
         * 
         * Length: 1
         */
		@TxnColumn (column = "DYBZ" )
		public java.lang.String dybz;		
        /**
         * 
         * Length: 1
         */
		@TxnColumn (column = "FWFS" )
		public java.lang.String fwfs;		
        /**
         * 
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 
         * Length: 7
         */
		@TxnColumn (column = "JYC" )
		public java.lang.String jyc;		
        /**
         * 
         * Length: 9
         */
		@TxnColumn (column = "PZDH" )
		public java.lang.String pzdh;		
        /**
         * 
         * Length: 6
         */
		@TxnColumn (column = "MM" )
		public java.lang.String mm;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "CPDM" )
		public java.lang.String cpdm;		
        /**
         * 
         * Type: DATE
         */
		@TxnColumn (column = "QSRQ" )
		public java.util.Date qsrq;		
        /**
         * 
         * Type: DATE
         */
		@TxnColumn (column = "ZZRQ" )
		public java.util.Date zzrq;		
	}

	@SuppressWarnings("serial")
	public static class Tr5205Response extends TxnResponse {

        /**
         * 交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM" )
		public java.lang.String jydm;		
        /**
         * 交易日期
         * Length: 8
         */
		@TxnColumn (column = "JYRQ" )
		public java.lang.String jyrq;		
        /**
         * 交易时间
         * Length: 6
         */
		@TxnColumn (column = "JYSJ" )
		public java.lang.String jysj;		
        /**
         * 0表示交易成功
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "MSGID" )
		public java.lang.Integer msgid;		
        /**
         * 下传重复次数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "XCCFCS" )
		public java.lang.Integer xccfcs;		
        /**
         * 交易跟踪号
         * Length: 16
         */
		@TxnColumn (column = "JYGZH" )
		public java.lang.String jygzh;		
        /**
         * 柜员流水号
         * Length: 8
         */
		@TxnColumn (column = "GYLSH" )
		public java.lang.String gylsh;		
        /**
         * 报文结束标志 对柜面无用,仅接收,不处理
         * Length: 1
         */
		@TxnColumn (column = "MSGEND" )
		public java.lang.String msgend;		
        /**
         * 报文长度  对柜面无用,仅接收,不处理
         * Length: 4
         */
		@TxnColumn (column = "MSGLNG" )
		public java.lang.String msglng;		
        /**
         * 
         * Length: 30
         */
		@TxnColumn (column = "INFILE" )
		public java.lang.String infile;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "WJJYM" )
		public java.lang.String wjjym;		
	}
}
