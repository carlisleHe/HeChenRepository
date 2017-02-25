package com.newland.weixinService.card.dao;

import java.util.List;

import com.intensoft.dao.GenericDao;
import com.newland.weixinService.card.model.BindCard;

/**
 * 
 * @ClassName: AuthCardDao  
 * @Description: 卡授权dao 
 * @author: xuzz 
 * @date:2014-3-20 下午03:11:07
 */
public interface BindCardDao extends GenericDao<BindCard, String>{
	/**
	 * 根据卡号查询对应客户ID下卡授权信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @param acctNo 卡号
	 * @return 状态有效卡授权信息
	 */
	public BindCard findByAcctNo(String appId,String openId,String acctNo);
	
	
	/**
	 * 根据核心卡号查询借记卡对应客户ID下卡授权信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @param acctNo 卡号
	 * @return 状态有效卡授权信息
	 */
	public BindCard findByCibAcctNo(String appId,String openId,String cibAcctNo);
	/**
	 * 根据应用客户ID查询绑定卡信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @return 有效状态的信息
	 */
	public List<BindCard> findByOpenId(String appId,String openId);
	/**
	 * 根据卡号查询绑定信息
	 * @param acctNo 卡号
	 * @return 无关状态的卡授权信息
	 */
	public List<BindCard>  findByAcctNo(String acctNo);
	
	/**
	 * 根据用户授权号查询对应的卡授权信息
	 * 
	 * @param custId
	 *            用户授权号
	 * @return 有效状态的卡授权信息
	 */
	public List<BindCard> findByCustId(String custId);
	
	/**
	 * 后台 根据类型查询绑定用户数
	 * @param acctType
	 * @return
	 */
	public int countAuthCardByType(String appId,String acctType);
	
	/**
	 * 根据行内卡号查询卡授权信息，仅适用于借记卡
	 * @param cibAcctNo
	 * @return
	 */
	public List<BindCard> findByCibAcctNo(String cibAcctNo);
   
	/**
	 * 后台 根据卡号、类型、appId查询卡授权信息
	 * @param appId
	 * @param acctNo
	 * @param acctType
	 * @return
	 */
	public BindCard findByAcctNoAndType(String appId,String acctNo,String acctType);
	
}
