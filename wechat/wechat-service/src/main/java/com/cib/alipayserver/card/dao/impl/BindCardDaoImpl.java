package com.cib.alipayserver.card.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cib.alipayserver.base.AlipayBaseDao;
import com.cib.alipayserver.card.dao.BindCardDao;
import com.cib.alipayserver.card.model.BindCard;
import com.cib.alipayserver.common.Const;

/**
 * 
 * @ClassName: AuthCardDaoImpl  
 * @Description: dao实现类 
 * @author: xuzz 
 * @date:2014-3-20 下午03:14:26
 */
@Service("bindCardDao")
public class BindCardDaoImpl extends AlipayBaseDao<BindCard, String> implements BindCardDao{

	@Override
	public BindCard findByAcctNo(String appId,String openId,String accountNo) {
		String hql = "from "+BindCard.class.getName()+" a where a.appId = ? and a.openId = ? and a.acctNo = ? and a.valid = ?";
		List<BindCard> list = this.find(hql, appId,openId,accountNo,Const.STATUS_VALID);
		if(list.size() == 0){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	@Override
	public BindCard findByCibAcctNo(String appId, String openId,String cibAcctNo) {
		String hql = "from "+BindCard.class.getName()+" a where a.appId = ? and a.openId = ? and a.cibAcctNo = ? and a.valid = ?";
		List<BindCard> list = this.find(hql, appId,openId,cibAcctNo,Const.STATUS_VALID);
		if(list.size() == 0){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	@Override
	public List<BindCard> findByOpenId(String appId, String openId) {
		String hql = "from "+BindCard.class.getName()+" a where a.appId = ? and a.openId = ? and a.valid = ?";
		List<BindCard> list = this.find(hql, appId,openId,Const.STATUS_VALID);
		return list;
	}
	@Override
	public List<BindCard> findByAcctNo(String acctNo) {
		String hql = "from "+BindCard.class.getName()+" a where a.acctNo = ? ";
		List<BindCard> list = this.find(hql, acctNo);
		return list;
	}
	@Override
	public List<BindCard> findByCustId(String custId) {
		String hql = "from " + BindCard.class.getName()
				+ " a where a.custId = ? and a.valid = ?";
		List<BindCard> list = this.find(hql, custId, Const.STATUS_VALID);
		return list;
	}
	
	/**
	 * 根据类型查询绑定用户数
	 * @param acctType
	 * @return
	 */
	@Override
	public int countAuthCardByType(String appId,String acctType){
		String hql = "SELECT COUNT(*) from "+BindCard.class.getName()+ " a where a.appId = ? and a.acctType = ? and a.valid = ?";
		Long count = (Long) this.findUnique(hql,appId, acctType,Const.STATUS_VALID);
		return count.intValue();

	}
	@Override
	public BindCard findByAcctNoAndType(String appId, String acctNo,String acctType) {
		String hql = "from "+BindCard.class.getName()+" a where a.acctNo = ?  and a.appId = ? and a.acctType = ? and a.valid = ?";
		List<BindCard> list = this.find(hql, acctNo,appId,acctType,Const.STATUS_VALID);
		if(list.size() == 0){
			return null;
		}
		else{
			return list.get(0);
		}
	}
	
	@Override
	public List<BindCard> findByCibAcctNo(String cibAcctNo) {
		String hql = "from "+BindCard.class.getName()+" a where a.cibAcctNo = ? ";
		List<BindCard> list = this.find(hql, cibAcctNo);
		return list;
	}

}
