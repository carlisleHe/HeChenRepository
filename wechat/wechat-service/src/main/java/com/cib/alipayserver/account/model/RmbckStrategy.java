package com.cib.alipayserver.account.model;

import java.util.ArrayList;
import java.util.List;

public class RmbckStrategy implements TotalStrategy {


	@Override
	public Total getTotal() {
		Total total = new Total();
		total.setTitle("人民币存款");
		total.setTotal(true);
		total.setCurrency("01");
		return total;
	}

	static List<TotalStrategy> list = new ArrayList<TotalStrategy>();
	static{
		list.add(new RmbhqStrategy());
		list.add(new RmbdqStrategy());
		list.add(new RmbtzStrategy());
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
		return tlist;
	}


}
