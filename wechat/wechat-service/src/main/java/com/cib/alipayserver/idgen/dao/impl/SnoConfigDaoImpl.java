package com.cib.alipayserver.idgen.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.idgen.dao.SnoConfigDao;
import com.cib.alipayserver.idgen.model.SnoConfig;

/**
 * 流水号生成器配置DAO实现类
 * 
 * @author yelm
 */
@Service("snoConfigDao")
public class SnoConfigDaoImpl extends AlipayBaseDao<SnoConfig, Integer>
		implements SnoConfigDao {


	@Override
	public void lock(Integer snoId) {
		/** 
		 * 经验证，informix数据库中select for update，是在双方都要update时，才发生报错。
		 * 而这里我们想要的是悲观排它锁，所以显示的使用update去实现排它锁，以确保万无一失。
		 */
		String hql = "update "+SnoConfig.class.getName()+" c"
				+ " set c.maxValue = c.maxValue where c.snoId = ?";
		this.excute(hql, snoId);
	}

	@Override
	public SnoConfig findBySnoType(String snoType) {
		String hql = "from "+SnoConfig.class.getName()+" c where c.snoType = ?";
		List<SnoConfig> list = this.find(hql, snoType);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

}
