package com.cib.alipayserver.idgen.dao;

import com.cib.alipayserver.idgen.model.SnoConfig;
import com.intensoft.dao.GenericDao;

/**
 * 流水号服务DAO
 */
public interface SnoConfigDao extends GenericDao<SnoConfig, Integer> { 

	/**
	 * 锁定数据库记录
	 */
	public void lock(Integer snoId);

	/**
	 * 根据流水号类型查找当前配置
	 * @param snoType 流水号类型
	 * @return 流水号配置
	 */
	public SnoConfig findBySnoType(String snoType);

}
