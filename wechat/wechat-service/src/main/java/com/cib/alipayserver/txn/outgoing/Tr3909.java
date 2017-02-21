package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 发普通短信接口
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3909")
@OutgoingService
	(
		trCode = "3909" 
		,fixTxnHeader = false
	)
public class Tr3909
		extends
		TxnOutgoingService<Tr3909.Tr3909Request, Tr3909.Tr3909Response> {

	public Tr3909() {
		super(Tr3909Request.class, Tr3909Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3909Request extends TxnRequest {

		public Tr3909Request() {
			super("3909");
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
         * 账户代号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 文件标识
         * Length: 1
         */
		@TxnColumn (column = "WJBZ" )
		public java.lang.String wjbz;		
        /**
         * 输入文件名
         * Length: 30
         */
		@TxnColumn (column = "INFILE" )
		public java.lang.String infile;		
        /**
         * 文件校验码
         * Length: 8
         */
		@TxnColumn (column = "JYM" )
		public java.lang.String jym;		
        /**
         * 短信来源
         * Length: 3
         */
		@TxnColumn (column = "DXLY" )
		public java.lang.String dxly;		
        /**
         * 业务类型
         * Length: 2
         */
		@TxnColumn (column = "YWLX" )
		public java.lang.String ywlx;		
        /**
         * 客户层级
         * Length: 2
         */
		@TxnColumn (column = "KHJB" )
		public java.lang.String khjb;		
        /**
         * 发送时间
         * Length: 14
         */
		@TxnColumn (column = "TIME" )
		public java.lang.String time;		
        /**
         * 手机号码
         * Length: 12
         */
		@TxnColumn (column = "SJHM" )
		public java.lang.String sjhm;		
        /**
         * 短信内容
         * Length: 2
         */
		@TxnColumn (column = "DXLX" )
		public java.lang.String dxlx;		
        /**
         * 短信内容
         * Length: 1000
         */
		@TxnColumn (column = "DXNR" )
		public java.lang.String dxnr;		
        /**
         * 备注
         * Length: 20
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
	}

	@SuppressWarnings("serial")
	public static class Tr3909Response extends TxnResponse {

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
         * 文件标志 填0,即无文件传送
         * Length: 1
         */
		@TxnColumn (column = "WJBZ" )
		public java.lang.String wjbz;		
        /**
         * 输出文件名 填空格
         * Length: 30
         */
		@TxnColumn (column = "OUTFILE" )
		public java.lang.String outfile;		
        /**
         * 备注 预留字段，以后备用
         * Length: 20
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
	}
}
