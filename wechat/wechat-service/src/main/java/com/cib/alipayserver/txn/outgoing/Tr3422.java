package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡卡片信息查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3422")
@OutgoingService
	(
		trCode = "3422" 
		,fixTxnHeader = false
	)
public class Tr3422
		extends
		TxnOutgoingService<Tr3422.Tr3422Request, Tr3422.Tr3422Response> {

	public Tr3422() {
		super(Tr3422Request.class, Tr3422Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3422Request extends TxnRequest {

		public Tr3422Request() {
			super("3422");
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
         * 交易来源
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYK_KH" )
		public java.lang.String xyk_kh;		
        /**
         * 是否校验密码 密码 0：否1：查询密码2:取款密码
         * Length: 1
         */
		@TxnColumn (column = "SFJYMM" )
		public java.lang.String sfjymm;		
        /**
         * 查询/取现密码
         * Length: 6
         */
		@TxnColumn (column = "XYKMM" )
		public java.lang.String xykmm;		
	}

	@SuppressWarnings("serial")
	public static class Tr3422Response extends TxnResponse {

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
         * 证件种类
         * Length: 1
         */
		@TxnColumn (column = "ZJZL" )
		public java.lang.String zjzl;		
        /**
         * 证件号码
         * Length: 20
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 持卡人姓名
         * Length: 20
         */
		@TxnColumn (column = "ZWXM" )
		public java.lang.String zwxm;		
        /**
         * 凸字姓名
         * Length: 35
         */
		@TxnColumn (column = "YWXM" )
		public java.lang.String ywxm;		
        /**
         * 发卡日期
         * Length: 8
         */
		@TxnColumn (column = "QXRQ" )
		public java.lang.String qxrq;		
        /**
         * 开卡日期
         * Length: 8
         */
		@TxnColumn (column = "SXRQ" )
		public java.lang.String sxrq;		
        /**
         * 上次续卡日期
         * Length: 8
         */
		@TxnColumn (column = "RQ" )
		public java.lang.String rq;		
        /**
         * 信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "XYED" )
		public java.math.BigDecimal xyed;		
        /**
         * 可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "KYED" )
		public java.math.BigDecimal kyed;		
        /**
         * 大宗购货信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ3" )
		public java.math.BigDecimal jesz3;		
        /**
         * 大宗购货可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ4" )
		public java.math.BigDecimal jesz4;		
        /**
         * 预借现金信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ1" )
		public java.math.BigDecimal jesz1;		
        /**
         * 预借现金可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ2" )
		public java.math.BigDecimal jesz2;		
        /**
         * 第二币种信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "XYED1" )
		public java.math.BigDecimal xyed1;		
        /**
         * 第二币种可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JESZ6" )
		public java.math.BigDecimal jesz6;		
        /**
         * 卡片状态
         * Length: 1
         */
		@TxnColumn (column = "JLZT" )
		public java.lang.String jlzt;		
        /**
         * 主副卡标志
         * Length: 1
         */
		@TxnColumn (column = "KHLB" )
		public java.lang.String khlb;		
        /**
         * 挂失日期
         * Length: 8
         */
		@TxnColumn (column = "GSRQ" )
		public java.lang.String gsrq;		
        /**
         * 卡片有效期
         * Length: 6
         */
		@TxnColumn (column = "QSNY" )
		public java.lang.String qsny;		
        /**
         * 卡片种类描述
         * Length: 30
         */
		@TxnColumn (column = "ZJMC" )
		public java.lang.String zjmc;		
	}
}
