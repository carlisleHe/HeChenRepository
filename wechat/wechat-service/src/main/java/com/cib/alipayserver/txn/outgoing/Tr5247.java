package com.cib.alipayserver.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 客户信息查询
 * @author ShiZhenning
 * @since  16-01-07
 */
@Service("tr5247")
@OutgoingService
	(
		trCode = "5247" 
		,fixTxnHeader = false
	)
public class Tr5247
		extends
		TxnOutgoingService<Tr5247.Tr5247Request, Tr5247.Tr5247Response> {

	public Tr5247() {
		super(Tr5247Request.class, Tr5247Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr5247Request extends TxnRequest {

		public Tr5247Request() {
			super("5247");
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
         * 内部交易代码
         * Length: 4
         */
		@TxnColumn (column = "JYDM1" )
		public java.lang.String jydm1;		
        /**
         * 地区代号(转发头)
         * Length: 2
         */
		@TxnColumn (column = "DQDH1" )
		public java.lang.String dqdh1;		
        /**
         * 机构代号(转发头)
         * Length: 3
         */
		@TxnColumn (column = "JGDH1" )
		public java.lang.String jgdh1;		
        /**
         * 交易柜员(转发头)
         * Length: 4
         */
		@TxnColumn (column = "JYGY1" )
		public java.lang.String jygy1;		
        /**
         * 终端代号(转发头)
         * Length: 8
         */
		@TxnColumn (column = "ZDDH1" )
		public java.lang.String zddh1;		
        /**
         * 参考核心提供的前台系统编号
         * Length: 6
         */
		@TxnColumn (column = "XTBH" )
		public java.lang.String xtbh;		
        /**
         * 前台交易流水编号
         * Length: 30
         */
		@TxnColumn (column = "QTJYLSBH" )
		public java.lang.String qtjylsbh;		
        /**
         * 法人代码，固定值：01
         * Length: 2
         */
		@TxnColumn (column = "FRDM" )
		public java.lang.String frdm;		
        /**
         * 授权柜员
         * Length: 4
         */
		@TxnColumn (column = "SQGY" )
		public java.lang.String sqgy;		
        /**
         * 接口版本，固定值：01
         * Length: 2
         */
		@TxnColumn (column = "JKBB1" )
		public java.lang.String jkbb1;		
        /**
         * 识别方式
         * Length: 1
         */
		@TxnColumn (column = "SBFS" )
		public java.lang.String sbfs;		
        /**
         * 客户唯一编号
         * Length: 12
         */
		@TxnColumn (column = "KHWYBH" )
		public java.lang.String khwybh;		
        /**
         * 证件类型
         * Length: 1
         */
		@TxnColumn (column = "ZJLX" )
		public java.lang.String zjlx;		
        /**
         * 证件号码
         * Length: 20
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;		
        /**
         * 客户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHXM" )
		public java.lang.String khxm;		
        /**
         * 卡账号
         * Length: 18
         */
		@TxnColumn (column = "KZH" )
		public java.lang.String kzh;		
        /**
         * 客户归属系统
         * Length: 6
         */
		@TxnColumn (column = "KHGSXT" )
		public java.lang.String khgsxt;		
	}

	@SuppressWarnings("serial")
	public static class Tr5247Response extends TxnResponse {

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
         * 信息标识(CIF成功响应头)
         * Type: NUMBER_05_0
         */
		@TxnColumn (column = "MSGID1" )
		public java.lang.Integer msgid1;		
        /**
         * 重复次数(CIF成功响应头)
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "CFCS1" )
		public java.lang.Integer cfcs1;		
        /**
         * 交易日期 (CIF成功响应头)
         * Type: DATE
         */
		@TxnColumn (column = "JYRQ1" )
		public java.util.Date jyrq1;		
        /**
         * 交易时间(CIF成功响应头)
         * Type: TIME
         */
		@TxnColumn (column = "JYSJ1" )
		public java.util.Date jysj1;		
        /**
         * 柜员流水号(CIF成功响应头)
         * Length: 8
         */
		@TxnColumn (column = "GYLSH1" )
		public java.lang.String gylsh1;		
        /**
         * CIF流水号(CIF成功响应头)
         * Length: 30
         */
		@TxnColumn (column = "CIFLSH" )
		public java.lang.String ciflsh;		
        /**
         * 客户重复次数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "KHCFCS" )
		public java.lang.Integer khcfcs;		
        /**
         * 电话重复次数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "DHCFCS" )
		public java.lang.Integer dhcfcs;		
        /**
         * 地址重复次数
         * Type: NUMBER_04_0
         */
		@TxnColumn (column = "DZCFCS" )
		public java.lang.Integer dzcfcs;		
        /**
         * 客户姓名
         * Length: 60
         */
		@TxnColumn (column = "KHXM" )
		public java.lang.String khxm;		
        /**
         * 英文姓名
         * Length: 60
         */
		@TxnColumn (column = "YWXM" )
		public java.lang.String ywxm;		
        /**
         * 称谓
         * Length: 20
         */
		@TxnColumn (column = "CW" )
		public java.lang.String cw;		
        /**
         * 曾用姓名
         * Length: 60
         */
		@TxnColumn (column = "CYXM" )
		public java.lang.String cyxm;		
        /**
         * 拼音名
         * Length: 60
         */
		@TxnColumn (column = "PYM" )
		public java.lang.String pym;		
        /**
         * 中文简称
         * Length: 60
         */
		@TxnColumn (column = "ZWJC" )
		public java.lang.String zwjc;		
        /**
         * 英文简称
         * Length: 60
         */
		@TxnColumn (column = "YWJC" )
		public java.lang.String ywjc;		
        /**
         * 国籍
         * Length: 3
         */
		@TxnColumn (column = "GJ" )
		public java.lang.String gj;		
        /**
         * 出生日期
         * Length: 8
         */
		@TxnColumn (column = "CSRQ" )
		public java.lang.String csrq;		
        /**
         * 性别
         * Length: 1
         */
		@TxnColumn (column = "XB" )
		public java.lang.String xb;		
        /**
         * 婚姻状况
         * Length: 2
         */
		@TxnColumn (column = "HYZK" )
		public java.lang.String hyzk;		
        /**
         * 民族
         * Length: 2
         */
		@TxnColumn (column = "MZ" )
		public java.lang.String mz;		
        /**
         * 居民标志
         * Length: 1
         */
		@TxnColumn (column = "JMBZ" )
		public java.lang.String jmbz;		
        /**
         * 宗教信仰
         * Length: 2
         */
		@TxnColumn (column = "ZJXY" )
		public java.lang.String zjxy;		
        /**
         * 学历
         * Length: 2
         */
		@TxnColumn (column = "XL" )
		public java.lang.String xl;		
        /**
         * 学位
         * Length: 3
         */
		@TxnColumn (column = "XW" )
		public java.lang.String xw;		
        /**
         * 工作单位
         * Length: 60
         */
		@TxnColumn (column = "GZDW" )
		public java.lang.String gzdw;		
        /**
         * 职务
         * Length: 2
         */
		@TxnColumn (column = "ZW" )
		public java.lang.String zw;		
        /**
         * 行业
         * Length: 5
         */
		@TxnColumn (column = "HY" )
		public java.lang.String hy;		
        /**
         * 职业
         * Length: 3
         */
		@TxnColumn (column = "ZY" )
		public java.lang.String zy;		
        /**
         * 证件到期日
         * Length: 8
         */
		@TxnColumn (column = "ZJDQR" )
		public java.lang.String zjdqr;		
        /**
         * 户籍地址
         * Length: 60
         */
		@TxnColumn (column = "HJDZ" )
		public java.lang.String hjdz;		
        /**
         * 发证机构代码
         * Length: 4
         */
		@TxnColumn (column = "FZJGDM" )
		public java.lang.String fzjgdm;		
        /**
         * 发证机构名称
         * Length: 60
         */
		@TxnColumn (column = "FZJGMC" )
		public java.lang.String fzjgmc;		
        /**
         * 身份核实结果
         * Length: 2
         */
		@TxnColumn (column = "SFHSJG" )
		public java.lang.String sfhsjg;		
        /**
         * 出生地
         * Length: 3
         */
		@TxnColumn (column = "CSD" )
		public java.lang.String csd;		
        /**
         * 纳税人识别号
         * Length: 20
         */
		@TxnColumn (column = "NSRSBH" )
		public java.lang.String nsrsbh;		
        /**
         * 纳税人自我申明
         * Length: 1
         */
		@TxnColumn (column = "NSRZWSM" )
		public java.lang.String nsrzwsm;		
        /**
         * 纳税人类型
         * Length: 1
         */
		@TxnColumn (column = "NSRLX" )
		public java.lang.String nsrlx;		
        /**
         * 纳税人类型最近更新时间
         * Length: 8
         */
		@TxnColumn (column = "NSRLXZJGXSJ" )
		public java.lang.String nsrlxzjgxsj;		
        /**
         * 电子邮箱
         * Length: 60
         */
		@TxnColumn (column = "DZYX" )
		public java.lang.String dzyx;		
        /**
         * 微信
         * Length: 60
         */
		@TxnColumn (column = "WX" )
		public java.lang.String wx;		
        /**
         * 客户层级
         * Length: 2
         */
		@TxnColumn (column = "KHCJ" )
		public java.lang.String khcj;		
        /**
         * 客户层级永久标志
         * Length: 1
         */
		@TxnColumn (column = "KHCJYJBZ" )
		public java.lang.String khcjyjbz;		
        /**
         * 反洗钱评级结果
         * Length: 2
         */
		@TxnColumn (column = "FXQPJJG" )
		public java.lang.String fxqpjjg;		
        /**
         * 系统开通标志
         * Length: 100
         */
		@TxnColumn (column = "XTKTBZ" )
		public java.lang.String xtktbz;		
        /**
        * 交易明细
        */
		@TxnColumn (column = "CUSTGROUP", contentClass=java.util.ArrayList.class, targetClass=TrCUSTGROUPResponse.class )		
		public List<TrCUSTGROUPResponse> CUSTGROUPResponses;
		public static class TrCUSTGROUPResponse extends TxnResponse {
	
			public TrCUSTGROUPResponse(){
				super("5247");
			}
        /**
         * 客户唯一编号
         * Length: 12
         */
		@TxnColumn (column = "KHWYBH" )
		public java.lang.String khwybh;				
        /**
         * 证件类型
         * Length: 1
         */
		@TxnColumn (column = "ZJLX" )
		public java.lang.String zjlx;				
        /**
         * 证件号码
         * Length: 20
         */
		@TxnColumn (column = "ZJHM" )
		public java.lang.String zjhm;				
        /**
         * 证件客户姓名
         * Length: 60
         */
		@TxnColumn (column = "ZJKHXM" )
		public java.lang.String zjkhxm;				
        /**
         * 外部系统证件号码
         * Length: 20
         */
		@TxnColumn (column = "WBXTZJHM" )
		public java.lang.String wbxtzjhm;				
		}
		
		public static TrCUSTGROUPResponse newTrCUSTGROUPResponse(){
			return new TrCUSTGROUPResponse();
		}

        /**
        * 交易明细
        */
		@TxnColumn (column = "PHONEGROUP", contentClass=java.util.ArrayList.class, targetClass=TrPHONEGROUPResponse.class )		
		public List<TrPHONEGROUPResponse> PHONEGROUPResponses;
		public static class TrPHONEGROUPResponse extends TxnResponse {
	
			public TrPHONEGROUPResponse(){
				super("5247");
			}
        /**
         * 电话类型
         * Length: 4
         */
		@TxnColumn (column = "DHLX" )
		public java.lang.String dhlx;				
        /**
         * 类型小序号
         * Length: 3
         */
		@TxnColumn (column = "LXXXH" )
		public java.lang.String lxxxh;				
        /**
         * 国际电话区号
         * Length: 4
         */
		@TxnColumn (column = "GJDHQH" )
		public java.lang.String gjdhqh;				
        /**
         * 国内电话区号
         * Length: 4
         */
		@TxnColumn (column = "GNDHQH" )
		public java.lang.String gndhqh;				
        /**
         * 电话号码
         * Length: 30
         */
		@TxnColumn (column = "DHHM" )
		public java.lang.String dhhm;				
        /**
         * 电话分机
         * Length: 15
         */
		@TxnColumn (column = "DHFJ" )
		public java.lang.String dhfj;				
        /**
         * 手机号码
         * Length: 30
         */
		@TxnColumn (column = "SJHM" )
		public java.lang.String sjhm;				
		}
		
		public static TrPHONEGROUPResponse newTrPHONEGROUPResponse(){
			return new TrPHONEGROUPResponse();
		}

        /**
        * 交易明细
        */
		@TxnColumn (column = "ADDRGROUP", contentClass=java.util.ArrayList.class, targetClass=TrADDRGROUPResponse.class )		
		public List<TrADDRGROUPResponse> ADDRGROUPResponses;
		public static class TrADDRGROUPResponse extends TxnResponse {
	
			public TrADDRGROUPResponse(){
				super("5247");
			}
        /**
         * 地址类型
         * Length: 4
         */
		@TxnColumn (column = "DZLX" )
		public java.lang.String dzlx;				
        /**
         * 类型小序号
         * Length: 3
         */
		@TxnColumn (column = "LXXXH1" )
		public java.lang.String lxxxh1;				
        /**
         * 国家编码
         * Length: 3
         */
		@TxnColumn (column = "GJBM" )
		public java.lang.String gjbm;				
        /**
         * 行政区划代码
         * Length: 6
         */
		@TxnColumn (column = "XZQHDM" )
		public java.lang.String xzqhdm;				
        /**
         * 街道地址
         * Length: 60
         */
		@TxnColumn (column = "JDDZ" )
		public java.lang.String jddz;				
        /**
         * 详细地址
         * Length: 230
         */
		@TxnColumn (column = "XXDZ" )
		public java.lang.String xxdz;				
        /**
         * 邮政编码
         * Length: 6
         */
		@TxnColumn (column = "YZBM" )
		public java.lang.String yzbm;				
		}
		
		public static TrADDRGROUPResponse newTrADDRGROUPResponse(){
			return new TrADDRGROUPResponse();
		}

	}
}
