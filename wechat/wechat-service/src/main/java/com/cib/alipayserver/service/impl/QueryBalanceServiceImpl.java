package com.cib.alipayserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.account.model.FinanceAccount;
import com.cib.alipayserver.account.model.RmbckStrategy;
import com.cib.alipayserver.account.model.Total;
import com.cib.alipayserver.account.model.TotalStrategy;
import com.cib.alipayserver.account.model.UsackStrategy;
import com.cib.alipayserver.account.service.QueryOpenApi;
import com.cib.alipayserver.service.QueryBalanceService;

@Service("queryBalanceService")
public class QueryBalanceServiceImpl implements QueryBalanceService, InitializingBean {
	
	private List<TotalStrategy> list;
	@Resource (name = "queryOpenApi")
	private QueryOpenApi queryService;

    private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public List<Total> queryBalance(String acctNo) {
		FinanceAccount acct = this.queryService.queryBalance(acctNo);
		if (acct == null) {
			logger.error("卡号：[" + acctNo + "],查询余额出错！");
			return null;
		}
		List<Total> result = new ArrayList<Total>();
		for (TotalStrategy sty:list){
			result.addAll(sty.proc(acct.getInnerAccount()));
		}
		return result;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		list = new ArrayList<TotalStrategy>();
		list.add(new RmbckStrategy());
		list.add(new UsackStrategy());
	}

}
