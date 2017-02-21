package com.cib.alipayserver.txn.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.common.AppExCode;
import com.cib.alipayserver.idgen.service.IdentifierGenerator;
import com.cib.alipayserver.txn.client.CTSPFileService;
import com.cib.alipayserver.txn.outgoing.Tr1107;
import com.cib.alipayserver.txn.outgoing.Tr1109;
import com.cib.alipayserver.txn.outgoing.Tr1110;
import com.cib.alipayserver.txn.outgoing.Tr1699;
import com.cib.alipayserver.txn.outgoing.Tr2560;
import com.cib.alipayserver.txn.outgoing.Tr2569;
import com.cib.alipayserver.txn.outgoing.Tr3295;
import com.cib.alipayserver.txn.outgoing.Tr3416;
import com.cib.alipayserver.txn.outgoing.Tr3419;
import com.cib.alipayserver.txn.outgoing.Tr3420;
import com.cib.alipayserver.txn.outgoing.Tr3422;
import com.cib.alipayserver.txn.outgoing.Tr3425;
import com.cib.alipayserver.txn.outgoing.Tr3431;
import com.cib.alipayserver.txn.outgoing.Tr3446;
import com.cib.alipayserver.txn.outgoing.Tr3502;
import com.cib.alipayserver.txn.outgoing.Tr3779;
import com.cib.alipayserver.txn.outgoing.Tr3796;
import com.cib.alipayserver.txn.outgoing.Tr3797;
import com.cib.alipayserver.txn.outgoing.Tr3873;
import com.cib.alipayserver.txn.outgoing.Tr3900;
import com.cib.alipayserver.txn.outgoing.Tr3902;
import com.cib.alipayserver.txn.outgoing.Tr3905;
import com.cib.alipayserver.txn.outgoing.Tr3908;
import com.cib.alipayserver.txn.outgoing.Tr3909;
import com.cib.alipayserver.txn.outgoing.Tr4614;
import com.cib.alipayserver.txn.outgoing.Tr5070;
import com.cib.alipayserver.txn.outgoing.Tr5205;
import com.cib.alipayserver.txn.outgoing.Tr5247;
import com.cib.alipayserver.txn.outgoing.Tr8881;
import com.cib.alipayserver.txn.outgoing.TrD001;
import com.cib.alipayserver.txn.outgoing.TrD003;
import com.cib.alipayserver.txn.outgoing.TrD004;
import com.cib.alipayserver.txn.outgoing.TrD005;
import com.cib.alipayserver.txn.outgoing.TrD101;
import com.cib.alipayserver.txn.outgoing.TrD102;
import com.cib.alipayserver.txn.outgoing.TrD104;
import com.cib.alipayserver.txn.outgoing.TrW01;
import com.cib.alipayserver.txn.outgoing.TrW05;
import com.cib.alipayserver.txn.outgoing.Tr1107.Tr1107Request;
import com.cib.alipayserver.txn.outgoing.Tr1107.Tr1107Response;
import com.cib.alipayserver.txn.outgoing.Tr1109.Tr1109Request;
import com.cib.alipayserver.txn.outgoing.Tr1109.Tr1109Response;
import com.cib.alipayserver.txn.outgoing.Tr1110.Tr1110Request;
import com.cib.alipayserver.txn.outgoing.Tr1110.Tr1110Response;
import com.cib.alipayserver.txn.outgoing.Tr1699.Tr1699Request;
import com.cib.alipayserver.txn.outgoing.Tr1699.Tr1699Response;
import com.cib.alipayserver.txn.outgoing.Tr2560.Tr2560Request;
import com.cib.alipayserver.txn.outgoing.Tr2560.Tr2560Response;
import com.cib.alipayserver.txn.outgoing.Tr2569.Tr2569Request;
import com.cib.alipayserver.txn.outgoing.Tr2569.Tr2569Response;
import com.cib.alipayserver.txn.outgoing.Tr3295.Tr3295Request;
import com.cib.alipayserver.txn.outgoing.Tr3295.Tr3295Response;
import com.cib.alipayserver.txn.outgoing.Tr3416.Tr3416Request;
import com.cib.alipayserver.txn.outgoing.Tr3416.Tr3416Response;
import com.cib.alipayserver.txn.outgoing.Tr3419.Tr3419Request;
import com.cib.alipayserver.txn.outgoing.Tr3419.Tr3419Response;
import com.cib.alipayserver.txn.outgoing.Tr3420.Tr3420Request;
import com.cib.alipayserver.txn.outgoing.Tr3420.Tr3420Response;
import com.cib.alipayserver.txn.outgoing.Tr3422.Tr3422Request;
import com.cib.alipayserver.txn.outgoing.Tr3422.Tr3422Response;
import com.cib.alipayserver.txn.outgoing.Tr3425.Tr3425Request;
import com.cib.alipayserver.txn.outgoing.Tr3425.Tr3425Response;
import com.cib.alipayserver.txn.outgoing.Tr3431.Tr3431Request;
import com.cib.alipayserver.txn.outgoing.Tr3431.Tr3431Response;
import com.cib.alipayserver.txn.outgoing.Tr3446.Tr3446Request;
import com.cib.alipayserver.txn.outgoing.Tr3446.Tr3446Response;
import com.cib.alipayserver.txn.outgoing.Tr3502.Tr3502Request;
import com.cib.alipayserver.txn.outgoing.Tr3502.Tr3502Response;
import com.cib.alipayserver.txn.outgoing.Tr3779.Tr3779Request;
import com.cib.alipayserver.txn.outgoing.Tr3779.Tr3779Response;
import com.cib.alipayserver.txn.outgoing.Tr3796.Tr3796Request;
import com.cib.alipayserver.txn.outgoing.Tr3796.Tr3796Response;
import com.cib.alipayserver.txn.outgoing.Tr3797.Tr3797Request;
import com.cib.alipayserver.txn.outgoing.Tr3797.Tr3797Response;
import com.cib.alipayserver.txn.outgoing.Tr3873.Tr3873Request;
import com.cib.alipayserver.txn.outgoing.Tr3873.Tr3873Response;
import com.cib.alipayserver.txn.outgoing.Tr3900.Tr3900Request;
import com.cib.alipayserver.txn.outgoing.Tr3900.Tr3900Response;
import com.cib.alipayserver.txn.outgoing.Tr3902.Tr3902Request;
import com.cib.alipayserver.txn.outgoing.Tr3902.Tr3902Response;
import com.cib.alipayserver.txn.outgoing.Tr3905.Tr3905Request;
import com.cib.alipayserver.txn.outgoing.Tr3905.Tr3905Response;
import com.cib.alipayserver.txn.outgoing.Tr3908.Tr3908Request;
import com.cib.alipayserver.txn.outgoing.Tr3908.Tr3908Response;
import com.cib.alipayserver.txn.outgoing.Tr3909.Tr3909Request;
import com.cib.alipayserver.txn.outgoing.Tr3909.Tr3909Response;
import com.cib.alipayserver.txn.outgoing.Tr4614.Tr4614Request;
import com.cib.alipayserver.txn.outgoing.Tr4614.Tr4614Response;
import com.cib.alipayserver.txn.outgoing.Tr5070.Tr5070Request;
import com.cib.alipayserver.txn.outgoing.Tr5070.Tr5070Response;
import com.cib.alipayserver.txn.outgoing.Tr5205.Tr5205Request;
import com.cib.alipayserver.txn.outgoing.Tr5205.Tr5205Response;
import com.cib.alipayserver.txn.outgoing.Tr5247.Tr5247Request;
import com.cib.alipayserver.txn.outgoing.Tr5247.Tr5247Response;
import com.cib.alipayserver.txn.outgoing.Tr8881.Tr8881Request;
import com.cib.alipayserver.txn.outgoing.Tr8881.Tr8881Response;
import com.cib.alipayserver.txn.outgoing.TrD001.TrD001Request;
import com.cib.alipayserver.txn.outgoing.TrD001.TrD001Response;
import com.cib.alipayserver.txn.outgoing.TrD003.TrD003Request;
import com.cib.alipayserver.txn.outgoing.TrD003.TrD003Response;
import com.cib.alipayserver.txn.outgoing.TrD004.TrD004Request;
import com.cib.alipayserver.txn.outgoing.TrD004.TrD004Response;
import com.cib.alipayserver.txn.outgoing.TrD005.TrD005Request;
import com.cib.alipayserver.txn.outgoing.TrD005.TrD005Response;
import com.cib.alipayserver.txn.outgoing.TrD101.TrD101Request;
import com.cib.alipayserver.txn.outgoing.TrD101.TrD101Response;
import com.cib.alipayserver.txn.outgoing.TrD102.TrD102Request;
import com.cib.alipayserver.txn.outgoing.TrD102.TrD102Response;
import com.cib.alipayserver.txn.outgoing.TrD104.TrD104Request;
import com.cib.alipayserver.txn.outgoing.TrD104.TrD104Response;
import com.cib.alipayserver.txn.outgoing.TrW01.TrW01Request;
import com.cib.alipayserver.txn.outgoing.TrW01.TrW01Response;
import com.cib.alipayserver.txn.outgoing.TrW05.TrW05Request;
import com.cib.alipayserver.txn.outgoing.TrW05.TrW05Response;
import com.cib.alipayserver.txn.service.TxnService;
import com.cib.alipayserver.util.AccountUtil;
import com.intensoft.coresyst.CIBTxnAdapterPara;
import com.intensoft.coresyst.OutgoingTxnAdapter;
import com.intensoft.coresyst.exception.CoreSystException;
import com.intensoft.exception.AppRTException;

