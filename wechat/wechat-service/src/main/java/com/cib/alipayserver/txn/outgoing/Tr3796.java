package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 互联网渠道批量进度查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3796")
@OutgoingService
	(
		trCode = "3796" 
		,fixTxnHeader = false
	)
public class Tr3796
		extends
		TxnOutgoingService<Tr3796.Tr3796Request, Tr3796.Tr3796Response> {

	public Tr3796() {
		super(Tr3796Request.class, Tr3796Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3796Request extends TxnRequest {

		public Tr3796Request() {
			super("3796");
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
         * 交易日期
         * Type: DATE
         */
		@TxnColumn (column = "JYRQ" )
		public java.util.Date jyrq;		
        /**
         * 互联网申请来源
         * Length: 10
         */
		@TxnColumn (column = "HLWSQLY" )
		public java.lang.String hlwsqly;		
	}

	@SuppressWarnings("serial")
	public static class Tr3796Response extends TxnResponse {

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
         * 文件名称
         * Length: 100
         */
		@TxnColumn (column = "wjmc" )
		public java.lang.String wjmc;		
        /**
         * 文件校验码
         * Length: 8
         */
		@TxnColumn (column = "wjjym" )
		public java.lang.String wjjym;		
        /**
         * 互联网申请来源
         * Length: 10
         */
		@TxnColumn (column = "HLWSQLY" )
		public java.lang.String hlwsqly;		
        /**
         * 申请来源唯一编号
         * Length: 40
         */
		@TxnColumn (column = "SQLYWYBH" )
		public java.lang.String sqlywybh;		
        /**
         * 申请进度;0-已受理、1-审核中、2-已审批、3-拒绝、4-取消
         * Length: 1
         */
		@TxnColumn (column = "SQJD" )
		public java.lang.String sqjd;		
        /**
         * 审批金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "SPJE" )
		public java.math.BigDecimal spje;		
	}
}
