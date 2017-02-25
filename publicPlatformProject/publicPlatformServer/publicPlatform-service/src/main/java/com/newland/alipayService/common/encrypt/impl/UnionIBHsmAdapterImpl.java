/*
 * File UnionIBHsmAdapterImpl.java
 * Created on 2004-7-6
 */
package com.newland.alipayService.common.encrypt.impl;

import com.newland.alipayService.common.encrypt.HsmAdapter;
import com.newland.alipayService.common.encrypt.UnionIBWebServerMDL;

/**
 * HSM适配器实现类
 * @author seabao
 * @project IFX
 * @date 2004-7-6
 */
public class UnionIBHsmAdapterImpl implements HsmAdapter {
    /**HSM主机地址*/
    private String hsmHost;

    /**加密机密码公钥*/
    private String rsaPublicKey;
    
    /**加密机端口*/
    private int portOfHsm;
    
   
    private UnionIBWebServerMDL constructMDL() {
        return new UnionIBWebServerMDL(hsmHost, portOfHsm);
    }
    public String getHsmHost() {
        return hsmHost;
    }

    public void setHsmHost(String hsmHost) {
        this.hsmHost = hsmHost;
    }

	@Override
    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

	public int getPortOfHsm() {
		return portOfHsm;
	}

	public void setPortOfHsm(int portOfHsm) {
		this.portOfHsm = portOfHsm;
	}

	public String transAcctPwd(String acctPwd, String acctNo) {
	    return constructMDL().transAPin(acctPwd, acctNo);
	}

	public String transXYKPW(String APinProByPK) {
	    return constructMDL().transAPin(APinProByPK);
	}
}