/**
 * 
 * @ClassName: TxnServiceImpl
 * @Description: 交易服务实现类
 * @author: xuzz
 * @date:2014-3-20 下午01:39:30
 */
@Service("txnService")
public class TxnServiceImpl implements TxnService {
	@Resource(name = "tuxedoOutgoingTxnAdapter")
	private OutgoingTxnAdapter tuxedoOutgoingTxnAdapter;
	@Resource(name = "outgoingTxnAdapter")
	private OutgoingTxnAdapter outgoingTxnAdapter;
	@Resource(name = "tuxedoCIBTxnAdapterPara")
	private CIBTxnAdapterPara txnAdapterPara;
	@Resource(name = "tr1699")
	private Tr1699 tr1699;
	@Resource(name = "tr1107")
	private Tr1107 tr1107;
	@Resource(name = "tr1109")
	private Tr1109 tr1109;
	@Resource(name = "tr1110")
	private Tr1110 tr1110;
	@Resource(name = "tr2569")
	private Tr2569 tr2569;
	@Resource(name = "tr3446")
	private Tr3446 tr3446;
	@Resource(name = "tr3419")
	private Tr3419 tr3419;
	@Resource(name = "tr3431")
	private Tr3431 tr3431;
	@Resource(name = "tr3422")
	private Tr3422 tr3422;
	@Resource(name = "tr3900")
	private Tr3900 tr3900;
	@Resource(name = "tr3502")
	private Tr3502 tr3502;
	@Resource(name = "tr3295")
	private Tr3295 tr3295;
	@Resource(name = "tr3425")
	private Tr3425 tr3425;
	@Resource(name = "tr5070")
	private Tr5070 tr5070;
	@Resource(name = "trD001")
	private TrD001 trD001;
	@Resource(name = "trD101")
	private TrD101 trD101;
	@Resource(name = "trD102")
	private TrD102 trD102;
	@Resource(name = "trD104")
	private TrD104 trD104;
	@Resource(name = "trD003")
	private TrD003 trD003;
	@Resource(name = "trD004")
	private TrD004 trD004;
	@Resource(name = "trD005")
	private TrD005 trD005;
	@Resource(name = "tr5205")
	private Tr5205 tr5205;
	@Resource(name = "tr3873")
	private Tr3873 tr3873;
	@Resource(name = "tr2560")
	private Tr2560 tr2560;
	@Resource(name = "tr3779")
	private Tr3779 tr3779;
	@Resource(name = "tr3797")
	private Tr3797 tr3797;
	@Resource(name = "tr3796")
	private Tr3796 tr3796;
	@Resource(name = "tr3902")
	private Tr3902 tr3902;
	@Resource(name = "trW01")
	private TrW01 trW01;
	@Resource(name = "trW05")
	private TrW05 trW05;	
	@Resource(name = "tr3909")
	private Tr3909 tr3909;	
	@Resource(name = "tr3908")
	private Tr3908 tr3908;
	@Resource(name = "tr3905")
	private Tr3905 tr3905;
	@Resource(name = "tr3416")
	private Tr3416 tr3416;
	@Resource(name = "tr3420")
	private Tr3420 tr3420;
	@Resource(name = "tr4614")
	private Tr4614 tr4614;
	@Resource(name = "tr8881")
	private Tr8881 tr8881;
	@Resource(name = "tr5247")
	private Tr5247 tr5247;
	@Resource(name = "snoGenerator")
	private IdentifierGenerator snoGenerator;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private Date date = new Date();
	
