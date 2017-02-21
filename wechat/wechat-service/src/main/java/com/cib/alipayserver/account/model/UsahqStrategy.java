package com.cib.alipayserver.account.model;

import java.util.ArrayList;
import java.util.List;

public class UsahqStrategy implements TotalStrategy {

	public boolean pass(InnerAccount acct) {
		// TODO condition=ywdh=398||ywdh=394
		if (acct.getCurrency().equals("14") &&
				(acct.getRelativeBusinessNo().equals("398")
						|| acct.getRelativeBusinessNo().equals("394"))){
			return true;
		}
		return false;
	}

	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("美元活期");
		total.setCurrency("14");
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
