package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡账户查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3419")
@OutgoingService
	(
		trCode = "3419" 
		,fixTxnHeader = false
	)
public class Tr3419
		extends
		TxnOutgoingService<Tr3419.Tr3419Request, Tr3419.Tr3419Response> {

	public Tr3419() {
		super(Tr3419Request.class, Tr3419Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3419Request extends TxnRequest {

		public Tr3419Request() {
			super("3419");
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
		@TxnColumn (column = "JYLY" )
		public java.lang.String jyly;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 服务方式
         * Length: 1
         */
		@TxnColumn (column = "FWFS" )
		public java.lang.String fwfs;		
        /**
         * 查询/取现密码
         * Length: 6
         */
		@TxnColumn (column = "XYKMM" )
		public java.lang.String xykmm;		
	}

	@SuppressWarnings("serial")
	public static class Tr3419Response extends TxnResponse {

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
         * 账户名称1
         * Length: 30
         */
		@TxnColumn (column = "ZHMC" )
		public java.lang.String zhmc;		
        /**
         * 账户名称2
         * Length: 30
         */
		@TxnColumn (column = "SZMC" )
		public java.lang.String szmc;		
        /**
         * 卡片种类
         * Length: 30
         */
		@TxnColumn (column = "XYKZL" )
		public java.lang.String xykzl;		
        /**
         * 分行编号
         * Type: NUMBER_08_0
         */
		@TxnColumn (column = "FHBH" )
		public java.lang.Integer fhbh;		
        /**
         * 公司代码
         * Length: 10
         */
		@TxnColumn (column = "GSDM" )
		public java.lang.String gsdm;		
        /**
         * 公司名称
         * Length: 30
         */
		@TxnColumn (column = "GSMC" )
		public java.lang.String gsmc;		
        /**
         * 开户日期
         * Type: DATE
         */
		@TxnColumn (column = "KHRQ" )
		public java.util.Date khrq;		
        /**
         * 信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "XYED" )
		public java.math.BigDecimal xyed;		
        /**
         * 授权未请款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WQKED" )
		public java.math.BigDecimal wqked;		
        /**
         * 目前可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "KYED" )
		public java.math.BigDecimal kyed;		
        /**
         * 本币账面余额符合
         * Length: 1
         */
		@TxnColumn (column = "RMBJEFH" )
		public java.lang.String rmbjefh;		
        /**
         * 本币账面余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "RMBZHYE" )
		public java.math.BigDecimal rmbzhye;		
        /**
         * 第二币种信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "XYED1X" )
		public java.math.BigDecimal xyed1x;		
        /**
         * 第二币种可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "KYED1" )
		public java.math.BigDecimal kyed1;		
        /**
         * 美元账面余额符合
         * Length: 1
         */
		@TxnColumn (column = "JEFH3" )
		public java.lang.String jefh3;		
        /**
         * 美元账面余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "ZHYE1" )
		public java.math.BigDecimal zhye1;		
        /**
         * 还款截止日期
         * Type: DATE
         */
		@TxnColumn (column = "ZZRQ" )
		public java.util.Date zzrq;		
        /**
         * 自动还款账号
         * Length: 32
         */
		@TxnColumn (column = "FKDWZH" )
		public java.lang.String fkdwzh;		
        /**
         * 自动还款方式
         * Length: 1
         */
		@TxnColumn (column = "KKFS" )
		public java.lang.String kkfs;		
        /**
         * 上次付款日期
         * Type: DATE
         */
		@TxnColumn (column = "SJYRQ" )
		public java.util.Date sjyrq;		
        /**
         * 已付账款
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE1" )
		public java.math.BigDecimal je1;		
        /**
         * 逾期金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE2" )
		public java.math.BigDecimal je2;		
        /**
         * 逾期天数
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "TS" )
		public java.lang.Integer ts;		
        /**
         * 证件类型
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
         * 争议金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE3" )
		public java.math.BigDecimal je3;		
        /**
         * 调整金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE4" )
		public java.math.BigDecimal je4;		
        /**
         * 调整金额符号
         * Length: 1
         */
		@TxnColumn (column = "TZJEFH" )
		public java.lang.String tzjefh;		
        /**
         * 账户状态
         * Length: 1
         */
		@TxnColumn (column = "QSZHZT" )
		public java.lang.String qszhzt;		
        /**
         * 账户状态日期
         * Type: DATE
         */
		@TxnColumn (column = "ZHZTRQ" )
		public java.util.Date zhztrq;		
        /**
         * 消费净额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "XFJE" )
		public java.math.BigDecimal xfje;		
        /**
         * 消费净额符号
         * Length: 1
         */
		@TxnColumn (column = "XFJEFH" )
		public java.lang.String xfjefh;		
        /**
         * 预借现金信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "QXXYED" )
		public java.math.BigDecimal qxxyed;		
        /**
         * 预借现金可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "QXKYED" )
		public java.math.BigDecimal qxkyed;		
        /**
         * 大宗购货信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DZGWXYED" )
		public java.math.BigDecimal dzgwxyed;		
        /**
         * 大宗购货可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DZGWKYED" )
		public java.math.BigDecimal dzgwkyed;		
	}
}
