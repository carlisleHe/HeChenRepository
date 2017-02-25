package com.newland.alipayService.txn;

import com.intensoft.coresyst.TxnRequest;
import com.intensoft.coresyst.TxnResponse;
import com.intensoft.exception.AppBizException;

/**
 * 交易处理接口（具体交易服务实现类）
 * @author zhangzhaoxing
 */
public interface ITxnProcess {
	/**
	 * 具体交易处理
	 * @param txnRequest
	 * @param txnResponse
	 * @throws AppBizException
	 */
	public void process(TxnRequest txnRequest, TxnResponse txnResponse) throws AppBizException;
}
