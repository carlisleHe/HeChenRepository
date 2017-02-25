package com.newland.alipayService.txn.service;

import java.io.File;

import com.intensoft.coresyst.exception.CoreSystException;
import com.newland.alipayService.txn.outgoing.Tr1107.Tr1107Request;
import com.newland.alipayService.txn.outgoing.Tr1107.Tr1107Response;
import com.newland.alipayService.txn.outgoing.Tr1109.Tr1109Request;
import com.newland.alipayService.txn.outgoing.Tr1109.Tr1109Response;
import com.newland.alipayService.txn.outgoing.Tr1110.Tr1110Request;
import com.newland.alipayService.txn.outgoing.Tr1110.Tr1110Response;
import com.newland.alipayService.txn.outgoing.Tr1699.Tr1699Request;
import com.newland.alipayService.txn.outgoing.Tr1699.Tr1699Response;
import com.newland.alipayService.txn.outgoing.Tr2560.Tr2560Request;
import com.newland.alipayService.txn.outgoing.Tr2560.Tr2560Response;
import com.newland.alipayService.txn.outgoing.Tr2569.Tr2569Request;
import com.newland.alipayService.txn.outgoing.Tr2569.Tr2569Response;
import com.newland.alipayService.txn.outgoing.Tr3295.Tr3295Request;
import com.newland.alipayService.txn.outgoing.Tr3295.Tr3295Response;
import com.newland.alipayService.txn.outgoing.Tr3416.Tr3416Request;
import com.newland.alipayService.txn.outgoing.Tr3416.Tr3416Response;
import com.newland.alipayService.txn.outgoing.Tr3419.Tr3419Request;
import com.newland.alipayService.txn.outgoing.Tr3419.Tr3419Response;
import com.newland.alipayService.txn.outgoing.Tr3420.Tr3420Request;
import com.newland.alipayService.txn.outgoing.Tr3420.Tr3420Response;
import com.newland.alipayService.txn.outgoing.Tr3422.Tr3422Request;
import com.newland.alipayService.txn.outgoing.Tr3422.Tr3422Response;
import com.newland.alipayService.txn.outgoing.Tr3425.Tr3425Request;
import com.newland.alipayService.txn.outgoing.Tr3425.Tr3425Response;
import com.newland.alipayService.txn.outgoing.Tr3431.Tr3431Request;
import com.newland.alipayService.txn.outgoing.Tr3431.Tr3431Response;
import com.newland.alipayService.txn.outgoing.Tr3446.Tr3446Request;
import com.newland.alipayService.txn.outgoing.Tr3446.Tr3446Response;
import com.newland.alipayService.txn.outgoing.Tr3502.Tr3502Request;
import com.newland.alipayService.txn.outgoing.Tr3502.Tr3502Response;
import com.newland.alipayService.txn.outgoing.Tr3779.Tr3779Request;
import com.newland.alipayService.txn.outgoing.Tr3779.Tr3779Response;
import com.newland.alipayService.txn.outgoing.Tr3796.Tr3796Request;
import com.newland.alipayService.txn.outgoing.Tr3796.Tr3796Response;
import com.newland.alipayService.txn.outgoing.Tr3797.Tr3797Request;
import com.newland.alipayService.txn.outgoing.Tr3797.Tr3797Response;
import com.newland.alipayService.txn.outgoing.Tr3873.Tr3873Request;
import com.newland.alipayService.txn.outgoing.Tr3873.Tr3873Response;
import com.newland.alipayService.txn.outgoing.Tr3900.Tr3900Request;
import com.newland.alipayService.txn.outgoing.Tr3900.Tr3900Response;
import com.newland.alipayService.txn.outgoing.Tr3902.Tr3902Request;
import com.newland.alipayService.txn.outgoing.Tr3902.Tr3902Response;
import com.newland.alipayService.txn.outgoing.Tr3905.Tr3905Request;
import com.newland.alipayService.txn.outgoing.Tr3905.Tr3905Response;
import com.newland.alipayService.txn.outgoing.Tr3908.Tr3908Request;
import com.newland.alipayService.txn.outgoing.Tr3908.Tr3908Response;
import com.newland.alipayService.txn.outgoing.Tr3909.Tr3909Request;
import com.newland.alipayService.txn.outgoing.Tr3909.Tr3909Response;
import com.newland.alipayService.txn.outgoing.Tr4614.Tr4614Request;
import com.newland.alipayService.txn.outgoing.Tr4614.Tr4614Response;
import com.newland.alipayService.txn.outgoing.Tr5070.Tr5070Request;
import com.newland.alipayService.txn.outgoing.Tr5070.Tr5070Response;
import com.newland.alipayService.txn.outgoing.Tr5205.Tr5205Request;
import com.newland.alipayService.txn.outgoing.Tr5205.Tr5205Response;
import com.newland.alipayService.txn.outgoing.Tr5247.Tr5247Request;
import com.newland.alipayService.txn.outgoing.Tr5247.Tr5247Response;
import com.newland.alipayService.txn.outgoing.Tr8881.Tr8881Request;
import com.newland.alipayService.txn.outgoing.Tr8881.Tr8881Response;
import com.newland.alipayService.txn.outgoing.TrD001.TrD001Request;
import com.newland.alipayService.txn.outgoing.TrD001.TrD001Response;
import com.newland.alipayService.txn.outgoing.TrD003.TrD003Request;
import com.newland.alipayService.txn.outgoing.TrD003.TrD003Response;
import com.newland.alipayService.txn.outgoing.TrD004.TrD004Request;
import com.newland.alipayService.txn.outgoing.TrD004.TrD004Response;
import com.newland.alipayService.txn.outgoing.TrD005.TrD005Request;
import com.newland.alipayService.txn.outgoing.TrD005.TrD005Response;
import com.newland.alipayService.txn.outgoing.TrD101.TrD101Request;
import com.newland.alipayService.txn.outgoing.TrD101.TrD101Response;
import com.newland.alipayService.txn.outgoing.TrD102.TrD102Request;
import com.newland.alipayService.txn.outgoing.TrD102.TrD102Response;
import com.newland.alipayService.txn.outgoing.TrD104.TrD104Request;
import com.newland.alipayService.txn.outgoing.TrD104.TrD104Response;
import com.newland.alipayService.txn.outgoing.TrW01.TrW01Request;
import com.newland.alipayService.txn.outgoing.TrW01.TrW01Response;
import com.newland.alipayService.txn.outgoing.TrW05.TrW05Request;
import com.newland.alipayService.txn.outgoing.TrW05.TrW05Response;
/**
 * 
 * @ClassName: TxnService  
 * @Description: 交易服务类 
 * @author: xuzz 
 * @date:2014-3-12 下午02:41:16
 */
