package com.newland.weixinService.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.newland.alipayService.account.model.InnerAccount;
import com.newland.weixinService.model.Total;
import com.newland.weixinService.service.TotalStrategy;

public class RmbtzStrategy implements TotalStrategy {

	public boolean pass(InnerAccount acct) {
		//通知汇总 condition=hbzl=01##xxh&gt;001##xxh&lt;500##ywdh=266
		if (acct.getCurrency().equals("01") && 
				Integer.parseInt(acct.getSeqence()) >= 001 && 
				acct.getRelativeBusinessNo().equals("266")){
			return true;
		}
		return false;
	}

	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("人民币通知");
		total.setCurrency("01");
		return total;
	}
	
	@Override
	public List<Total> proc(List<InnerAccount> innerAccountList) {
		List<Total> list = new ArrayList<Total>();
		Total root = getTotal();
		list.add(root);
		for (InnerAccount acct:innerAccountList){
			if (this.pass(acct))
				root.add(acct.getBalance());
		}
		return list;
	}

}
