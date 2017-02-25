package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 账户认证查询服务交易
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr8881")
@OutgoingService
	(
		trCode = "8881" 
		,fixTxnHeader = false
	)
public class Tr8881
		extends
		TxnOutgoingService<Tr8881.Tr8881Request, Tr8881.Tr8881Response> {

	public Tr8881() {
		super(Tr8881Request.class, Tr8881Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr8881Request extends TxnRequest {

		public Tr8881Request() {
			super("8881");
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
         * 
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 系统编号
         * Length: 6
         */
		@TxnColumn (column = "XTBH" )
		public java.lang.String xtbh;		
        /**
         * 账户代号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 服务种类 0、全部1、证书 2、短信 3、令牌
         * Length: 1
         */
		@TxnColumn (column = "FWZL" )
		public java.lang.String fwzl;		
	}

	@SuppressWarnings("serial")
	public static class Tr8881Response extends TxnResponse {

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
         * 
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "MSGID1" )
		public java.lang.Integer msgid1;		
        /**
         * 
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "CFCS1" )
		public java.lang.Integer cfcs1;		
        /**
         * 
         * Type: DATE
         */
		@TxnColumn (column = "JYRQ1" )
		public java.util.Date jyrq1;		
        /**
         * 
         * Length: 6
         */
		@TxnColumn (column = "JYSJ1" )
		public java.lang.String jysj1;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "GYLSH1" )
		public java.lang.String gylsh1;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "KTMX", contentClass=java.util.ArrayList.class, targetClass=TrKTMXResponse.class )		
		public List<TrKTMXResponse> KTMXResponses;
		public static class TrKTMXResponse extends TxnResponse {
	
			public TrKTMXResponse(){
				super("8881");
			}
        /**
         * 认证工具编号
         * Length: 32
         */
		@TxnColumn (column = "RZGJBH" )
		public java.lang.String rzgjbh;				
        /**
         * 服务种类1、证书 2、短信 3、令牌
         * Length: 1
         */
		@TxnColumn (column = "FWZL" )
		public java.lang.String fwzl;				
        /**
         * 0 文件证书, 1 USB证书 ，2 一代预置证书，3 二代预置证书
         * Length: 1
         */
		@TxnColumn (column = "FWZLX" )
		public java.lang.String fwzlx;				
		}
		
		public static TrKTMXResponse newTrKTMXResponse(){
			return new TrKTMXResponse();
		}

	}
}