	@Override
	public Tr3873Response doTr3873(Tr3873Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		// 发起交易
		Tr3873.Tr3873Response resp = tr3873.excuteTxn(req);
		return resp;
	}
	
	@Override
	public Tr5205Response doTr5205(Tr5205Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		// 发起交易
		Tr5205.Tr5205Response resp = tr5205.excuteTxn(req);
		return resp;
	}
	
	@Override
	public TrD001Response doTrD001(TrD001Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jydm = "3291";

		// 发起交易
		TrD001.TrD001Response resp = trD001.excuteTxn(req);
		return resp;
	}
	@Override
	public TrD003Response doTrD003(TrD003Request req) throws CoreSystException {
		req.jydm = "3291";
		// 发起交易
		TrD003.TrD003Response resp = trD003.excuteTxn(req);
		return resp;
	}
	@Override
	public TrD004Response doTrD004(TrD004Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jydm = "3291";

		// 发起交易
		TrD004.TrD004Response resp = trD004.excuteTxn(req);
		return resp;
	}
	@Override
	public TrD005Response doTrD005(TrD005Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jydm = "3291";

		// 发起交易
		TrD005.TrD005Response resp = trD005.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr1699Response doTr1699(Tr1699Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		// 通用设置
		// 接口版本：01--返回的客户名称为50长度，02--返回客户名称为60长度
		req.jkbb = "02";
		// 发起交易
		Tr1699.Tr1699Response resp = tr1699.excuteTxn(req);
		return resp;

	}

	
	@Override
	public Tr2569Response doTr2569(Tr2569Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.kh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jkbb = "02";
		// 发起交易
		Tr2569.Tr2569Response resp = tr2569.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3446Response doTr3446(Tr3446Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.xykkh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
		Tr3446.Tr3446Response resp = tr3446.excuteTxn(req);
		return resp;
	}
	
	@Override
	public Tr3419Response doTr3419(Tr3419Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
		Tr3419.Tr3419Response resp = tr3419.excuteTxn(req);
		return resp;
	}
	
	@Override
	public Tr3431Response doTr3431(Tr3431Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.xyk_kh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
		req.sfjymm = "1";
		Tr3431.Tr3431Response resp = tr3431.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3900Response doTr3900(Tr3900Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		Tr3900.Tr3900Response resp = tr3900.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3422Response doTr3422(Tr3422Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.xyk_kh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
		Tr3422.Tr3422Response resp = tr3422.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr5070Response doTr5070(Tr5070Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.xykkh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
    	req.qtlsh = snoGenerator.generate();
    	req.qtxtrq = new Date();
		Tr5070.Tr5070Response resp = tr5070.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr3502Response doTr3502(Tr3502Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.kh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jkbb = "02";
		Tr3502.Tr3502Response resp = tr3502.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3295Response doTr3295(Tr3295Request req) throws CoreSystException {
		Tr3295.Tr3295Response resp = tr3295.excuteTxn(req);
		return resp;
	}
	
	@Override
	public Tr3425Response doTr3425(Tr3425Request req) throws CoreSystException {
		if (!AccountUtil.isCreditCard(req.xykkh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效信用卡！");
		}
		Tr3425.Tr3425Response resp = tr3425.excuteTxn(req);
		return resp;
	}
	
	@Override
	public String getCTSPFile(String fileMac, boolean isTuxedo)throws CoreSystException {
		if (isTuxedo) {
			String tempPath = txnAdapterPara.getAttachmentTempPath();
			txnAdapterPara.getTxnComm().getFile("tuxedo:" + fileMac, tempPath+ "/" + fileMac);
			return tempPath + "/" + fileMac;
		} else {
			//因为CTSP是直接推回文件信息，所以这时已在服务器client.properties里配置好的目录里
			CTSPFileService fileService = new CTSPFileService();
			String tempPath = txnAdapterPara.getAttachmentTempPath();
			File file = fileService.getLocalFile(fileMac, tempPath+ "/" + fileMac);
			return file.getAbsolutePath();
		}

	}
	@Override
	public String getFile(String fileMac, boolean isTuxedo)
			throws CoreSystException {
		if (isTuxedo) {
			String tempPath = tuxedoOutgoingTxnAdapter.getPara().getAttachmentTempPath();
			tuxedoOutgoingTxnAdapter.getFile("tuxedo:" + fileMac, tempPath+ "/" + fileMac);
			return tempPath + "/" + fileMac;
		} else {
			String tempPath = outgoingTxnAdapter.getPara().getAttachmentTempPath();
			outgoingTxnAdapter.getFile(fileMac, tempPath + "/" + fileMac);
			return tempPath + "/" + fileMac;
		}

	}

	@Override
	public String putCTSPFile(File file) throws CoreSystException {
			String path = file.getAbsolutePath();
			String fileMac = txnAdapterPara.getTxnComm().putFile(path, file.getName());
			return fileMac;
	}
	@Override
	public String putFile(File file, boolean isTuxedo) throws CoreSystException {
		if (isTuxedo) {
			String path = "tuxedo:" + file.getAbsolutePath();
			String fileMac = tuxedoOutgoingTxnAdapter.putFile(path,file.getName());
			return fileMac;
		} else {
			String path = file.getAbsolutePath();
			String fileMac = outgoingTxnAdapter.putFile(path, file.getName());
			return fileMac;
		}

	}
	
	@Override
	public String getTuxedoFile(String fileMac, String fileName) throws CoreSystException {
		String tempPath = tuxedoOutgoingTxnAdapter.getPara().getAttachmentTempPath();
		tuxedoOutgoingTxnAdapter.getFile("tuxedo:" + fileMac + fileName,tempPath + "/" + fileName);
		return tempPath + "/" + fileName;
	}
	
	@Override
	public Tr2560Response doTr2560(Tr2560Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.kh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.fwfs = "2";
		req.jkbb = "02";
		// 发起交易
		Tr2560.Tr2560Response resp = tr2560.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr1107Response doTr1107(Tr1107Request req) throws CoreSystException {
		if(req.appid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信APPID不能为空！");
		}
		if(req.appuserid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信USERID不能为空！");
		}
		
//		req.jydm="1107";
		//发起交易
		Tr1107.Tr1107Response resp=tr1107.excuteTxn(req);
		return resp;
	}


	@Override
	
	public Tr1109Response doTr1109(Tr1109Request req) throws CoreSystException {
		if(req.appid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信APPID不能为空！");
		}
		if(req.appuserid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信USERID不能为空！");
		}
		req.lyxt = "07";//来源系统：07-微信银行
		if(StringUtils.isBlank(req.jjkh) && StringUtils.isNotBlank(req.xykh)){
			req.ywlx = "B";  //信用卡业务
		}else{
			req.ywlx = "A";  //个人业务
		}
	
		//发起交易
		Tr1109.Tr1109Response resp=tr1109.excuteTxn(req);
		return resp;
	}
	
	@Override
	public Tr1110Response doTr1110(Tr1110Request req) throws CoreSystException {
		if(req.appid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信APPID不能为空！");
		}
		if(req.appuserid==null){
			throw new AppRTException(AppExCode.NOT_NULL, "微信USERID不能为空！");
		}

		//发起交易
		Tr1110.Tr1110Response resp=tr1110.excuteTxn(req);
		return resp;
	}
	
	@Override	public Tr3779Response doTr3779(Tr3779Request req) throws CoreSystException {
		req.jkbb="02";
		req.qdzl="W";
		//发起交易
		Tr3779Response resp = tr3779.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3797Response doTr3797(Tr3797Request req) throws CoreSystException {
		req.jkbb="02";
		//发起交易
		Tr3797Response resp = tr3797.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr3796Response doTr3796(Tr3796Request req) throws CoreSystException {
		req.jkbb="02";
		//发起交易
		Tr3796Response resp = tr3796.excuteTxn(req);
		return resp;
	}

	@Override
	public String getTempPath() {
		return txnAdapterPara.getAttachmentTempPath();
	}
	
	@Override
	public TrD102Response doTrD102(TrD102Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jydm = "3291";
		//前台系统编号+前台系统日期+前台交易流水序号

		req.qtjylsh = req.qtjylsh+sdf.format(date)+snoGenerator.generate();
		// 发起交易
		TrD102.TrD102Response resp = trD102.excuteTxn(req);
		return resp;
	}
	@Override
	public TrD104Response doTrD104(TrD104Request req) throws CoreSystException {
		req.jydm = "3291";
		//前台系统编号+前台系统日期+前台交易流水序号
		req.qtjylsh = req.qtjylsh+sdf.format(date)+snoGenerator.generate();
		// 发起交易
		TrD104.TrD104Response resp = trD104.excuteTxn(req);
		return resp;
	}
	@Override
	public TrD101Response doTrD101(TrD101Request req) throws CoreSystException {
		if (!AccountUtil.isDebitCard(req.zhdh)) {
			throw new AppRTException(AppExCode.CHECK_ERROR, "输入账户不是有效借记卡！");
		}
		req.jydm = "3291";
		//前台系统编号+前台系统日期+前台交易流水序号
		req.qtjylsh = req.qtjylsh+sdf.format(date)+snoGenerator.generate();
		// 发起交易
		TrD101.TrD101Response resp = trD101.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr3902Response doTr3902(Tr3902Request req) throws CoreSystException {
		Tr3902Response resp = tr3902.excuteTxn(req);
		return resp;
	}

	@Override
	public TrW01Response doTrW01(TrW01Request req) throws CoreSystException {
		req.jydm = "5502";
		TrW01Response resp = trW01.excuteTxn(req);
		return resp;
	}
	@Override
	public TrW05Response doTrW05(TrW05Request req) throws CoreSystException {
		req.jydm = "5502";
		TrW05Response resp = trW05.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr3909Response doTr3909(Tr3909Request req) throws CoreSystException {
		req.zhdh="";
		req.infile="";
		req.jym="";
		req.dxly="MBK";
		req.ywlx="02";
		req.jkbb="02";
		req.bz="";   /* 发送普通短信 */
		//发起交易
		Tr3909Response resp = tr3909.excuteTxn(req);
		return resp;

	}
	@Override
	public Tr3908Response doTr3908(Tr3908Request req) throws CoreSystException {
		req.zhdh="";
		req.infile="";
		req.jym="";
		req.dxly="SKL";
		req.ywlx="01";
		req.jkbb="02";
		req.bz="";   /* 发送短信验证码 */
		//发起交易
		Tr3908Response resp = tr3908.excuteTxn(req);
		return resp;

	}

	@Override
	public Tr3905Response doTr3905(Tr3905Request req) throws CoreSystException {
		req.jydm = "3905";
		//发起交易
		Tr3905Response resp = tr3905.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr3416Response doTr3416(Tr3416Request req) throws CoreSystException {
		//发起交易
		Tr3416Response resp = tr3416.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr3420Response doTr3420(Tr3420Request req) throws CoreSystException {
		//发起交易
		Tr3420Response resp = tr3420.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr4614Response doTr4614(Tr4614Request req) throws CoreSystException {
		//发起交易
		Tr4614Response resp = tr4614.excuteTxn(req);
		return resp;
	}
	@Override
	public Tr8881Response doTr8881(Tr8881Request req) throws CoreSystException {
		//发起交易
		Tr8881Response resp = tr8881.excuteTxn(req);
		return resp;
	}

	@Override
	public Tr5247Response doTr5247(Tr5247Request req) throws CoreSystException {
		//发起交易
		Tr5247Response resp = tr5247.excuteTxn(req);
		return resp;
	}
}
