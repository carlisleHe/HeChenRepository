package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡未出账单查询
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr5070")
@OutgoingService
	(
		trCode = "5070" 
		,fixTxnHeader = false
	)
public class Tr5070
		extends
		TxnOutgoingService<Tr5070.Tr5070Request, Tr5070.Tr5070Response> {

	public Tr5070() {
		super(Tr5070Request.class, Tr5070Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr5070Request extends TxnRequest {

		public Tr5070Request() {
			super("5070");
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
         * IC卡应用标识
         * Length: 32
         */
		@TxnColumn (column = "CKHM" )
		public java.lang.String ckhm;		
        /**
         * 介质标志:1:磁条卡2:IC卡7:无卡
         * Length: 1
         */
		@TxnColumn (column = "JZBZ" )
		public java.lang.String jzbz;		
        /**
         * IC卡认证信息个数
         * Length: 1
         */
		@TxnColumn (column = "XHCS" )
		public java.lang.String xhcs;		
        /**
         * 
         * Length: 4
         */
		@TxnColumn (column = "JYDM1" )
		public java.lang.String jydm1;		
        /**
         * 操作标志：1-查询
         * Length: 1
         */
		@TxnColumn (column = "CZBZ" )
		public java.lang.String czbz;		
        /**
         * 渠道种类
         * Length: 3
         */
		@TxnColumn (column = "QDZL" )
		public java.lang.String qdzl;		
        /**
         * 前台系统编号
         * Length: 6
         */
		@TxnColumn (column = "QTXTBH" )
		public java.lang.String qtxtbh;		
        /**
         * 前台系统日期
         * Type: DATE
         */
		@TxnColumn (column = "QTXTRQ" )
		public java.util.Date qtxtrq;		
        /**
         * 前台流水号
         * Length: 16
         */
		@TxnColumn (column = "QTLSH" )
		public java.lang.String qtlsh;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYKKH" )
		public java.lang.String xykkh;		
        /**
         * 是否校验密码 密码 0：否1：查询密码2:取款密码
         * Length: 1
         */
		@TxnColumn (column = "YBMZ" )
		public java.lang.String ybmz;		
        /**
         * 查询/取现密码
         * Length: 6
         */
		@TxnColumn (column = "XYKMM" )
		public java.lang.String xykmm;		
        /**
         * 货币种类:01:人民币14：美元
         * Length: 2
         */
		@TxnColumn (column = "HBZL" )
		public java.lang.String hbzl;		
        /**
         * 文件版本 app_ver Char(2) 必输，赋值01
         * Length: 2
         */
		@TxnColumn (column = "WJBB" )
		public java.lang.String wjbb;		
        /**
         * 备注
         * Length: 60
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
        /**
         * IC卡安全认证信息长度:0表示无
         * Length: 3
         */
		@TxnColumn (column = "HS1" )
		public java.lang.String hs1;		
        /**
         * IC卡安全认证信息
         * Length: 255
         */
		@TxnColumn (column = "ICAQRZXX1" )
		public java.lang.String icaqrzxx1;		
        /**
         * IC卡号1:参与arqc认证
         * Length: 19
         */
		@TxnColumn (column = "JKKH1" )
		public java.lang.String jkkh1;		
        /**
         * IC卡小序号1:参与arqc认证
         * Length: 2
         */
		@TxnColumn (column = "ICKXH1" )
		public java.lang.String ickxh1;		
        /**
         * IC卡安全认证信息长度：0表示无
         * Length: 3
         */
		@TxnColumn (column = "HS2" )
		public java.lang.String hs2;		
        /**
         * IC卡安全认证信息
         * Length: 255
         */
		@TxnColumn (column = "ICAQRZXX2" )
		public java.lang.String icaqrzxx2;		
        /**
         * IC卡号2：参与arqc认证
         * Length: 19
         */
		@TxnColumn (column = "JKKH2" )
		public java.lang.String jkkh2;		
        /**
         * IC卡小序号2：参与arqc认证
         * Length: 2
         */
		@TxnColumn (column = "ICHXH2" )
		public java.lang.String ichxh2;		
	}

	@SuppressWarnings("serial")
	public static class Tr5070Response extends TxnResponse {

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
         *  前置系统日期
         * Type: DATE
         */
		@TxnColumn (column = "PTRQ" )
		public java.util.Date ptrq;		
        /**
         * 前置系统流水号
         * Length: 8
         */
		@TxnColumn (column = "ZJLSH" )
		public java.lang.String zjlsh;		
        /**
         * 前台系统编号
         * Length: 6
         */
		@TxnColumn (column = "QTXTBH" )
		public java.lang.String qtxtbh;		
        /**
         * 前台流水号
         * Length: 16
         */
		@TxnColumn (column = "QTLSH" )
		public java.lang.String qtlsh;		
        /**
         * 本次返回交易条数
         * Length: 4
         */
		@TxnColumn (column = "ZBS" )
		public java.lang.String zbs;		
        /**
         * 备注
         * Length: 60
         */
		@TxnColumn (column = "BZ" )
		public java.lang.String bz;		
        /**
         * IC卡安全认证信息长度:0表示无
         * Length: 3
         */
		@TxnColumn (column = "HS1" )
		public java.lang.String hs1;		
        /**
         * IC卡安全认证信息
         * Length: 255
         */
		@TxnColumn (column = "ICAQRZXX1" )
		public java.lang.String icaqrzxx1;		
        /**
         * IC卡号1:参与arqc认证
         * Length: 19
         */
		@TxnColumn (column = "JKKH1" )
		public java.lang.String jkkh1;		
        /**
         * IC卡小序号1:参与arqc认证
         * Length: 2
         */
		@TxnColumn (column = "ICKXH1" )
		public java.lang.String ickxh1;		
        /**
         * IC卡安全认证信息长度：0表示无
         * Length: 3
         */
		@TxnColumn (column = "HS2" )
		public java.lang.String hs2;		
        /**
         * IC卡安全认证信息
         * Length: 255
         */
		@TxnColumn (column = "ICAQRZXX2" )
		public java.lang.String icaqrzxx2;		
        /**
         * IC卡号2：参与arqc认证
         * Length: 19
         */
		@TxnColumn (column = "JKKH2" )
		public java.lang.String jkkh2;		
        /**
         * IC卡小序号2：参与arqc认证
         * Length: 2
         */
		@TxnColumn (column = "ICHXH2" )
		public java.lang.String ichxh2;		
	}
}
