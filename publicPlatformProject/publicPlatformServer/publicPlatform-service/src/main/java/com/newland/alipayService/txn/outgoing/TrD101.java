package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 账户信息查询
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("trD101")
@OutgoingService
	(
		trCode = "D101" 
	)
public class TrD101
		extends
		TxnOutgoingService<TrD101.TrD101Request, TrD101.TrD101Response> {

	public TrD101() {
		super(TrD101Request.class, TrD101Response.class);
	}

	@SuppressWarnings("serial")
	public static class TrD101Request extends TxnRequest {

		public TrD101Request() {
			super("D101");
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
         * 用户证件类型
         * Length: 1
         */
		@TxnColumn (column = "ZJLX" )
		public java.lang.String zjlx;		
        /**
         * 用户证件号码
         * Length: 32
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 银行卡号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
	}

	@SuppressWarnings("serial")
	public static class TrD101Response extends TxnResponse {

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
         * 银行卡号
         * Length: 32
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 用户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHMC" )
		public java.lang.String khmc;		
        /**
         * 用户证件类型
         * Length: 1
         */
		@TxnColumn (column = "ZJLX" )
		public java.lang.String zjlx;		
        /**
         * 用户证件号码
         * Length: 32
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 用户手机
         * Length: 11
         */
		@TxnColumn (column = "KHSJ" )
		public java.lang.String khsj;		
        /**
         * 总记录数
         * Type: NUMBER_08_0
         */
		@TxnColumn (column = "ZJLS" )
		public java.lang.Integer zjls;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "KHMX", contentClass=java.util.ArrayList.class, targetClass=TrKHMXResponse.class )		
		public List<TrKHMXResponse> KHMXResponses;
		public static class TrKHMXResponse extends TxnResponse {
	
			public TrKHMXResponse(){
				super("D101");
			}
        /**
         * 基金代码
         * Length: 6
         */
		@TxnColumn (column = "JJDM" )
		public java.lang.String jjdm;				
        /**
         * 基金名称
         * Length: 60
         */
		@TxnColumn (column = "JJMC" )
		public java.lang.String jjmc;				
        /**
         * 基金别名（页面展示用）
         * Length: 60
         */
		@TxnColumn (column = "JJBM" )
		public java.lang.String jjbm;				
		}
		
		public static TrKHMXResponse newTrKHMXResponse(){
			return new TrKHMXResponse();
		}

	}
}
