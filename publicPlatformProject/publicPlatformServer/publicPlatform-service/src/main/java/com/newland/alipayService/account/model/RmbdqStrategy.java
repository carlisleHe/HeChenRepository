package com.newland.alipayService.account.model;

import java.util.ArrayList;
import java.util.List;

public class RmbdqStrategy implements TotalStrategy {


	public boolean pass(InnerAccount acct) {
		// TODO 定期汇总condition=hbzl=01##xxh&gt;001##xxh&lt;500##ywdh=270
		if (acct.getCurrency().equals("01") && 
				Integer.parseInt(acct.getSeqence()) <= 500 && 
				acct.getRelativeBusinessNo().equals("270")){
			return true;
		}
		
		return false;
	}

	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("人民币定期");
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
