package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 查询账户资料
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3502")
@OutgoingService
	(
		trCode = "3502" 
		,fixTxnHeader = false
	)
public class Tr3502
		extends
		TxnOutgoingService<Tr3502.Tr3502Request, Tr3502.Tr3502Response> {

	public Tr3502() {
		super(Tr3502Request.class, Tr3502Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3502Request extends TxnRequest {

		public Tr3502Request() {
			super("3502");
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
         * 起始记录号
         * Type: NUMBER_08_0
         */
		@TxnColumn (column = "STREC" )
		public java.lang.Integer strec;		
        /**
         * 查询记录数
         * Type: NUMBER_03_0
         */
		@TxnColumn (column = "RECNU" )
		public java.lang.Integer recnu;		
        /**
         * 授权柜员
         * Length: 4
         */
		@TxnColumn (column = "SQGY" )
		public java.lang.String sqgy;		
        /**
         * 服务方式-1对客户，2对柜员
         * Length: 1
         */
		@TxnColumn (column = "FWFS" )
		public java.lang.String fwfs;		
        /**
         * 卡号
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
         * 业务代号－4:理财账户
         * Length: 3
         */
		@TxnColumn (column = "YW" )
		public java.lang.String yw;		
        /**
         * 货币种类－1人民币，14美元，13港币，27日元，00全部
         * Length: 2
         */
		@TxnColumn (column = "HBZL" )
		public java.lang.String hbzl;		
	}

	@SuppressWarnings("serial")
	public static class Tr3502Response extends TxnResponse {

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
         * 客户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHXM" )
		public java.lang.String khxm;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "ZZHXX", contentClass=java.util.ArrayList.class, targetClass=TrZZHXXResponse.class )		
		public List<TrZZHXXResponse> ZZHXXResponses;
		public static class TrZZHXXResponse extends TxnResponse {
	
			public TrZZHXXResponse(){
				super("3502");
			}
        /**
         * 小序号
         * Length: 3
         */
		@TxnColumn (column = "XXH" )
		public java.lang.String xxh;				
        /**
         * 产品名称
         * Length: 40
         */
		@TxnColumn (column = "CPMC" )
		public java.lang.String cpmc;				
        /**
         * 账户代号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;				
        /**
         * 业务代号
         * Length: 3
         */
		@TxnColumn (column = "YWDH" )
		public java.lang.String ywdh;				
        /**
         * 货币种类
         * Length: 2
         */
		@TxnColumn (column = "HBZL" )
		public java.lang.String hbzl;				
        /**
         * 钞汇标志
         * Length: 1
         */
		@TxnColumn (column = "CHBZ" )
		public java.lang.String chbz;				
        /**
         * 帐户余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "ZHYE" )
		public java.math.BigDecimal zhye;				
        /**
         * 可用余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "KYYE" )
		public java.math.BigDecimal kyye;				
        /**
         * 存款期限
         * Length: 3
         */
		@TxnColumn (column = "CKQX" )
		public java.lang.String ckqx;				
        /**
         * 续存存期
         * Length: 3
         */
		@TxnColumn (column = "XCCQ" )
		public java.lang.String xccq;				
        /**
         * 开户日期
         * Type: DATE
         */
		@TxnColumn (column = "KHRQ" )
		public java.util.Date khrq;				
        /**
         * 开户金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "KHJE" )
		public java.math.BigDecimal khje;				
        /**
         * 到期日期
         * Type: DATE
         */
		@TxnColumn (column = "DQRQ" )
		public java.util.Date dqrq;				
        /**
         * 是否计息标志
         * Length: 1
         */
		@TxnColumn (column = "SFJXBZ" )
		public java.lang.String sfjxbz;				
        /**
         * 记录状态
         * Length: 1
         */
		@TxnColumn (column = "JLZT" )
		public java.lang.String jlzt;				
        /**
         * 部分支取次数
         * Length: 5
         */
		@TxnColumn (column = "BZCS" )
		public java.lang.String bzcs;				
		}
		
		public static TrZZHXXResponse newTrZZHXXResponse(){
			return new TrZZHXXResponse();
		}

	}
}
