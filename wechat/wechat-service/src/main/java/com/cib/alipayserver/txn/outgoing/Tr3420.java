package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡客户信息查询/修改（转发交易）
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr3420")
@OutgoingService
	(
		trCode = "3420" 
	)
public class Tr3420
		extends
		TxnOutgoingService<Tr3420.Tr3420Request, Tr3420.Tr3420Response> {

	public Tr3420() {
		super(Tr3420Request.class, Tr3420Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3420Request extends TxnRequest {

		public Tr3420Request() {
			super("3420");
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
         * 交易代码
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
         * 操作功能  1-查询 2-修改
         * Length: 1
         */
		@TxnColumn (column = "CZGN" )
		public java.lang.String czgn;		
        /**
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYK_KH" )
		public java.lang.String xyk_kh;		
        /**
         * 是否校验密码 0-否 1-查询密码 2-取现密码
         * Length: 1
         */
		@TxnColumn (column = "SFJYMM" )
		public java.lang.String sfjymm;		
        /**
         * 是否校验密码
         * Length: 6
         */
		@TxnColumn (column = "XYKMM" )
		public java.lang.String xykmm;		
        /**
         * 国籍 1-本国 2-外籍
         * Length: 1
         */
		@TxnColumn (column = "GJ" )
		public java.lang.String gj;		
        /**
         * 婚姻状况  S-未婚，M-已婚，W-丧偶，D-离异，O-其它
         * Length: 1
         */
		@TxnColumn (column = "HYZK" )
		public java.lang.String hyzk;		
        /**
         * 教育程度  1-初中以下，2-高中/中专，3-大专，4-本科，5-硕士，6-博士
         * Length: 2
         */
		@TxnColumn (column = "JYCD" )
		public java.lang.String jycd;		
        /**
         * 职称 01-负责人，02-高级主管，03-中级主管，04-基层，05-无
         * Length: 2
         */
		@TxnColumn (column = "ZC" )
		public java.lang.String zc;		
        /**
         * 家庭电话
         * Length: 20
         */
		@TxnColumn (column = "JTDH" )
		public java.lang.String jtdh;		
        /**
         * 办公电话
         * Length: 20
         */
		@TxnColumn (column = "BGDH" )
		public java.lang.String bgdh;		
        /**
         * 分机号码
         * Length: 10
         */
		@TxnColumn (column = "FJHM" )
		public java.lang.String fjhm;		
        /**
         * 手机号
         * Length: 12
         */
		@TxnColumn (column = "SJH" )
		public java.lang.String sjh;		
        /**
         * 电子邮箱
         * Length: 30
         */
		@TxnColumn (column = "EMAIL" )
		public java.lang.String email;		
        /**
         * 传真号码
         * Length: 20
         */
		@TxnColumn (column = "CZHM" )
		public java.lang.String czhm;		
        /**
         * 居住年数
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "JZNS" )
		public java.lang.Integer jzns;		
        /**
         * 居住状况 L-与父母同住，R-租用，O-自购无贷款，D-自购有贷款，B-寄宿
         * Length: 1
         */
		@TxnColumn (column = "JZZK" )
		public java.lang.String jzzk;		
        /**
         * 公司名称
         * Length: 30
         */
		@TxnColumn (column = "GSMC" )
		public java.lang.String gsmc;		
        /**
         * 公司部门
         * Length: 20
         */
		@TxnColumn (column = "GSBM" )
		public java.lang.String gsbm;		
        /**
         * 职务
         * Length: 20
         */
		@TxnColumn (column = "DWZW" )
		public java.lang.String dwzw;		
        /**
         * 行业
         * Length: 4
         */
		@TxnColumn (column = "DWHY" )
		public java.lang.String dwhy;		
        /**
         * 公司性质 01-国有，02-独资，03-合资/合作，04-股份制，05-私营，06-其它
         * Length: 1
         */
		@TxnColumn (column = "GSXZ" )
		public java.lang.String gsxz;		
        /**
         * 工作年数
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "GZNS" )
		public java.lang.Integer gzns;		
        /**
         * 年收入
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE1" )
		public java.math.BigDecimal je1;		
        /**
         * 配偶年收入
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE2" )
		public java.math.BigDecimal je2;		
	}

	@SuppressWarnings("serial")
	public static class Tr3420Response extends TxnResponse {

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
         * 中文姓名
         * Length: 20
         */
		@TxnColumn (column = "ZWXM" )
		public java.lang.String zwxm;		
        /**
         * 英文姓名
         * Length: 35
         */
		@TxnColumn (column = "YWXM" )
		public java.lang.String ywxm;		
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
         * 性别 M-男， F-女
         * Length: 1
         */
		@TxnColumn (column = "XB" )
		public java.lang.String xb;		
        /**
         * 国籍 1-本国，2-外籍
         * Length: 1
         */
		@TxnColumn (column = "GJ" )
		public java.lang.String gj;		
        /**
         * 婚姻状况 S-未婚，M-已婚，W-丧偶，D-离异，O-其它
         * Length: 1
         */
		@TxnColumn (column = "HYZK" )
		public java.lang.String hyzk;		
        /**
         * 教育程度  1-初中以下，2-高中/中专，3-大专，4-本科，5-硕士，6-博士
         * Length: 2
         */
		@TxnColumn (column = "JYCD" )
		public java.lang.String jycd;		
        /**
         * 职称 01-负责人，02-高级主管，03-中级主管，04-基层，05-无
         * Length: 2
         */
		@TxnColumn (column = "CZ" )
		public java.lang.String cz;		
        /**
         * 家庭电话
         * Length: 20
         */
		@TxnColumn (column = "JTDH" )
		public java.lang.String jtdh;		
        /**
         * 办公电话
         * Length: 20
         */
		@TxnColumn (column = "BGDH" )
		public java.lang.String bgdh;		
        /**
         * 分机号码
         * Length: 6
         */
		@TxnColumn (column = "FJHM" )
		public java.lang.String fjhm;		
        /**
         * 手机号
         * Length: 12
         */
		@TxnColumn (column = "SJH" )
		public java.lang.String sjh;		
        /**
         * 电子邮箱
         * Length: 30
         */
		@TxnColumn (column = "EMAIL" )
		public java.lang.String email;		
        /**
         * 传真号码
         * Length: 20
         */
		@TxnColumn (column = "CZHM" )
		public java.lang.String czhm;		
        /**
         * 居住年数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "JZNS" )
		public java.lang.Integer jzns;		
        /**
         * 居住状况 L-与父母同住，R-租用，O-自购无贷款，D-自购有贷款，B-寄宿
         * Length: 1
         */
		@TxnColumn (column = "JZZK" )
		public java.lang.String jzzk;		
        /**
         * 公司名称
         * Length: 30
         */
		@TxnColumn (column = "GSMC" )
		public java.lang.String gsmc;		
        /**
         * 公司部门
         * Length: 20
         */
		@TxnColumn (column = "GSBM" )
		public java.lang.String gsbm;		
        /**
         * 职务
         * Length: 20
         */
		@TxnColumn (column = "DWZW" )
		public java.lang.String dwzw;		
        /**
         * 行业
         * Length: 4
         */
		@TxnColumn (column = "DWHY" )
		public java.lang.String dwhy;		
        /**
         * 公司性质
         * Length: 1
         */
		@TxnColumn (column = "GSXZ" )
		public java.lang.String gsxz;		
        /**
         * 工作年数
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "GZNS" )
		public java.lang.Integer gzns;		
        /**
         * 年收入
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE1" )
		public java.math.BigDecimal je1;		
        /**
         * 配偶年收入
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "JE2" )
		public java.math.BigDecimal je2;		
        /**
         * 是否为外国政要标志
         * Length: 1
         */
		@TxnColumn (column = "wgzybz" )
		public java.lang.String wgzybz;		
        /**
         * 证件有效期
         * Type: DATE
         */
		@TxnColumn (column = "zjyxq" )
		public java.util.Date zjyxq;		
	}
}
