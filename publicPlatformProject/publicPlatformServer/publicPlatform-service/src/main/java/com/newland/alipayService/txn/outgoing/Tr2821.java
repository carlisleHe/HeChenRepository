package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 客户签约管理
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr2821")
@OutgoingService
	(
		trCode = "2821" 
		,fixTxnHeader = true
	)
public class Tr2821
		extends
		TxnOutgoingService<Tr2821.Tr2821Request, Tr2821.Tr2821Response> {

	public Tr2821() {
		super(Tr2821Request.class, Tr2821Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr2821Request extends TxnRequest {

		public Tr2821Request() {
			super("2821");
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
         * 交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM1" )
		public java.lang.String jydm1;		
        /**
         * 地区代号
         * Length: 2
         */
		@TxnColumn (column = "DQDH1" )
		public java.lang.String dqdh1;		
        /**
         * 机构代号
         * Length: 3
         */
		@TxnColumn (column = "JGDH1" )
		public java.lang.String jgdh1;		
        /**
         * 交易柜员
         * Length: 4
         */
		@TxnColumn (column = "JYGY1" )
		public java.lang.String jygy1;		
        /**
         * 终端代号
         * Length: 8
         */
		@TxnColumn (column = "ZDDH1" )
		public java.lang.String zddh1;		
        /**
         * 操作功能 1- 注册  5-销户
         * Length: 1
         */
		@TxnColumn (column = "CZGN" )
		public java.lang.String czgn;		
        /**
         * 显示类型
         * Length: 1
         */
		@TxnColumn (column = "CXBJ" )
		public java.lang.String cxbj;		
        /**
         * 账号
         * Length: 18
         */
		@TxnColumn (column = "ZHDH" )
		public java.lang.String zhdh;		
        /**
         * 账户签约品种
         * Length: 8
         */
		@TxnColumn (column = "XYLXDM" )
		public java.lang.String xylxdm;		
        /**
         * 对方标识
         * Length: 32
         */
		@TxnColumn (column = "DFXYBH" )
		public java.lang.String dfxybh;		
        /**
         * 地址小序号
         * Length: 3
         */
		@TxnColumn (column = "DZXXH" )
		public java.lang.String dzxxh;		
        /**
         * 电话小序号
         * Length: 3
         */
		@TxnColumn (column = "DHXXH" )
		public java.lang.String dhxxh;		
        /**
         * 备注
         * Length: 60
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
        /**
         * 记录状态
         * Length: 1
         */
		@TxnColumn (column = "JLZT" )
		public java.lang.String jlzt;		
        /**
         * 文件名
         * Length: 50
         */
		@TxnColumn (column = "WMJ" )
		public java.lang.String wmj;		
	}

	@SuppressWarnings("serial")
	public static class Tr2821Response extends TxnResponse {

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
         * 备注
         * Length: 60
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
        /**
         * 记录状态
         * Length: 1
         */
		@TxnColumn (column = "JLZT" )
		public java.lang.String jlzt;		
        /**
         * 文件名
         * Length: 50
         */
		@TxnColumn (column = "WMJ" )
		public java.lang.String wmj;		
	}
}
