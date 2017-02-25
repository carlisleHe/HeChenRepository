package com.newland.alipayService.card.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.newland.alipayService.card.model.CreditCardPassword;
import com.newland.alipayService.common.encrypt.HsmAdapter;
import com.newland.alipayService.txn.outgoing.Tr3416.Tr3416Request;
import com.newland.alipayService.txn.outgoing.Tr3416.Tr3416Response;
import com.newland.alipayService.txn.outgoing.Tr3420.Tr3420Request;
import com.newland.alipayService.txn.outgoing.Tr3420.Tr3420Response;
import com.newland.alipayService.txn.outgoing.Tr4614.Tr4614Request;
import com.newland.alipayService.txn.service.TxnService;
import com.newland.base.common.AppExCode;

@Service("creditCardPasswordService")
public class CreditCardPasswordImpl implements CreditCardPasswordService {
	private static final Logger logger = LoggerFactory
			.getLogger(CreditCardPasswordImpl.class);
	@Resource(name = "txnService")
	private TxnService txnService;
	@Resource(name = "hsmAdapter")
	private HsmAdapter hsmAdapter;

	@Override
	public boolean isSetPassByAcctNo(String acctNo) throws AppBizException {
		if (acctNo == null) {
			throw new AppRTException(AppExCode.NOT_NULL, "必输要素不能为空！");
		}

		Tr3416Request req = new Tr3416Request();
		req.xyk_kh = acctNo;
		req.cxszbz = "1";
		req.xykmm = "";
		try {
			Tr3416Response resp = txnService.doTr3416(req);
			String sfszmm = resp.sfszmm;
			if (sfszmm.equals("1")) {
				return true; // 有设置
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AppRTException(AppExCode.TXN_EXCEPTION, String.format(
					"查询信用卡是否激活失败，原因：[%s]", e.getMessage()));
		}
	}

	@Override
	public String findMobileByAcctNo(String acctNo) throws AppBizException {
		if (acctNo == null) {
			throw new AppRTException(AppExCode.NOT_NULL, "必输要素不能为空！");
		}

		Tr3420Request req = new Tr3420Request();
		req.xyk_kh = acctNo;
		req.czgn = "1";

		try {
			Tr3420Response resp = txnService.doTr3420(req);
			String sjh = resp.sjh;
			return sjh;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AppRTException(AppExCode.TXN_EXCEPTION, String.format(
					"查询信用卡设置的手机号失败，原因：[%s]", e.getMessage()));
		}

	}

	@Override
	public boolean setQueryPassword(CreditCardPassword creditCardPassword)
			throws AppBizException {
		if (creditCardPassword == null) {
			throw new AppRTException(AppExCode.NOT_NULL, "必输要素不能为空！");
		}
		String password = hsmAdapter.transXYKPW(creditCardPassword
				.getQueryPassword());

		Tr3416Request req = new Tr3416Request();
		req.xyk_kh = creditCardPassword.getAcctNo();
		req.cxszbz = "";
		req.xykmm = password;
		req.cvv2 = creditCardPassword.getSign();
		req.zjhm = creditCardPassword.getCertNo();
		try {
			Tr3416Response resp = txnService.doTr3416(req);
			String sfszmm = resp.sfszmm;
			if (sfszmm.equals("1")) {
				return true; // 有设置
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AppRTException(AppExCode.TXN_EXCEPTION, String.format(
					"设置信用卡查询密码失败，原因：[%s]", e.getMessage()));
		}
	}

	@Override
	public void setTranPassword(CreditCardPassword creditCardPassword)
			throws AppBizException {
		if (creditCardPassword == null) {
			throw new AppRTException(AppExCode.NOT_NULL, "必输要素不能为空！");
		}
		String password = hsmAdapter.transXYKPW(creditCardPassword
				.getTranPassword());

		Tr4614Request req = new Tr4614Request();
		req.xyk_kh = creditCardPassword.getAcctNo();
		req.xmm = password;
		req.cvv2 = creditCardPassword.getSign();
		req.zjhm = creditCardPassword.getCertNo();
		try {
			txnService.doTr4614(req);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new AppRTException(AppExCode.TXN_EXCEPTION, String.format(
					"设置信用卡交易密码失败，原因：[%s]", e.getMessage()));
		}

	}

	@Override
	public void checkTranPassword(CreditCardPassword creditCardPassword)
			throws AppBizException {
		String password = hsmAdapter.transXYKPW(creditCardPassword
				.getTranPassword());

		Tr3420Request req = new Tr3420Request();
		req.xyk_kh = creditCardPassword.getAcctNo();
		req.czgn = "1";
		req.sfjymm = "2";
		req.xykmm = password;
		txnService.doTr3420(req);
	}

}
