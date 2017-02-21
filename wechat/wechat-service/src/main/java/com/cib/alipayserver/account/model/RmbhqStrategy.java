package com.cib.alipayserver.account.model;

import java.util.ArrayList;
import java.util.List;

public class RmbhqStrategy implements TotalStrategy {
	
	public boolean pass(InnerAccount acct) {
		if (acct.getSeqence().equals("001") && 
				acct.getCurrency().equals("01")){
			return true;
		}
		if (acct.getCurrency().equals("01") && Integer.parseInt(acct.getSeqence()) >= 499){
			return true;
		}
		return false;
	}

	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("人民币活期");
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
