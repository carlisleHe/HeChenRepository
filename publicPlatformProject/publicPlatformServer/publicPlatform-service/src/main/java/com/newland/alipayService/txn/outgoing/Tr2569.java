package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 交易明细查询-最近10笔
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr2569")
@OutgoingService
	(
		trCode = "2569" 
		,fixTxnHeader = false
	)
public class Tr2569
		extends
		TxnOutgoingService<Tr2569.Tr2569Request, Tr2569.Tr2569Response> {

	public Tr2569() {
		super(Tr2569Request.class, Tr2569Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr2569Request extends TxnRequest {

		public Tr2569Request() {
			super("2569");
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
         * 核心卡号
         * Length: 11
         */
		@TxnColumn (column = "KH" )
		public java.lang.String kh;		
        /**
         * 查询密码
         * Length: 6
         */
		@TxnColumn (column = "MM" )
		public java.lang.String mm;		
        /**
         * 起始序号
         * Length: 8
         */
		@TxnColumn (column = "QSXH" )
		public java.lang.String qsxh;		
        /**
         * 请求笔数
         * Length: 3
         */
		@TxnColumn (column = "QQBS" )
		public java.lang.String qqbs;		
	}

	@SuppressWarnings("serial")
	public static class Tr2569Response extends TxnResponse {

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
         * 客户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHXM" )
		public java.lang.String khxm;		
        /**
         * 客户卡号
         * Length: 11
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "JYMX", contentClass=java.util.ArrayList.class, targetClass=TrJYMXResponse.class )		
		public List<TrJYMXResponse> JYMXResponses;
		public static class TrJYMXResponse extends TxnResponse {
	
			public TrJYMXResponse(){
				super("2569");
			}
        /**
         * 交易时间
         * Type: DATETIME
         */
		@TxnColumn (column = "JYRQ" )
		public java.util.Date jyrq;				
        /**
         * 借贷标记
         * Length: 1
         */
		@TxnColumn (column = "JDBJ" )
		public java.lang.String jdbj;				
        /**
         * 交易金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JYJE" )
		public java.math.BigDecimal jyje;				
        /**
         * 账户余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "ZHYE" )
		public java.math.BigDecimal zhye;				
        /**
         * 交易柜员
         * Length: 4
         */
		@TxnColumn (column = "JYGY" )
		public java.lang.String jygy;				
        /**
         * 摘要代号
         * Length: 3
         */
		@TxnColumn (column = "ZYDH" )
		public java.lang.String zydh;				
        /**
         * 备注信息
         * Length: 12
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;				
		}
		
		public static TrJYMXResponse newTrJYMXResponse(){
			return new TrJYMXResponse();
		}

	}
}
