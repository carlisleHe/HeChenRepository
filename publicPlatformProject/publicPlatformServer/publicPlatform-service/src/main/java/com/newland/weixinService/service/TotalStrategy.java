package com.newland.weixinService.service;

import java.util.List;

import com.newland.alipayService.account.model.InnerAccount;
import com.newland.weixinService.model.Total;
/**
 * 汇总策略接口
 * @author dvlp
 *
 */
public interface TotalStrategy {
	
	public abstract List<Total> proc(List<InnerAccount> innerAccountList);
	
	public abstract Total getTotal();
	
}
