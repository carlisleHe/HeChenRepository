package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 查询有效凭证代号
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr2169")
@OutgoingService
	(
		trCode = "2169" 
		,fixTxnHeader = false
	)
public class Tr2169
		extends
		TxnOutgoingService<Tr2169.Tr2169Request, Tr2169.Tr2169Response> {

	public Tr2169() {
		super(Tr2169Request.class, Tr2169Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr2169Request extends TxnRequest {

		public Tr2169Request() {
			super("2169");
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
         * 
         * Length: 8
         */
		@TxnColumn (column = "ZDDH" )
		public java.lang.String zddh;		
        /**
         * 前台流水号
         * Length: 16
         */
		@TxnColumn (column = "QTLSH" )
		public java.lang.String qtlsh;		
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
         * 接口版本
         * Length: 2
         */
		@TxnColumn (column = "JKBB" )
		public java.lang.String jkbb;		
        /**
         * 交易保留头
         * Length: 20
         */
		@TxnColumn (column = "JYTBL" )
		public java.lang.String jytbl;		
        /**
         * 账户代号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 处理方式
         * Length: 1
         */
		@TxnColumn (column = "CLFS" )
		public java.lang.String clfs;		
	}

	@SuppressWarnings("serial")
	public static class Tr2169Response extends TxnResponse {

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
         * 响应码
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
         * 凭证代号
         * Length: 9
         */
		@TxnColumn (column = "PZDH" )
		public java.lang.String pzdh;		
        /**
         * 凭证代号1
         * Length: 9
         */
		@TxnColumn (column = "PZDH1" )
		public java.lang.String pzdh1;		
        /**
         * 凭证试用状态
         * Length: 1
         */
		@TxnColumn (column = "PZSYZT" )
		public java.lang.String pzsyzt;		
	}
}
