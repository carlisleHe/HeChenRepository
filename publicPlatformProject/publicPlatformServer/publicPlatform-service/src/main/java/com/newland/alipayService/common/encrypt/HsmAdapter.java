package com.newland.alipayService.common.encrypt;

public interface HsmAdapter {

    /**
     * 加密借记卡帐户密码
     */
    public String transAcctPwd(String acctPwd, String acctNo);
    
    /**
     * 加密信用卡帐户密码
     */
    public String transXYKPW(String APinProByPK);

	/**
	 * 获取加密机公钥
	 * 
	 * @return
	 */
	public String getRsaPublicKey();
}
