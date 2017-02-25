package com.newland.alipayService.account.model;

import java.util.ArrayList;
import java.util.List;

public class UsackStrategy implements TotalStrategy {


	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("美元存款");
		total.setCurrency("14");
		total.setTotal(true);
		return total;
	}

	static List<TotalStrategy> list = new ArrayList<TotalStrategy>();
	static{
		list.add(new UsahqStrategy());
		list.add(new UsadqStrategy());
	}

	@Override
	public List<Total> proc(List<InnerAccount> innerAccountList) {
		List<Total> tlist = new ArrayList<Total>();
		Total root = getTotal();
		tlist.add(root);
		for (TotalStrategy sty:list){
			List<Total> tl = sty.proc(innerAccountList);
			for (Total s:tl){
				root.add(s.getAmount());
				tlist.add(s);
			}
		}
		if (root.getAmount().intValue() == 0) tlist.clear();
		return tlist;
	}

}