public interface TxnService {
	
	
    /**
     * 取txnAdapterPara.getAttachmentTempPath();下的文件目录
     * @return
     */
	public String getTempPath();

/**

	 * 开户机构查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3873Response doTr3873(Tr3873Request req) throws CoreSystException; 
	
	/**
	 * 产品余额查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr5205Response doTr5205(Tr5205Request req) throws CoreSystException; 
	
	/**
	 * 兴业宝查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD001Response doTrD001(TrD001Request req) throws CoreSystException;
	
	/**
	 * 兴业宝相关收益信息查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD003Response doTrD003(TrD003Request req) throws CoreSystException;
	/**
	 * 兴业宝账户交易明细查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD004Response doTrD004(TrD004Request req) throws CoreSystException;
	/**
	 * 兴业宝账户收益明细查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD005Response doTrD005(TrD005Request req) throws CoreSystException;
		
	

	/**
	 * 2569交易（借记卡交易明细查询，查询最近10笔）
	 * @param req
	 * @return 交易结果
	 */
	public Tr2569Response doTr2569(Tr2569Request req) throws CoreSystException; 
	/**
	 * 3446交易（信用卡账户信息查询）
	 * @param req
	 * @return 交易结果
	 */
	public Tr3446Response doTr3446(Tr3446Request req) throws CoreSystException;
	/**
	 * 3419交易（信用卡额度信息查询）
	 * @param req
	 * @return 交易结果
	 */
	public Tr3419Response doTr3419(Tr3419Request req) throws CoreSystException;
	/**
	 * 3422交易（信用卡卡片信息查询）
	 * @param req
	 * @return 交易结果
	 */
	public Tr3422Response doTr3422(Tr3422Request req) throws CoreSystException;
	/**
	 * 3431交易（信用卡验密）
	 * @param req
	 * @return
	 */
	public Tr3431Response doTr3431(Tr3431Request req) throws CoreSystException;
	
	/**
	 * 3900交易（对私验密）
	 * 
	 * @param req
	 * @return
	 */
	public Tr3900Response doTr3900(Tr3900Request req) throws CoreSystException;
	/**
	 * 1699交易（借记卡账户信息查询）
	 * @param req
	 * @return
	 */
	public Tr1699Response doTr1699(Tr1699Request req) throws CoreSystException;
	
