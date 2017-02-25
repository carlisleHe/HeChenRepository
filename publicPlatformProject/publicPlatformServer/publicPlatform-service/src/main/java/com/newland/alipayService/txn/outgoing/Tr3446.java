package com.newland.alipayService.txn.outgoing;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.intensoft.coresyst.*;
import com.intensoft.coresyst.annotation.*;

/**
 * 信用卡账户查询
 * @author ShiZhenning
 * @since  16-03-30
 */
@Service("tr3446")
@OutgoingService
	(
		trCode = "3446" 
		,fixTxnHeader = false
	)
public class Tr3446
		extends
		TxnOutgoingService<Tr3446.Tr3446Request, Tr3446.Tr3446Response> {

	public Tr3446() {
		super(Tr3446Request.class, Tr3446Response.class);
	}

	@SuppressWarnings("serial")
	public static class Tr3446Request extends TxnRequest {

		public Tr3446Request() {
			super("3446");
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
		@TxnColumn (column = "XYKKH" )
		public java.lang.String xykkh;		
        /**
         * 二磁道信息
         * Length: 37
         */
		@TxnColumn (column = "ECDNR" )
		public java.lang.String ecdnr;		
        /**
         * 三磁道信息
         * Length: 104
         */
		@TxnColumn (column = "SCDNR" )
		public java.lang.String scdnr;		
        /**
         * 2:不检查密码;1:检查查询密码
         * Length: 1
         */
		@TxnColumn (column = "FWFS" )
		public java.lang.String fwfs;		
        /**
         * 查询密码
         * Length: 6
         */
		@TxnColumn (column = "CXMM" )
		public java.lang.String cxmm;		
	}

	@SuppressWarnings("serial")
	public static class Tr3446Response extends TxnResponse {

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
         * 信用卡卡号
         * Length: 16
         */
		@TxnColumn (column = "XYKKH" )
		public java.lang.String xykkh;		
        /**
         * 客户名称
         * Length: 30
         */
		@TxnColumn (column = "KHMC" )
		public java.lang.String khmc;		
        /**
         * 客户名称2
         * Length: 30
         */
		@TxnColumn (column = "KHMC2" )
		public java.lang.String khmc2;		
        /**
         * 卡片种类
         * Length: 30
         */
		@TxnColumn (column = "KPZL" )
		public java.lang.String kpzl;		
        /**
         * 分行编号
         * Length: 4
         */
		@TxnColumn (column = "FHBH" )
		public java.lang.String fhbh;		
        /**
         * 公司代码
         * Length: 10
         */
		@TxnColumn (column = "GSDM" )
		public java.lang.String gsdm;		
        /**
         * 公司名称
         * Length: 36
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
		@TxnColumn (column = "SQWQKJE" )
		public java.math.BigDecimal sqwqkje;		
        /**
         * 目前可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "MQKYED" )
		public java.math.BigDecimal mqkyed;		
        /**
         * 第二币种信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DEBZXYED" )
		public java.math.BigDecimal debzxyed;		
        /**
         * 第二币种可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DEBZKYED" )
		public java.math.BigDecimal debzkyed;		
        /**
         * 最后还款日
         * Type: DATE
         */
		@TxnColumn (column = "ZHHKR" )
		public java.util.Date zhhkr;		
        /**
         * 人民币自动还款帐号
         * Length: 19
         */
		@TxnColumn (column = "RMBHKZH" )
		public java.lang.String rmbhkzh;		
        /**
         * 自扣还款方式
         * Length: 1
         */
		@TxnColumn (column = "ZKHKFS" )
		public java.lang.String zkhkfs;		
        /**
         * 上次还款日期
         * Type: DATE
         */
		@TxnColumn (column = "SCHKRQ" )
		public java.util.Date schkrq;		
        /**
         * 上次还款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "SCHKJE" )
		public java.math.BigDecimal schkje;		
        /**
         * 逾期金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YQJE" )
		public java.math.BigDecimal yqje;		
        /**
         * 逾期天数
         * Length: 3
         */
		@TxnColumn (column = "YQTS" )
		public java.lang.String yqts;		
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
         * 争议金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "ZYJE" )
		public java.math.BigDecimal zyje;		
        /**
         * 本期调账金额符号
         * Length: 1
         */
		@TxnColumn (column = "BQTZJEFH" )
		public java.lang.String bqtzjefh;		
        /**
         * 本期调整金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BQTZJE" )
		public java.math.BigDecimal bqtzje;		
        /**
         * 账户状态
         * Length: 1
         */
		@TxnColumn (column = "ZHZT" )
		public java.lang.String zhzt;		
        /**
         * 账户状态日期
         * Type: DATE
         */
		@TxnColumn (column = "ZHZTRQ" )
		public java.util.Date zhztrq;		
        /**
         * 本期消费净额符号
         * Length: 1
         */
		@TxnColumn (column = "BQXFJEFH" )
		public java.lang.String bqxfjefh;		
        /**
         * 本期消费净额(人民币)
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBBQXFJE" )
		public java.math.BigDecimal bbbqxfje;		
        /**
         * 预借现金信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YJXJXYED" )
		public java.math.BigDecimal yjxjxyed;		
        /**
         * 预借现金可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YJXJKYED" )
		public java.math.BigDecimal yjxjkyed;		
        /**
         * 分期付款可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "FQFKXYED" )
		public java.math.BigDecimal fqfkxyed;		
        /**
         * 分期付款可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "FQFKKYED" )
		public java.math.BigDecimal fqfkkyed;		
        /**
         * 本币账面余额符号
         * Length: 1
         */
		@TxnColumn (column = "BBZMYEFH" )
		public java.lang.String bbzmyefh;		
        /**
         * 本币账面余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBZMYE" )
		public java.math.BigDecimal bbzmye;		
        /**
         * 外币账面余额符号
         * Length: 1
         */
		@TxnColumn (column = "WBZMYEFH" )
		public java.lang.String wbzmyefh;		
        /**
         * 外币账面余额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBZMYE" )
		public java.math.BigDecimal wbzmye;		
        /**
         * 账户累计总积分
         * Length: 10
         */
		@TxnColumn (column = "ZHLJZJF" )
		public java.lang.String zhljzjf;		
        /**
         * 账户累计可兑换积分
         * Length: 10
         */
		@TxnColumn (column = "ZHLJKDHJF" )
		public java.lang.String zhljkdhjf;		
        /**
         * 本币预收滞纳金
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBYSZNJ" )
		public java.math.BigDecimal bbysznj;		
        /**
         * 外币预收滞纳金
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBYSZNJ" )
		public java.math.BigDecimal wbysznj;		
        /**
         * 外币预授权金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBYSQJE" )
		public java.math.BigDecimal wbysqje;		
        /**
         * 年费代码
         * Length: 2
         */
		@TxnColumn (column = "NFDM" )
		public java.lang.String nfdm;		
        /**
         * 利率代码
         * Length: 2
         */
		@TxnColumn (column = "LLDM" )
		public java.lang.String lldm;		
        /**
         * 年利率
         * Type: NUMBER_08_4
         */
		@TxnColumn (column = "NLL" )
		public java.math.BigDecimal nll;		
        /**
         * 美元自动还款帐号
         * Length: 20
         */
		@TxnColumn (column = "MYBHKZH" )
		public java.lang.String mybhkzh;		
        /**
         * 外币自扣还款方式
         * Length: 1
         */
		@TxnColumn (column = "WBHKFS" )
		public java.lang.String wbhkfs;		
        /**
         * 1
         * Length: 1
         */
		@TxnColumn (column = "XXYY" )
		public java.lang.String xxyy;		
        /**
         * 帐单日
         * Length: 2
         */
		@TxnColumn (column = "ZDR" )
		public java.lang.String zdr;		
        /**
         * 最近一次账单日
         * Type: DATE
         */
		@TxnColumn (column = "ZJYCZDR" )
		public java.util.Date zjyczdr;		
        /**
         * 本币上次交易日期
         * Type: DATE
         */
		@TxnColumn (column = "BBSCJYRQ" )
		public java.util.Date bbscjyrq;		
        /**
         * 外币上次交易日期
         * Type: DATE
         */
		@TxnColumn (column = "WBSCJYRQ" )
		public java.util.Date wbscjyrq;		
        /**
         * 外币上次还款日期
         * Type: DATE
         */
		@TxnColumn (column = "WBSCHKRQ" )
		public java.util.Date wbschkrq;		
        /**
         * 外币上次还款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBSCHKJE" )
		public java.math.BigDecimal wbschkje;		
        /**
         * 人民币帐单金额符号
         * Length: 1
         */
		@TxnColumn (column = "RMBZDJEFH" )
		public java.lang.String rmbzdjefh;		
        /**
         * 人民币帐单金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "RMBZDJE" )
		public java.math.BigDecimal rmbzdje;		
        /**
         * 美元帐单金额符号
         * Length: 1
         */
		@TxnColumn (column = "MYBZDJEFH" )
		public java.lang.String mybzdjefh;		
        /**
         * 美元帐单金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "MYZDJE" )
		public java.math.BigDecimal myzdje;		
        /**
         * 本币账单周期已处理交易数
         * Length: 4
         */
		@TxnColumn (column = "BBZQYCLJYS" )
		public java.lang.String bbzqycljys;		
        /**
         * 外币账单周期已处理交易数
         * Length: 4
         */
		@TxnColumn (column = "WBZQYCLJYS" )
		public java.lang.String wbzqycljys;		
        /**
         * 应还人民币最低金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YHRMBJE" )
		public java.math.BigDecimal yhrmbje;		
        /**
         * 应还美元最低金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YHMYJE" )
		public java.math.BigDecimal yhmyje;		
        /**
         * 本币本期预借现金
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBBQYJXJ" )
		public java.math.BigDecimal bbbqyjxj;		
        /**
         * 外币本期预借现金
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBBQYJXJ" )
		public java.math.BigDecimal wbbqyjxj;		
        /**
         * 本期消费净额(正负号)
         * Length: 1
         */
		@TxnColumn (column = "BQXFJE" )
		public java.lang.String bqxfje;		
        /**
         * 外币本期消费净额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBBQXFJE" )
		public java.math.BigDecimal wbbqxfje;		
        /**
         * 本币本期还款金额符号
         * Length: 1
         */
		@TxnColumn (column = "BBBQHKJEFH" )
		public java.lang.String bbbqhkjefh;		
        /**
         * 本币本期还款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBBQHKJE" )
		public java.math.BigDecimal bbbqhkje;		
        /**
         * 外币本期还款金额符号
         * Length: 1
         */
		@TxnColumn (column = "WBBQHKJEFH" )
		public java.lang.String wbbqhkjefh;		
        /**
         * 外币本期还款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBBQHKJE" )
		public java.math.BigDecimal wbbqhkje;		
        /**
         * 本币应收利息
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBYSLX" )
		public java.math.BigDecimal bbyslx;		
        /**
         * 外币应收利息
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBYSLX" )
		public java.math.BigDecimal wbyslx;		
        /**
         * 外币本期调账金额符号
         * Length: 1
         */
		@TxnColumn (column = "WBBQTZJEFH" )
		public java.lang.String wbbqtzjefh;		
        /**
         * 外币本期调整金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBBQTZJE" )
		public java.math.BigDecimal wbbqtzje;		
        /**
         * 本币本期新增费用金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BBBQXZFYJE" )
		public java.math.BigDecimal bbbqxzfyje;		
        /**
         * 外币本期新增费用金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBBQXZFYJE" )
		public java.math.BigDecimal wbbqxzfyje;		
        /**
         * 外币争议金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBZYJE" )
		public java.math.BigDecimal wbzyje;		
        /**
         * 外币预借现金可用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBYJXJKYED" )
		public java.math.BigDecimal wbyjxjkyed;		
        /**
         * 系统日期
         * Type: DATE
         */
		@TxnColumn (column = "XTRQ" )
		public java.util.Date xtrq;		
        /**
         * 分期付款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "FQFKJE" )
		public java.math.BigDecimal fqfkje;		
        /**
         * 当前应还人名币金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DQYHRMBJE" )
		public java.math.BigDecimal dqyhrmbje;		
        /**
         * 当前应还美元金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DQYHMYJE" )
		public java.math.BigDecimal dqyhmyje;		
        /**
         * 分期付款授权未请款金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "FQFKSQWQKJE" )
		public java.math.BigDecimal fqfksqwqkje;		
        /**
         * 分期付款剩余本金总额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "FQFKSYBJZE" )
		public java.math.BigDecimal fqfksybjze;		
        /**
         * 本月新增分期付款额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "BYXZFQFKE" )
		public java.math.BigDecimal byxzfqfke;		
        /**
         * 公司卡标志
         * Length: 1
         */
		@TxnColumn (column = "GSKBZ" )
		public java.lang.String gskbz;		
        /**
         * 逾期或超限状态
         * Length: 1
         */
		@TxnColumn (column = "YQHCXZT" )
		public java.lang.String yqhcxzt;		
        /**
         * 上次信用额度更改日期
         * Type: DATE
         */
		@TxnColumn (column = "SCXYEDGGRQ" )
		public java.util.Date scxyedggrq;		
        /**
         * 新账单日
         * Length: 2
         */
		@TxnColumn (column = "XZDR" )
		public java.lang.String xzdr;		
        /**
         * 临时信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "LSXYED" )
		public java.math.BigDecimal lsxyed;		
        /**
         * 临时额度生效日期
         * Type: DATE
         */
		@TxnColumn (column = "LSEDSXRQ" )
		public java.util.Date lsedsxrq;		
        /**
         * 临时额度失效日期
         * Type: DATE
         */
		@TxnColumn (column = "LSEDSHIXRQ" )
		public java.util.Date lsedshixrq;		
        /**
         * 预借积分等值金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "YJJFDZJE" )
		public java.math.BigDecimal yjjfdzje;		
        /**
         * 当前信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DQXYED" )
		public java.math.BigDecimal dqxyed;		
        /**
         * 第二币种当前信用额度
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "DEBZDQXYED" )
		public java.math.BigDecimal debzdqxyed;		
        /**
         * 外币逾期金额
         * Type: NUMBER_15_2
         */
		@TxnColumn (column = "WBYQJE" )
		public java.math.BigDecimal wbyqje;		
        /**
         * 外币逾期天数
         * Length: 3
         */
		@TxnColumn (column = "WBYQTS" )
		public java.lang.String wbyqts;		
        /**
         * 累计总积分符号
         * Length: 1
         */
		@TxnColumn (column = "LJZJFFH" )
		public java.lang.String ljzjffh;		
        /**
         * 累计可兑换积分符号
         * Length: 1
         */
		@TxnColumn (column = "LJKDHJFFH" )
		public java.lang.String ljkdhjffh;		
        /**
         * 保留字段
         * Length: 198
         */
		@TxnColumn (column = "BLZD" )
		public java.lang.String blzd;		
	}
}
