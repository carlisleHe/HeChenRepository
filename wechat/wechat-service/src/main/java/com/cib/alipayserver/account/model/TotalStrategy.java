package com.cib.alipayserver.account.model;

import java.util.List;
/**
 * 汇总策略接口
 * @author dvlp
 *
 */
public interface TotalStrategy {
	
	public abstract List<Total> proc(List<InnerAccount> innerAccountList);
	
	public abstract Total getTotal();
	
}
