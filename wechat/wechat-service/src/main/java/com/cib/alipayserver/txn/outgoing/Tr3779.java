package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 互联网贷款申请
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3779")
@OutgoingService
	(
		trCode = "3779" 
		,fixTxnHeader = false
	)
public class Tr3779
		extends
		TxnOutgoingService<Tr3779.Tr3779Request, Tr3779.Tr3779Response> {

	public Tr3779() {
		super(Tr3779Request.class, Tr3779Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3779Request extends TxnRequest {

		public Tr3779Request() {
			super("3779");
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
         * 渠道种类
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 客户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHXM" )
		public java.lang.String khxm;		
        /**
         * 性别
         * Length: 1
         */
		@TxnColumn (column = "XB" )
		public java.lang.String xb;		
        /**
         * 证件类型
         * Length: 10
         */
		@TxnColumn (column = "ZJLX" )
		public java.lang.String zjlx;		
        /**
         * 证件号码
         * Length: 25
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 拟申请金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "NSQJE" )
		public java.math.BigDecimal nsqje;		
        /**
         * 拟申请期限
         * Type: NUMBER_03_0
         */
		@TxnColumn (column = "NSQQX" )
		public java.lang.Integer nsqqx;		
        /**
         * 拟申请贷款用途
         * Length: 100
         */
		@TxnColumn (column = "NSQDKYT" )
		public java.lang.String nsqdkyt;		
        /**
         * 联系电话
         * Length: 20
         */
		@TxnColumn (column = "LXDH" )
		public java.lang.String lxdh;		
        /**
         * 申请日期
         * Type: DATE
         */
		@TxnColumn (column = "SQRQ" )
		public java.util.Date sqrq;		
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
         * 工作所在省份
         * Length: 10
         */
		@TxnColumn (column = "GZSZSF" )
		public java.lang.String gzszsf;		
        /**
         * 工作所在城市
         * Length: 10
         */
		@TxnColumn (column = "GZSZCS" )
		public java.lang.String gzszcs;		
        /**
         * 工作所在区
         * Length: 10
         */
		@TxnColumn (column = "GZSZQ" )
		public java.lang.String gzszq;		
        /**
         * 户籍所在省份
         * Length: 10
         */
		@TxnColumn (column = "HJSZSF" )
		public java.lang.String hjszsf;		
        /**
         * 户籍所在城市
         * Length: 10
         */
		@TxnColumn (column = "HJSZCS" )
		public java.lang.String hjszcs;		
        /**
         * 工作单位名城
         * Length: 60
         */
		@TxnColumn (column = "GZDWM" )
		public java.lang.String gzdwm;		
        /**
         * 工作单位地址
         * Length: 60
         */
		@TxnColumn (column = "GZDWDZ" )
		public java.lang.String gzdwdz;		
        /**
         * 月收入
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YSL" )
		public java.math.BigDecimal ysl;		
        /**
         * 本人及家庭名下金融资产
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "MXJRZC" )
		public java.math.BigDecimal mxjrzc;		
        /**
         * 本人及家庭名下本地房产
         * Type: NUMBER_03_0
         */
		@TxnColumn (column = "MXBDFC" )
		public java.lang.Integer mxbdfc;		
        /**
         * 备注1
         * Length: 20
         */
		@TxnColumn (column = "BZ1" )
		public java.lang.String bz1;		
        /**
         * 备注2
         * Length: 20
         */
		@TxnColumn (column = "BZ2" )
		public java.lang.String bz2;		
        /**
         * 备注3
         * Length: 20
         */
		@TxnColumn (column = "BZ3" )
		public java.lang.String bz3;		
        /**
         * 备注4
         * Length: 20
         */
		@TxnColumn (column = "BZ4" )
		public java.lang.String bz4;		
        /**
         * 备注5
         * Length: 20
         */
		@TxnColumn (column = "BZ5" )
		public java.lang.String bz5;		
        /**
         * 备注6
         * Length: 30
         */
		@TxnColumn (column = "BZ6" )
		public java.lang.String bz6;		
        /**
         * 备注7
         * Length: 30
         */
		@TxnColumn (column = "BZ7" )
		public java.lang.String bz7;		
        /**
         * 备注8
         * Length: 30
         */
		@TxnColumn (column = "BZ8" )
		public java.lang.String bz8;		
        /**
         * 备注9
         * Length: 30
         */
		@TxnColumn (column = "BZ9" )
		public java.lang.String bz9;		
        /**
         * 备注10
         * Length: 30
         */
		@TxnColumn (column = "BZ10" )
		public java.lang.String bz10;		
	}

	@SuppressWarnings("serial")
	public static class Tr3779Response extends TxnResponse {

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
         * 预申请协议编号;年后两位+月+日+渠道代号+8位流水号 网银代号为W，自助为Z、呼叫中心为H 则06年12月1日呼叫中心当天上报的第123个预审批申请为：061201H00000123           
         * Length: 17
         */
		@TxnColumn (column = "YSQXYBH" )
		public java.lang.String ysqxybh;		
	}
}
