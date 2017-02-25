package com.newland.alipayService.card.service;

import java.util.List;

import com.newland.alipayService.card.model.BindCard;



/**
 * 
 * @ClassName: BindCardService  
 * @Description: 微信卡绑定服务类 
 * @author: xuzz 
 * @date:2014-3-12 下午03:49:31
 */
public interface BindCardService {
	/**
	 * 绑定卡授权
	 * <h3>业务规则:</h3>
	 * <ol>
	 * <li>一张卡号在一个应用ID下，只能与一个应用客户号绑定，但一个应用客户号可以绑定多张卡</li>
	 * <li>绑定完成后自动默认设置当前的卡为默认卡，借记卡、信用卡规则同适用</li>
	 * <li>如果该卡原先存在绑定关系，则新绑定后默认解除之前的绑定关系</li>
	 * <li>绑定关系确认后，默认开通动户通知</li>
	 * </ol>@param authCard
	 * @return
	 */
	public BindCard bindAuthCard(BindCard authCard);
	/**
	 * 解绑卡授权
	 * @param autCard
	 * @return
	 */
	public void unbindAuthCard(BindCard autCard);
	/**
	 * 根据应用客户号查询绑定卡信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @return
	 */
	public List<BindCard> findAuthCardsByClientId(String appId,String openId);
	/**
	 * 根据应用号、卡号查询卡授权信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @param acctNo 账号
	 * @return
	 */
	public BindCard findAuthCardByAcctNo(String appId,String openId,String acctNo);
	
	/**
	 * 根据应用号、核心卡号查询卡借记卡授权信息
	 * @param appId 应用ID
	 * @param openId 应用客户ID
	 * @param cibAcctNo 核心账号
	 * @return
	 */
	public BindCard findAuthCardBycibAcctNo(String appId,String openId,String cibAcctNo);


	/**
	 * 根据用户授权号查询对应的卡授权信息
	 * @param authId 用户授权号
	 * @return
	 */
	public List<BindCard> findAuthCardByCustId(String authId);
	/**
	 * 更新卡授权信息
	 * @param authCard
	 * @return
	 */
	public BindCard updBindCard(BindCard authCard);

}
