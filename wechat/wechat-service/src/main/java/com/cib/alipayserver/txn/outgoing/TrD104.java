package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 收益信息查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("trD104")
@OutgoingService
	(
		trCode = "D104" 
	)
public class TrD104
		extends
		TxnOutgoingService<TrD104.TrD104Request, TrD104.TrD104Response> {

	public TrD104() {
		super(TrD104Request.class, TrD104Response.class);
	}

	@SuppressWarnings("serial")
	public static class TrD104Request extends TxnRequest {

		public TrD104Request() {
			super("D104");
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
         * 内部交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM1" )
		public java.lang.String jydm1;		
        /**
         * 基金代码
         * Length: 6
         */
		@TxnColumn (column = "JJDM" )
		public java.lang.String jjdm;		
        /**
         * 起始日期
         * Type: DATE
         */
		@TxnColumn (column = "QSRQ" )
		public java.util.Date qsrq;		
        /**
         * 截止日期
         * Type: DATE
         */
		@TxnColumn (column = "JZRQ" )
		public java.util.Date jzrq;		
        /**
         * 查询页号
         * Type: NUMBER_08_0
         */
		@TxnColumn (column = "CXYH" )
		public java.lang.Integer cxyh;		
        /**
         * 查询记录数
         * Type: NUMBER_02_0
         */
		@TxnColumn (column = "CXJLS" )
		public java.lang.Integer cxjls;		
	}

	@SuppressWarnings("serial")
	public static class TrD104Response extends TxnResponse {

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
         * 总记录数
         * Type: NUMBER_08_0
         */
		@TxnColumn (column = "ZJLS" )
		public java.lang.Integer zjls;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "SYMX", contentClass=java.util.ArrayList.class, targetClass=TrSYMXResponse.class )		
		public List<TrSYMXResponse> SYMXResponses;
		public static class TrSYMXResponse extends TxnResponse {
	
			public TrSYMXResponse(){
				super("D104");
			}
        /**
         * 日期
         * Type: DATE
         */
		@TxnColumn (column = "RQ" )
		public java.util.Date rq;				
        /**
         * 万分收益
         * Type: NUMBER_15_4
         */
		@TxnColumn (column = "WFSY" )
		public java.math.BigDecimal wfsy;				
        /**
         * 七天年化收益率
         * Type: NUMBER_15_4
         */
		@TxnColumn (column = "QTNHSY" )
		public java.math.BigDecimal qtnhsy;				
		}
		
		public static TrSYMXResponse newTrSYMXResponse(){
			return new TrSYMXResponse();
		}

	}
}