	/**
	 * 1107交易（上行微信接口）
	 * @param req
	 * @return
	 */
	public Tr1107Response doTr1107(Tr1107Request req) throws CoreSystException;
	/**
	 * 借记卡余额查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3502Response doTr3502(Tr3502Request req) throws CoreSystException;

	/**
	 * 核心动户通知文件查询
	 * 
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3295Response doTr3295(Tr3295Request req) throws CoreSystException;
	/**
	 * 信用卡账单交易查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3425Response doTr3425(Tr3425Request req) throws CoreSystException;
	/**
	 * 信用卡未出账单交易查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr5070Response doTr5070(Tr5070Request req) throws CoreSystException;

	/**
	 * 交易:获取文件信息CTSP
	 * @param fileMac
	 * @param isTuxedo 是否文件名前加tuxedo:
	 * @return 文件附件信息
	 */
	public String getCTSPFile(String fileMac,boolean isTuxedo) throws CoreSystException;

	/**
	 * 交易:发送文件CTSP
	 * @param file 文件
	 * @return 文件mac码
	 */
	public String putCTSPFile(File file) throws CoreSystException;
	/**
	 * 交易:获取文件信息(网关获取)
	 * @param fileMac
	 * @param isTuxedo 是否文件名前加tuxedo:
	 * @return 文件附件信息
	 */
	public String getFile(String fileMac,boolean isTuxedo) throws CoreSystException;
	/**
	 * 交易:获取文件信息(需文件名)
	 * 
	 * @param fileMac
	 * @param fileName
	 * @return 文件附件信息
	 */
	public String getTuxedoFile(String fileMac, String fileName)throws CoreSystException;
	/**
	 * 交易:发送文件（网关获取）
	 * @param file 文件
	 * @param 是否文件名加tuxedo:
	 * @return 文件mac码
	 */
	public String putFile(File file,boolean isTuxedo) throws CoreSystException;
	
	/**
	 * 交易：查询客户资料信息相比于1699有校验客户凭证代号
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr2560Response doTr2560(Tr2560Request req) throws CoreSystException;
	/**
	 * 交易：互联网贷款申请
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3779Response doTr3779(Tr3779Request req) throws CoreSystException;
	/**
	 * 交易：互联网贷款申请进度查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3797Response doTr3797(Tr3797Request req) throws CoreSystException;
	/**
	 * 交易：互联网贷款批量查询申请进度
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3796Response doTr3796(Tr3796Request req) throws CoreSystException;
	/**
	 *  兴业宝客户相关收益信息查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD102Response doTrD102(TrD102Request req) throws CoreSystException;
	/**
	 * 兴业宝相关收益信息查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD104Response doTrD104(TrD104Request req) throws CoreSystException;
/**
	 * 客户开卡信息查询
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3902Response doTr3902(Tr3902Request req) throws CoreSystException;
	/**
	 * 开卡
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrW01Response doTrW01(TrW01Request req) throws CoreSystException;
	/**
	 * 查询开卡信息
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrW05Response doTrW05(TrW05Request req) throws CoreSystException;
	/**
	 * 发普通短信接口
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3909Response doTr3909(Tr3909Request req) throws CoreSystException;
	/**
	 * 发短信口令接口
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3908Response doTr3908(Tr3908Request req) throws CoreSystException;
/**
	 * 查询客户购买兴业宝信息
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public TrD101Response doTrD101(TrD101Request req) throws CoreSystException;
	/**
	 * 客户访问客服
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr1109Response doTr1109(Tr1109Request req) throws CoreSystException;
	/**
	 * 客户退出通知客服
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr1110Response doTr1110(Tr1110Request req) throws CoreSystException;
	/**
	 * 短信由客服处理
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3905Response doTr3905(Tr3905Request req) throws CoreSystException;
	/**
	 * 信用卡是否激活/设置信用卡查询密码
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3416Response doTr3416(Tr3416Request req) throws CoreSystException;
	/**
	 * 查询信用卡设置的手机号
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr3420Response doTr3420(Tr3420Request req) throws CoreSystException;
	/**
	 * 设置交易密码
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr4614Response doTr4614(Tr4614Request req) throws CoreSystException;
	
	/**
	 * 从动态口令取 手机号
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr8881Response doTr8881(Tr8881Request req) throws CoreSystException;
	/**
	 * 从核心系统中取 手机号
	 * @param req
	 * @return
	 * @throws CoreSystException
	 */
	public Tr5247Response doTr5247(Tr5247Request req) throws CoreSystException;
}
