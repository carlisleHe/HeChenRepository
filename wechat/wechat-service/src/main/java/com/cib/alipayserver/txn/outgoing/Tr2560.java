package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 顺通卡客户信息查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr2560")
@OutgoingService
	(
		trCode = "2560" 
		,fixTxnHeader = false
	)
public class Tr2560
		extends
		TxnOutgoingService<Tr2560.Tr2560Request, Tr2560.Tr2560Response> {

	public Tr2560() {
		super(Tr2560Request.class, Tr2560Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr2560Request extends TxnRequest {

		public Tr2560Request() {
			super("2560");
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
         * 授权柜员
         * Length: 4
         */
		@TxnColumn (column = "SQGY" )
		public java.lang.String sqgy;		
        /**
         * 服务方式
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
	}

	@SuppressWarnings("serial")
	public static class Tr2560Response extends TxnResponse {

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
         * 客户性别
         * Length: 1
         */
		@TxnColumn (column = "XB" )
		public java.lang.String xb;		
        /**
         * 出生日期
         * Type: DATE
         */
		@TxnColumn (column = "CSRQ" )
		public java.util.Date csrq;		
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
         * 地址
         * Length: 60
         */
		@TxnColumn (column = "DZ" )
		public java.lang.String dz;		
        /**
         * 网络地址
         * Length: 60
         */
		@TxnColumn (column = "WLDZ" )
		public java.lang.String wldz;		
        /**
         * 电话号码
         * Length: 30
         */
		@TxnColumn (column = "DHHM" )
		public java.lang.String dhhm;		
        /**
         * BP机号码
         * Length: 20
         */
		@TxnColumn (column = "BPJHM" )
		public java.lang.String bpjhm;		
        /**
         * 邮政编码
         * Length: 6
         */
		@TxnColumn (column = "YZBM" )
		public java.lang.String yzbm;		
        /**
         * 职业
         * Length: 12
         */
		@TxnColumn (column = "ZY" )
		public java.lang.String zy;		
        /**
         * 开户机构代号
         * Length: 3
         */
		@TxnColumn (column = "KHJGDH" )
		public java.lang.String khjgdh;		
        /**
         * 开户柜员
         * Length: 4
         */
		@TxnColumn (column = "KHGY" )
		public java.lang.String khgy;		
        /**
         * 开户日期
         * Type: DATE
         */
		@TxnColumn (column = "KHRQ" )
		public java.util.Date khrq;		
        /**
         * 凭证代号
         * Length: 9
         */
		@TxnColumn (column = "PZDH" )
		public java.lang.String pzdh;		
        /**
         * 启用日期
         * Type: DATE
         */
		@TxnColumn (column = "QYRQ" )
		public java.util.Date qyrq;		
        /**
         * 有效日期
         * Type: DATE
         */
		@TxnColumn (column = "YXRQ" )
		public java.util.Date yxrq;		
        /**
         * 串7
         * Length: 12
         */
		@TxnColumn (column = "S7" )
		public java.lang.String s7;		
        /**
         * 串8
         * Length: 12
         */
		@TxnColumn (column = "S8" )
		public java.lang.String s8;		
	}
}
