package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡设置交易密码
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr4614")
@OutgoingService
	(
		trCode = "4614" 
	)
public class Tr4614
		extends
		TxnOutgoingService<Tr4614.Tr4614Request, Tr4614.Tr4614Response> {

	public Tr4614() {
		super(Tr4614Request.class, Tr4614Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr4614Request extends TxnRequest {

		public Tr4614Request() {
			super("4614");
		}
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
		@TxnColumn (column = "JYDM0" )
		public java.lang.String jydm0;		
        /**
         * 地区代号
         * Length: 2
         */
		@TxnColumn (column = "DQDH0" )
		public java.lang.String dqdh0;		
        /**
         * 机构代号
         * Length: 3
         */
		@TxnColumn (column = "JGDH0" )
		public java.lang.String jgdh0;		
        /**
         * 交易柜员
         * Length: 4
         */
		@TxnColumn (column = "JYGY0" )
		public java.lang.String jygy0;		
        /**
         * 终端代号
         * Length: 8
         */
		@TxnColumn (column = "ZDDH0" )
		public java.lang.String zddh0;		
        /**
         * 
         * Length: 30
         */
		@TxnColumn (column = "QTJYLSH" )
		public java.lang.String qtjylsh;		
        /**
         * 
         * Length: 10
         */
		@TxnColumn (column = "QTYHDH" )
		public java.lang.String qtyhdh;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "QTJYBM" )
		public java.lang.String qtjybm;		
        /**
         * 
         * Length: 16
         */
		@TxnColumn (column = "QTYWTH" )
		public java.lang.String qtywth;		
        /**
         * 
         * Length: 4
         */
		@TxnColumn (column = "TYSQGY" )
		public java.lang.String tysqgy;		
        /**
         * 
         * Length: 16
         */
		@TxnColumn (column = "TYSQMM" )
		public java.lang.String tysqmm;		
        /**
         * 
         * Length: 3
         */
		@TxnColumn (column = "FJQDZL" )
		public java.lang.String fjqdzl;		
        /**
         * 
         * Length: 4
         */
		@TxnColumn (column = "SCCFCS" )
		public java.lang.String sccfcs;		
        /**
         * 
         * Length: 1
         */
		@TxnColumn (column = "SCWJBZ" )
		public java.lang.String scwjbz;		
        /**
         * 
         * Length: 128
         */
		@TxnColumn (column = "SCWJM" )
		public java.lang.String scwjm;		
        /**
         * 
         * Length: 8
         */
		@TxnColumn (column = "WJID" )
		public java.lang.String wjid;		
        /**
         * 
         * Length: 2
         */
		@TxnColumn (column = "JYBB" )
		public java.lang.String jybb;		
        /**
         * 
         * Length: 24
         */
		@TxnColumn (column = "SFXGXX" )
		public java.lang.String sfxgxx;		
        /**
         * 
         * Length: 1
         */
		@TxnColumn (column = "KHMMJMFS" )
		public java.lang.String khmmjmfs;		
        /**
         * 
         * Length: 20
         */
		@TxnColumn (column = "JYTBL" )
		public java.lang.String jytbl;		
        /**
         * 子交易代码
         * Length: 4
         */
		@TxnColumn (column = "ZJYDM" )
		public java.lang.String zjydm;		
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
         * 渠道种类
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 信用卡卡号
         * Length: 19
         */
		@TxnColumn (column = "XYK_KH" )
		public java.lang.String xyk_kh;		
        /**
         * 证件号码
         * Length: 18
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 家庭电话
         * Length: 17
         */
		@TxnColumn (column = "JTDH" )
		public java.lang.String jtdh;		
        /**
         * 出生年月
         * Type: DATE
         */
		@TxnColumn (column = "CSNY" )
		public java.util.Date csny;		
        /**
         * 新密码
         * Length: 6
         */
		@TxnColumn (column = "XMM" )
		public java.lang.String xmm;		
        /**
         * 第二磁道
         * Length: 37
         */
		@TxnColumn (column = "ECD" )
		public java.lang.String ecd;		
        /**
         * 检查项目选择(各位含义看xml注释)
         * Length: 15
         */
		@TxnColumn (column = "JCXMXZ" )
		public java.lang.String jcxmxz;		
        /**
         * CVV2
         * Length: 3
         */
		@TxnColumn (column = "CVV2" )
		public java.lang.String cvv2;		
        /**
         * 手机号码
         * Length: 12
         */
		@TxnColumn (column = "SJHM" )
		public java.lang.String sjhm;		
        /**
         * 会员编号
         * Length: 20
         */
		@TxnColumn (column = "HYBH" )
		public java.lang.String hybh;		
        /**
         * IP地址
         * Length: 20
         */
		@TxnColumn (column = "FJXX1" )
		public java.lang.String fjxx1;		
        /**
         * MAC地址
         * Length: 20
         */
		@TxnColumn (column = "FJXX2" )
		public java.lang.String fjxx2;		
        /**
         * 单位电话
         * Length: 17
         */
		@TxnColumn (column = "DWDH" )
		public java.lang.String dwdh;		
        /**
         * 母亲姓名
         * Length: 20
         */
		@TxnColumn (column = "MQXM" )
		public java.lang.String mqxm;		
	}

	@SuppressWarnings("serial")
	public static class Tr4614Response extends TxnResponse {

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
         * 文件标志, 1-需要传送文件;0-否
         * Length: 1
         */
		@TxnColumn (column = "WJBZ" )
		public java.lang.String wjbz;		
        /**
         * 文件名
         * Length: 30
         */
		@TxnColumn (column = "OUTFILE" )
		public java.lang.String outfile;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYKKH" )
		public java.lang.String xykkh;		
        /**
         * 持卡人姓名
         * Length: 30
         */
		@TxnColumn (column = "CKRXM" )
		public java.lang.String ckrxm;		
	}
}
